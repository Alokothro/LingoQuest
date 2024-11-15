package lingo_quest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import lingoquestpackage.models.*;


/**
 * @author CADE
 */
public class UserTest {
    private User defaultUser = new User();
    private User parameterizedUser = new User();
    private UUID testItemID;
    private UUID testFriendID;
    private Lesson lesson = new Lesson();

    @BeforeEach
    public void setup() {
        defaultUser = new User();
        parameterizedUser = new User("testUser", "testPassword123");
        testItemID = UUID.randomUUID();
        testFriendID = UUID.randomUUID();
        lesson = new Lesson();
    }

    @AfterEach
    public void tearDown() {
        //defaultUser = null;
        //parameterizedUser = null;
    }

    // Tests for User Constructors
    @Test
    public void testDefaultConstructorUUIDNotNull() {
        assertNotNull(defaultUser.getUUID());
    }

    @Test
    public void testParameterizedConstructorSetsUsername() {
        assertEquals("testUser", parameterizedUser.getUsername());
    }

    @Test
    public void testParameterizedConstructorSetsPassword() {
        assertEquals("testPassword123", parameterizedUser.getPassword());
    }

    // setUserDictionary
    @Test
    public void testSetUserDictionaryValidDictionary() {
        Dictionary dictionary = new Dictionary();
        defaultUser.setUserDictionary(dictionary);
        assertEquals(dictionary, defaultUser.getUserDictionary());
    }

    @Test
    public void testSetUserDictionaryNull() {
        defaultUser.setUserDictionary(null);
        assertNull(defaultUser.getUserDictionary());
    }

    @Test
    public void testSetUserDictionaryUpdatesDictionaryID() {
        Dictionary dictionary = new Dictionary();
        defaultUser.setUserDictionary(dictionary);
        assertEquals(dictionary.getID(), defaultUser.getUserDictionaryID());
    }

    @Test
    public void testSetUserDictionaryMaintainsNonNullDictionary() {
        Dictionary dictionary = new Dictionary();
        defaultUser.setUserDictionary(dictionary);
        defaultUser.setUserDictionary(null);
        assertEquals(dictionary, defaultUser.getUserDictionary());
    }

    @Test
    public void testSetUserDictionaryReplacesPreviousDictionary() {
        Dictionary firstDictionary = new Dictionary();
        Dictionary secondDictionary = new Dictionary();
        defaultUser.setUserDictionary(firstDictionary);
        defaultUser.setUserDictionary(secondDictionary);
        assertEquals(secondDictionary, defaultUser.getUserDictionary());
    }

    // addCoins
    @Test
    public void testAddCoinsIncreasesCoinBalance() {
        defaultUser.addCoins(100);
        assertEquals(100, defaultUser.getCoinBalance());
    }

    @Test
    public void testAddCoinsIncreasesCoinsEarned() {
        defaultUser.addCoins(100);
        assertEquals(100, defaultUser.getCoinsEarned());
    }

    @Test
    public void testAddCoinsMultipleCalls() {
        defaultUser.addCoins(50);
        defaultUser.addCoins(50);
        assertEquals(100, defaultUser.getCoinBalance());
    }

    @Test
    public void testAddCoinsZeroCoins() {
        defaultUser.addCoins(0);
        assertEquals(0, defaultUser.getCoinBalance());
    }

    @Test
    public void testAddCoinsNegativeValue() {
        defaultUser.addCoins(-50);
        assertEquals(-50, defaultUser.getCoinBalance());
    }

    // set coins
    @Test
    public void testSpendCoinsDecreasesCoinBalance() {
        defaultUser.addCoins(100);
        defaultUser.spendCoins(50);
        assertEquals(50, defaultUser.getCoinBalance());
    }

    @Test
    public void testSpendCoinsExceedsBalance() {
        defaultUser.addCoins(50);
        defaultUser.spendCoins(100);
        assertEquals(-50, defaultUser.getCoinBalance());
    }

    @Test
    public void testSpendCoinsMultipleCalls() {
        defaultUser.addCoins(100);
        defaultUser.spendCoins(30);
        defaultUser.spendCoins(20);
        assertEquals(50, defaultUser.getCoinBalance());
    }

    @Test
    public void testSpendCoinsZeroAmount() {
        defaultUser.addCoins(100);
        defaultUser.spendCoins(0);
        assertEquals(100, defaultUser.getCoinBalance());
    }

    @Test
    public void testSpendCoinsNegativeAmount() {
        defaultUser.addCoins(100);
        defaultUser.spendCoins(-50);
        assertEquals(150, defaultUser.getCoinBalance());
    }

    // owns item
    @Test
    public void testOwnsItemUserOwnsItem() {
        defaultUser.getItems().add(testItemID);
        assertTrue(defaultUser.ownsItem(testItemID));
    }

