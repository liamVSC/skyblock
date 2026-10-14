package online.skyblock.upgrade;

/**
 * Initial server-wide upgrade catalogue. Prices are whole coins and benefits
 * are deliberately explicit so they can be surfaced consistently in menus,
 * persistence, and leaderboard scoring.
 */
public enum UpgradeType {
    ISLAND_SIZE("Island Size", 1_000, 750, "Expands the protected island border"),
    GENERATOR_TIER("Generator Tier", 2_500, 1_000, "Improves generator progression"),
    MEMBER_SLOTS("Member Slots", 5_000, 2_000, "Adds island team capacity");

    private final String displayName;
    private final long baseCost;
    private final long costPerLevel;
    private final String description;

    UpgradeType(String displayName, long baseCost, long costPerLevel, String description) {
        this.displayName = displayName;
        this.baseCost = baseCost;
        this.costPerLevel = costPerLevel;
        this.description = description;
    }

    public String displayName() {
        return displayName;
    }

    public String description() {
        return description;
    }

    /**
     * Price for buying the next level, where currentLevel 0 means unpurchased.
     * Uses exact arithmetic so invalid levels or overflow cannot silently
     * create free or negative upgrades.
     */
    public long nextLevelCost(int currentLevel) {
        if (currentLevel < 0) {
            throw new IllegalArgumentException("Current upgrade level cannot be negative");
        }
        return Math.addExact(baseCost, Math.multiplyExact(costPerLevel, currentLevel));
    }
}
