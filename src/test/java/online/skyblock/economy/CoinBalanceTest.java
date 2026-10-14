package online.skyblock.economy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoinBalanceTest {

    @Test
    void startsAtZeroAndAcceptsDeposits() {
        assertEquals(0, new CoinBalance(0).coins());
        assertEquals(25, new CoinBalance(10).deposit(15).coins());
    }

    @Test
    void withdrawsWithoutMutatingOriginalBalance() {
        CoinBalance original = new CoinBalance(40);

        assertEquals(15, original.withdraw(25).coins());
        assertEquals(40, original.coins());
    }

    @Test
    void rejectsNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> new CoinBalance(-1));
    }

    @Test
    void rejectsZeroOrNegativeTransactions() {
        CoinBalance balance = new CoinBalance(10);

        assertThrows(IllegalArgumentException.class, () -> balance.deposit(0));
        assertThrows(IllegalArgumentException.class, () -> balance.deposit(-1));
        assertThrows(IllegalArgumentException.class, () -> balance.withdraw(0));
        assertThrows(IllegalArgumentException.class, () -> balance.withdraw(-1));
    }

    @Test
    void rejectsOverspending() {
        assertThrows(IllegalArgumentException.class, () -> new CoinBalance(10).withdraw(11));
    }

    @Test
    void rejectsOverflowRatherThanWrappingBalance() {
        assertThrows(ArithmeticException.class, () -> new CoinBalance(Long.MAX_VALUE).deposit(1));
    }
}
