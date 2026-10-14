package online.skyblock.island;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IslandProfileTest {
    private final UUID islandId = UUID.randomUUID();
    private final UUID ownerId = UUID.randomUUID();
    private final Instant createdAt = Instant.parse("2026-01-01T00:00:00Z");

    @Test
    void createsValidatedIslandProfile() {
        IslandProfile island = new IslandProfile(islandId, ownerId, "  My Island ", 1, createdAt);
        assertEquals("My Island", island.name());
        assertEquals(1, island.level());
    }

    @Test
    void rejectsInvalidNameAndLevel() {
        assertThrows(IllegalArgumentException.class,
                () -> new IslandProfile(islandId, ownerId, "  ", 1, createdAt));
        assertThrows(IllegalArgumentException.class,
                () -> new IslandProfile(islandId, ownerId, "x".repeat(33), 1, createdAt));
        assertThrows(IllegalArgumentException.class,
                () -> new IslandProfile(islandId, ownerId, "Island", 0, createdAt));
    }

    @Test
    void renameAndUpgradeReturnNewRecords() {
        IslandProfile island = new IslandProfile(islandId, ownerId, "Island", 1, createdAt);
        assertEquals("Renamed", island.rename("Renamed").name());
        assertEquals(2, island.withLevel(2).level());
        assertEquals("Island", island.name());
        assertEquals(1, island.level());
    }

    @Test
    void rejectsLevelDowngrade() {
        IslandProfile island = new IslandProfile(islandId, ownerId, "Island", 2, createdAt);
        assertThrows(IllegalArgumentException.class, () -> island.withLevel(1));
    }
}
