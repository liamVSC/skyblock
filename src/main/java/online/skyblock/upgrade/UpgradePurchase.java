package online.skyblock.upgrade;

import online.skyblock.economy.CoinBalance;

import java.util.Objects;

/**
 * Pure purchase calculation. A caller must persist the returned balance and
 * upgrade progress together in one database transaction before reporting
 * success to a player.
 */
public final class UpgradePurchase {
    private UpgradePurchase() {
    }

    public static Result purchase(CoinBalance balance, UpgradeProgress progress) {
        Objects.requireNonNull(balance, "balance");
        Objects.requireNonNull(progress, "progress");

        long cost = progress.nextLevelCost();
        CoinBalance updatedBalance = balance.withdraw(cost);
        UpgradeProgress updatedProgress = progress.increment();
        return new Result(updatedBalance, updatedProgress, cost);
    }

    public record Result(CoinBalance balance, UpgradeProgress progress, long cost) {
        public Result {
            Objects.requireNonNull(balance, "balance");
            Objects.requireNonNull(progress, "progress");
            if (cost <= 0) {
                throw new IllegalArgumentException("Purchase cost must be positive");
            }
        }
    }
}