    @Test
    public void testOwnsItemUserDoesNotOwnItem() {
        assertFalse(defaultUser.ownsItem(testItemID));
    }

    @Test
    public void testOwnsItemMultipleItems() {
        UUID item2 = UUID.randomUUID();
        defaultUser.getItems().add(testItemID);
        defaultUser.getItems().add(item2);
        assertTrue(defaultUser.ownsItem(item2));
    }

    @Test
    public void testOwnsItemNullID() {
        assertFalse(defaultUser.ownsItem(null));
    }

    @Test
    public void testOwnsItemEmptyItemsList() {
        defaultUser.getItems().clear();
        assertFalse(defaultUser.ownsItem(testItemID));
    }

    // buy item
    @Test
    public void testBuyItemSuccess() {
        ItemShop shop = ItemShop.getInstance();
        Item item = new Item("Test Item", "A sample item", 50);
        shop.addItem(item);
        defaultUser.addCoins(100);
        assertTrue(defaultUser.buyItem(item.getID()));
    }

    @Test
    public void testBuyItemNotEnoughCoins() {
        ItemShop shop = ItemShop.getInstance();
        Item item = new Item("Expensive Item", "Costly item", 200);
        shop.addItem(item);
        defaultUser.addCoins(100);
        assertFalse(defaultUser.buyItem(item.getID()));
    }

    @Test
    public void testBuyItemAlreadyOwned() {
        defaultUser.getItems().add(testItemID);
        assertFalse(defaultUser.buyItem(testItemID));
    }

    @Test
    public void testBuyItemNonexistentItem() {
        User defaultUser = new User();
        UUID randomID = UUID.randomUUID();
        assertFalse(defaultUser.buyItem(randomID));
    }

    @Test
    public void testBuyItemNullID() {
        User defaultUser = new User();
        assertFalse(defaultUser.buyItem(null));
    }

    // search friends
    @Test
    public void testSearchFriendsExistingFriend() {
        User testFriend = new User();
        UUID testFriendID = testFriend.getUUID();
        User defaultUser = new User();
        Users.getInstance().addUser(testFriend);
        Users.getInstance().addUser(defaultUser);
        defaultUser.addFriend(testFriendID);
        assertNotNull(defaultUser.searchFriends(testFriendID));
        Users.getInstance().getUsers().clear();
    }

    @Test
    public void testSearchFriendsNonexistentFriend() {
        UUID randomID = UUID.randomUUID();
        assertNull(defaultUser.searchFriends(randomID));
    }

    @Test
    public void testSearchFriendsNullID() {
        assertNull(defaultUser.searchFriends(null));
    }

    @Test
    public void testSearchFriendsMultipleFriends() {
        User defaultUser = new User();
        User friend2 = new User();
        UUID friend2ID = friend2.getUUID();
        User testFriend = new User();
        UUID testFriendID = testFriend.getUUID();
        Users.getInstance().addUser(defaultUser);
        Users.getInstance().addUser(testFriend);
        Users.getInstance().addUser(friend2);

        defaultUser.addFriend(testFriendID);
        defaultUser.addFriend(friend2ID);
        assertNotNull(defaultUser.searchFriends(friend2ID));

        Users.getInstance().getUsers().clear();
    }

    @Test
    public void testSearchFriendsEmptyFriendsList() {
        defaultUser.getFriendsList().clear();
        assertNull(defaultUser.searchFriends(testFriendID));
    }

    @Test
    public void testSendFriendRequest_successful() {
        User sender = new User("sender1111", "password1111");
        User recipient = new User("recipient1111", "password1111");
        Users.getInstance().addUser(recipient);
        Users.getInstance().addUser(sender);
        
        sender.sendFriendRequest(recipient.getUUID());
        
        assertTrue(recipient.getFriendRequests().contains(sender.getUUID()), "Recipient should have sender in friend requests");
    
        Users.getInstance().getUsers().clear();
    }
    
    @Test
    public void testSendFriendRequest_alreadyFriends() {
        User sender = new User("sender1111", "password1111");
        User recipient = new User("recipient1111", "password1111");
        Users.getInstance().addUser(recipient);
        Users.getInstance().addUser(sender);
        
        sender.addFriend(recipient.getUUID());
        sender.sendFriendRequest(recipient.getUUID());
        
        assertFalse(recipient.getFriendRequests().contains(sender.getUUID()), "Friend request should not be added if already friends");
        Users.getInstance().getUsers().clear();
    }
    
    @Test
    public void testSendFriendRequest_alreadySentRequest() {
        User sender = new User("sender1111", "password1111");
        User recipient = new User("recipient1111", "password1111");
        Users.getInstance().addUser(recipient);
        Users.getInstance().addUser(sender);
        
        sender.sendFriendRequest(recipient.getUUID());
        sender.sendFriendRequest(recipient.getUUID());
        
        assertEquals(1, recipient.getFriendRequests().size(), "Friend request should not duplicate if already sent");
        Users.getInstance().getUsers().clear();
    }
    
