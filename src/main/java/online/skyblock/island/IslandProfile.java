package online.skyblock.island;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Immutable island ownership and progression state.
 * Persistence adapters must store and load this record without changing its
 * validation rules.
 */
public record IslandProfile(
        UUID islandId,
        UUID ownerId,
        String name,
        int level,
        Instant createdAt
) {
    public IslandProfile {
        Objects.requireNonNull(islandId, "islandId");
        Objects.requireNonNull(ownerId, "ownerId");
        Objects.requireNonNull(createdAt, "createdAt");
        name = Objects.requireNonNull(name, "name").trim();
        if (name.isEmpty() || name.length() > 32) {
            throw new IllegalArgumentException("Island name must contain 1 to 32 characters");
        }
        if (level < 1) {
            throw new IllegalArgumentException("Island level must be at least 1");
        }
    }

    public IslandProfile rename(String newName) {
        return new IslandProfile(islandId, ownerId, newName, level, createdAt);
    }

    public IslandProfile withLevel(int newLevel) {
        if (newLevel < level) {
            throw new IllegalArgumentException("Island level cannot decrease through an upgrade");
        }
        return new IslandProfile(islandId, ownerId, name, newLevel, createdAt);
    }
}
