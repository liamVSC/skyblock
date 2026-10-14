package online.skyblock.upgrade;

import online.skyblock.economy.CoinBalance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradePurchaseTest {
    @Test
    void successfulPurchaseDeductsCostAndIncrementsLevel() {
        UpgradePurchase.Result result = UpgradePurchase.purchase(
                new CoinBalance(2_000),
                new UpgradeProgress(UpgradeType.ISLAND_SIZE, 0));

        assertEquals(1_000, result.cost());
        assertEquals(1_000, result.balance().coins());
        assertEquals(1, result.progress().level());
    }

    @Test
    void rejectsInsufficientFundsWithoutMutatingInputs() {
        CoinBalance balance = new CoinBalance(999);
        UpgradeProgress progress = new UpgradeProgress(UpgradeType.ISLAND_SIZE, 0);

        assertThrows(IllegalArgumentException.class,
                () -> UpgradePurchase.purchase(balance, progress));
        assertEquals(999, balance.coins());
        assertEquals(0, progress.level());
    }

    @Test
    void secondPurchaseUsesNextLevelPrice() {
        UpgradeProgress levelOne = new UpgradeProgress(UpgradeType.ISLAND_SIZE, 1);
        assertEquals(1_750, levelOne.nextLevelCost());
    }
}