    @Test
    public void testSendFriendRequest_selfRequest() {
        User sender = new User("sender1111", "password1111");
        
        sender.sendFriendRequest(sender.getUUID());
        
        assertFalse(sender.getFriendRequests().contains(sender.getUUID()), "User should not be able to send a friend request to themselves");
    }
    
    @Test
    public void testSendFriendRequest_invalidUUID() {
        User sender = new User("sender1111", "password1111");
        
        sender.sendFriendRequest(UUID.randomUUID()); // Send to a non-existent UUID
        
        // Assuming recipient user does not exist, no friend request should be made.
        assertTrue(sender.getFriendRequests().isEmpty(), "Friend request should not be added for an invalid UUID");
    }
    
    @Test
    public void testAcceptFriendRequest_successful() {
        User sender = new User("sender1111", "password1111");
        User recipient = new User("recipient1111", "password1111");
        Users.getInstance().addUser(recipient);
        Users.getInstance().addUser(sender);
        
        sender.sendFriendRequest(recipient.getUUID());
        recipient.acceptFriendRequest(sender.getUUID());
        
        assertTrue(recipient.getFriendsList().contains(sender.getUUID()), "Recipient should have sender in friends list after accepting request");
        assertTrue(sender.getFriendsList().contains(recipient.getUUID()), "Sender should have recipient in friends list after request is accepted");
        Users.getInstance().getUsers().clear();
    }
    
    @Test
    public void testAcceptFriendRequest_noRequestExists() {
        User sender = new User("sender1111", "password1111");
        User recipient = new User("recipient1111", "password1111");
        
        recipient.acceptFriendRequest(sender.getUUID());
        
        assertFalse(recipient.getFriendsList().contains(sender.getUUID()), "Recipient should not have sender in friends list if no request exists");
    }
    
    @Test
    public void testAcceptFriendRequest_selfAccept() {
        User sender = new User("sender1111", "password1111");
        
        sender.acceptFriendRequest(sender.getUUID());
        
        assertFalse(sender.getFriendsList().contains(sender.getUUID()), "User should not be able to accept a friend request from themselves");
    }
    
    @Test
    public void testRejectFriendRequest_successful() {
        User sender = new User("sender1111", "password1111");
        User recipient = new User("recipient1111", "password1111");
        
        sender.sendFriendRequest(recipient.getUUID());
        recipient.rejectFriendRequest(sender.getUUID());
        
        assertFalse(recipient.getFriendRequests().contains(sender.getUUID()), "Friend request should be removed after rejection");
        assertFalse(recipient.getFriendsList().contains(sender.getUUID()), "Recipient should not have sender in friends list after rejecting request");
    }
    
    @Test
    public void testRejectFriendRequest_noRequestExists() {
        User sender = new User("sender1111", "password1111");
        User recipient = new User("recipient1111", "password1111");
        
        recipient.rejectFriendRequest(sender.getUUID());
        
        assertFalse(recipient.getFriendRequests().contains(sender.getUUID()), "Friend request list should remain unaffected if no request exists");
    }
    
    @Test
    public void testRejectFriendRequest_selfReject() {
        User sender = new User("sender1111", "password1111");
        
        sender.rejectFriendRequest(sender.getUUID());
        
        assertFalse(sender.getFriendRequests().contains(sender.getUUID()), "User should not be able to reject a friend request from themselves");
    }
    
    @Test
    public void testAddFriend_successful() {
        User user1 = new User("user1111", "password1111");
        User user2 = new User("user2222", "password1111");
        
        user1.addFriend(user2.getUUID());
        
        assertTrue(user1.getFriendsList().contains(user2.getUUID()), "User should successfully add another user to friends list");
    }
    
    @Test
    public void testAddFriend_alreadyFriends() {
        User user1 = new User("user1111", "password1111");
        User user2 = new User("user2222", "password1111");
        
        user1.addFriend(user2.getUUID());
        user1.addFriend(user2.getUUID()); // Add again
        
        assertEquals(1, user1.getFriendsList().size(), "Friends list should not contain duplicates");
    }
    
    @Test
    public void testAddFriend_nullUUID() {
        User user = new User("user1111", "password");
        
        user.addFriend(null);
        
        assertTrue(user.getFriendsList().isEmpty(), "Friends list should not add a null UUID");
    }
    
    @Test
    public void testAddFriend_selfFriend() {
        User user = new User("user", "password");
        
        user.addFriend(user.getUUID());
        
        assertFalse(user.getFriendsList().contains(user.getUUID()), "User should not be able to add themselves to their friends list");
    }
}
