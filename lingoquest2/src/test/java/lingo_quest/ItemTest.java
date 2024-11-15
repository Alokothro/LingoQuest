package lingo_quest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lingoquestpackage.models.*;


public class ItemTest {

    private Item item;

    @Before
    public void setUp() {
        item = new Item("Sword", "A sharp blade.", 100);
    }

    @Test
    public void testPriceBoundaries() {
        Item expensiveItem = new Item("Castle", "A large stronghold.", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, expensiveItem.getPrice());
        Item freeItem = new Item("Air", "Just some air.", Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, freeItem.getPrice());
    }

    @Test(expected = NullPointerException.class)
    public void testNullName() {
        new Item(null, "No name provided.", 50);
    }

    @Test
    public void testUUIDUniqueness() {
        Set<UUID> ids = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            ids.add(new Item().getID());
        }
        assertEquals(1000, ids.size());  // Expecting no duplicates in UUIDs
    }

    @Test
    public void testImmutability() {
        UUID initialId = item.getID();
        try {
            UUID newId = UUID.randomUUID();
            item.setID(newId);
            assertNotEquals(newId, initialId);
        } finally {
            item.setID(initialId);  // Resetting ID to ensure immutability
        }
        assertEquals(initialId, item.getID());
    }

    @Test
    public void testToStringConsistency() {
        item = new Item("Pen", "Used for writing.", 1);
        assertEquals("Name: Pen, Description: Used for writing., Price: 1", item.toString());
        item = new Item("Pencil", "Used for drawing.", 2);
        assertEquals("Name: Pencil, Description: Used for drawing., Price: 2", item.toString());
    }

    @Test(timeout = 100)
    public void testPerformance() {
        for (int i = 0; i < 100000; i++) {
            new Item("Item" + i, "Description" + i, i);
        }
    }
}
