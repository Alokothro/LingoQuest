package lingo_quest;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.crypto.Data;

public class ItemShop {

    private ArrayList<Item> items;
    private static ItemShop itemShop;

    /**
     * @author Cade Stocker
     * Private because ItemShop is a singleton
     * Creates the arraylist of type item
     */
    private ItemShop() {
        items = new ArrayList<Item>();
    }

    public void addItem(Item i) {
        if(i != null && items.contains(i) == false) {
            items.add(i);
        }
    }

    /**
     * @author Cade Stocker
     * @return the instance of itemShop
     */
    public static ItemShop getInstance() {
        if (itemShop == null) {
            itemShop = new ItemShop();
        }
        return itemShop;
    }

    /**
     * @author Wade Little
     * Calls on the user.buyItem(Item item) method/
     * @param user The user that is attempting to buy the item
     * @param item The item the user wants to buy
     * @return True if the user successfully purchases the item. False if they
     *         already own it or don't have enough coins.
     */
    public boolean purchaseItem(User user, UUID item) {
        return user.buyItem(item);
    }

    /**
     * @author Wade Little
     * Gets the selected items information in a String
     * @param item The item the user wants to view.
     * @return The items properties in a String
     */
    public String getItem(Item item) {
        return item.toString();
    }

    /**
     * @author cade
     * @param id
     * @return
     */
    public Item getItemByID(UUID id) {
        for (Item i : items) {
            if (i.getID().equals(id))
                return i;
        }
        System.out.println("Item doesn't exist.");
        return null;
    }

    /**
     * @author Wade Little
     * Gives the items in the item shop
     * @return The array list of items in the itemshop
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * @author CADE STOCKER
     * @param item
     * Once the item gets removed, return so that multiple
     * aren't accidentally removed
     */
    private void removeItem(Item item) {
        for (Item it : this.items) {
            if (it.equals(item)) {
                items.remove(it);
                return;
            }
        }
    }

    /**
     * @author Wade Little
     * Iterates over the item shop and displays all of the items into the
     * console.
     */
    public void displayItemShop() {
        for (Item item : items) {
            System.out.println(item.toString());
        }
    }

    /**
     * @author cade stocker
     * Calls the dataloader
     */
    public void loadItems() {
        try {
            this.items = DataLoader.loadItemShop(DataLoader.getItemFile());
        } catch (IOException | ParseException | org.json.simple.parser.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
