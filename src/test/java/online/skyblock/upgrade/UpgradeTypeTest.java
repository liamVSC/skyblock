package online.skyblock.upgrade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeTypeTest {
    @Test
    void pricesNextUpgradeLevelPredictably() {
        assertEquals(1_000, UpgradeType.ISLAND_SIZE.nextLevelCost(0));
        assertEquals(1_750, UpgradeType.ISLAND_SIZE.nextLevelCost(1));
        assertEquals(4_500, UpgradeType.GENERATOR_TIER.nextLevelCost(2));
    }

    @Test
    void rejectsNegativeLevelsAndCalculatesLargeValidLevelsExactly() {
        assertThrows(IllegalArgumentException.class, () -> UpgradeType.MEMBER_SLOTS.nextLevelCost(-1));
        assertEquals(1_000L + 750L * Integer.MAX_VALUE,
                UpgradeType.ISLAND_SIZE.nextLevelCost(Integer.MAX_VALUE));
    }
}
