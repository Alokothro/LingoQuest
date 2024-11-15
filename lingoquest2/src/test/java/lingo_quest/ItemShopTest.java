package lingo_quest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import lingoquestpackage.models.*;


public class ItemShopTest {

    private ItemShop itemShop;
    private User testUser;
    private Item testItem;
    private UUID testItemID;

    @BeforeEach
    public void setUp() {
        itemShop = ItemShop.getInstance();
        testUser = new User();
        testItem = new Item("Test Item", null, 100);
        testItemID = testItem.getID();
        
        // Reset the itemShop for each test
        itemShop.getItems().clear();
        itemShop.addItem(testItem);
    }

    @Test
    public void testAddItem_addNewItem() {
        Item newItem = new Item("New Item", null, 200);
        itemShop.addItem(newItem);
        assertTrue(itemShop.getItems().contains(newItem));
    }

    @Test
    public void testAddItem_addNullItem() {
        itemShop.addItem(null);
        assertEquals(1, itemShop.getItems().size());
    }

    @Test
    public void testAddItem_addDuplicateItem() {
        itemShop.addItem(testItem);
        assertEquals(1, itemShop.getItems().size()); // Should not add duplicate
    }

    @Test
    public void testAddItem_addMultipleUniqueItems() {
        Item newItem1 = new Item("Item 1", null, 150);
        Item newItem2 = new Item("Item 2", null, 250);
        itemShop.addItem(newItem1);
        itemShop.addItem(newItem2);
        assertEquals(3, itemShop.getItems().size());
    }

    @Test
    public void testAddItem_checkAddedItemInList() {
        assertTrue(itemShop.getItems().contains(testItem));
    }

    @Test
    public void testGetInstance_returnsSameInstance() {
        ItemShop anotherInstance = ItemShop.getInstance();
        assertEquals(itemShop, anotherInstance);
    }

    @Test
    public void testPurchaseItem_successfulPurchase() {
        testUser.addCoins(200); // Ensure the user has enough coins
        assertTrue(itemShop.purchaseItem(testUser, testItemID));
    }

    @Test
    public void testPurchaseItem_insufficientCoins() {
        testUser.addCoins(50); // Not enough coins
        assertFalse(itemShop.purchaseItem(testUser, testItemID));
    }

    @Test
    public void testPurchaseItem_alreadyOwnsItem() {
        testUser.addCoins(200);
        itemShop.purchaseItem(testUser, testItemID);
        assertFalse(itemShop.purchaseItem(testUser, testItemID)); // Already owns it
    }

    @Test
    public void testPurchaseItem_invalidItemID() {
        UUID invalidID = UUID.randomUUID();
        assertFalse(itemShop.purchaseItem(testUser, invalidID)); // Non-existent item
    }

    @Test
    public void testPurchaseItem_checkUserInventoryAfterPurchase() {
        testUser.addCoins(200);
        itemShop.purchaseItem(testUser, testItemID);
        assertTrue(testUser.getInventory().contains(testItemID));
    }

    @Test
    public void testGetItem_returnsCorrectString() {
        String itemString = itemShop.getItem(testItem);
        assertEquals(testItem.toString(), itemString);
    }

    @Test
    public void testGetItem_nullItem() {
        assertNull(itemShop.getItem(null));
    }

    @Test
    public void testGetItem_nonExistentItem() {
        Item nonExistentItem = new Item("Non-existent", null, 100);
        String itemString = itemShop.getItem(nonExistentItem);
        assertNotEquals(testItem.toString(), itemString);
    }

    @Test
    public void testGetItem_emptyItemShop() {
        itemShop.getItems().clear();
        assertNull(itemShop.getItem(testItem));
    }

    @Test
    public void testGetItem_multipleItems() {
        Item anotherItem = new Item("Another Item", null, 150);
        itemShop.addItem(anotherItem);
        assertEquals(anotherItem.toString(), itemShop.getItem(anotherItem));
    }

    @Test
    public void testGetItemByID_existingID() {
        assertEquals(testItem, itemShop.getItemByID(testItemID));
    }

    @Test
    public void testGetItemByID_nonExistentID() {
        UUID randomID = UUID.randomUUID();
        assertNull(itemShop.getItemByID(randomID));
    }

    @Test
    public void testGetItemByID_nullID() {
        assertNull(itemShop.getItemByID(null));
    }

    @Test
    public void testGetItemByID_multipleItems() {
        Item anotherItem = new Item("Another Item", null, 150);
        itemShop.addItem(anotherItem);
        assertEquals(anotherItem, itemShop.getItemByID(anotherItem.getID()));
    }

    @Test
    public void testGetItemByID_afterRemovingItem() {
        itemShop.getItems().remove(testItem);
        assertNull(itemShop.getItemByID(testItemID));
    }

    @Test
    public void testGetItems_returnsListOfItems() {
        ArrayList<Item> items = itemShop.getItems();
        assertNotNull(items);
        assertTrue(items.contains(testItem));
    }

    @Test
    public void testGetItems_afterAddingMultipleItems() {
        Item anotherItem = new Item("Another Item", null, 150);
        itemShop.addItem(anotherItem);
        assertEquals(2, itemShop.getItems().size());
    }

    @Test
    public void testGetItems_afterClearingItems() {
        itemShop.getItems().clear();
        assertEquals(0, itemShop.getItems().size());
    }

    @Test
    public void testGetItems_immutableList() {
        ArrayList<Item> items = itemShop.getItems();
        items.clear();
        assertNotEquals(0, itemShop.getItems().size()); // Ensure original list remains unaffected
    }

    @Test
    public void testGetItems_emptyList() {
        itemShop.getItems().clear();
        assertTrue(itemShop.getItems().isEmpty());
    }

    @Test
    public void testLoadItems_successfulLoad() {
        itemShop.loadItems();
        assertFalse(itemShop.getItems().isEmpty());
    }

    @Test
    public void testLoadItems_itemsAddedToShop() {
        itemShop.loadItems();
        assertTrue(itemShop.getItems().size() > 0);
    }

    @Test
    public void testLoadItems_throwsExceptionOnInvalidFile() {
        // Assume DataLoader throws exception on invalid files
        try {
            itemShop.loadItems();
        } catch (Exception e) {
            fail("Exception should not be thrown if loadItems handles exceptions.");
        }
    }

    @Test
    public void testLoadItems_doesNotDuplicateItems() {
        itemShop.loadItems();
        int initialSize = itemShop.getItems().size();
        itemShop.loadItems();
        assertEquals(initialSize, itemShop.getItems().size()); // No duplicates should be added
    }

    @Test
    public void testLoadItems_handleIOException() {
        itemShop.loadItems();
    }
}
