package dungeonforge;


import dungeonforge.events.EventType;
import dungeonforge.items.BasicWeapon;
import dungeonforge.items.EnchantmentRegistry;
import dungeonforge.items.Weapon;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

/** WEEK 12 -- Adapter (US-9.1), Facade (US-9.2), the replay receipt (US-9.3), carry-over (US-8.5). */
class AdapterFacadeTest {



    // ===================== US-8.5 : the carry-over =====================

    @Test
    void enchantmentNamesAreCaseAndWhitespaceInsensitive() {
        assertTrue(EnchantmentRegistry.isKnown("FLAMING"), "a player typing in caps is not an error");
        assertTrue(EnchantmentRegistry.isKnown("  frost  "));
        Weapon w = EnchantmentRegistry.apply("  FLAMING ", new BasicWeapon("Iron Sword", 3.0, 40, 8));
        assertEquals("Flaming Iron Sword", w.getName());
        assertFalse(EnchantmentRegistry.isKnown("sparkly"), "unknown is still unknown");
    }

    @Test
    void theMonsterStateEventIsSpelledCorrectly() {
        assertNotNull(EventType.valueOf("MONSTER_STATE_CHANGED"));
        for (EventType t : EventType.values()) {
            assertFalse(t.name().contains("SATE"), "left-over typo: " + t.name());
        }
    }

}
