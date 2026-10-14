package online.skyblock.economy;

/**
 * Immutable, non-negative whole-coin balance.
 *
 * <p>This value object deliberately contains no persistence or Bukkit state;
 * economy services can use it to validate balance changes before committing
 * them through the chosen durable storage layer.</p>
 */
public record CoinBalance(long coins) {

    public CoinBalance {
        if (coins < 0) {
            throw new IllegalArgumentException("Coin balance cannot be negative");
        }
    }

    public CoinBalance deposit(long amount) {
        requirePositive(amount);
        return new CoinBalance(Math.addExact(coins, amount));
    }

    public CoinBalance withdraw(long amount) {
        requirePositive(amount);
        if (amount > coins) {
            throw new IllegalArgumentException("Insufficient coin balance");
        }
        return new CoinBalance(coins - amount);
    }

    private static void requirePositive(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Coin amount must be positive");
        }
    }
}
