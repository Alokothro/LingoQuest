package lingo_quest;

import java.util.UUID;

/**
 * Represents an item with a name, description, price, and a unique identifier.
 */
public class Item {

    private String name; // The name of the item.
    private String description; // The description of the item.
    private int price; // The price of the item.
    private UUID id; // The unique identifier for the item.

    /**
     * Default constructor that initializes the item with a unique identifier.
     */
    public Item() {
        this.id = UUID.randomUUID(); // Assigns a new unique UUID upon creation.
    }

    /**
     * Constructs an item with specified name, description, and price.
     * 
     * @param name        The name of the item.
     * @param description The description of the item, detailing its features or usage.
     * @param price       The price of the item in integer format.
     */
    public Item(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Returns the name of the item.
     * 
     * @return The current name of this item.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the description of the item.
     * 
     * @return The current description of this item.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the price of the item.
     * 
     * @return The current price of this item in integer format.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Sets a new unique identifier for the item.
     * 
     * @param id The UUID to set as the identifier for this item.
     */
    public void setID(UUID id) {
        this.id = id;
    }

    /**
     * Returns the unique identifier of the item.
     * 
     * @return The UUID of this item.
     */
    public UUID getID() {
        return this.id;
    }

    /**
     * Returns a string representation of the item, including its name, description, and price.
     * 
     * @return A formatted string with the item's details.
     */
    @Override
    public String toString() {
        return "Name: " + name + ", Description: " + description + ", Price: " + price;
    }
}
