package online.skyblock.upgrade;

import java.util.Objects;

/** Immutable progression state for one upgrade type on one island. */
public record UpgradeProgress(UpgradeType type, int level) {
    public UpgradeProgress {
        Objects.requireNonNull(type, "type");
        if (level < 0) {
            throw new IllegalArgumentException("Upgrade level cannot be negative");
        }
    }

    public long nextLevelCost() {
        return type.nextLevelCost(level);
    }

    public UpgradeProgress increment() {
        return new UpgradeProgress(type, Math.incrementExact(level));
    }
}
