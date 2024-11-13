package lingo_quest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.parser.ParseException;

/**
 * Manages dictionaries within the application, implementing singleton pattern to ensure a single instance.
 */
public class DictionaryManager {
    private ArrayList<Dictionary> dictionaries; // List of dictionaries managed by this class.
    private static DictionaryManager managerObject; // Singleton instance of DictionaryManager.
    private final UUID spanishDictionary = UUID.fromString("eeaaed42-a1be-4477-bc7c-2f6f2be2993b"); // UUID for the master Spanish dictionary.

    /**
     * Private constructor to prevent instantiation from outside the class and loads dictionaries.
     */
    private DictionaryManager() {
        this.dictionaries = new ArrayList<>();
        loadDictionaries();
    }

    /**
     * Provides access to the singleton instance of the DictionaryManager.
     * @return The singleton instance of DictionaryManager.
     */
    public static DictionaryManager getInstance() {
        if (managerObject == null) {
            managerObject = new DictionaryManager(); // Initialize the singleton instance if it doesn't exist.
        }
        return managerObject;
    }

    /**
     * Adds a dictionary to the list of managed dictionaries.
     * @param d The dictionary to add.
     */
    public void addDictionary(Dictionary d) {
        dictionaries.add(d);
    }

    /**
     * Retrieves the UUID of the master Spanish dictionary.
     * @return UUID of the Spanish dictionary.
     *  - I think we can remove this ~ cade
     */
    public UUID getSpanishDictionary() {
        return this.spanishDictionary;
    }

    public ArrayList<Dictionary> getDictionaries() {
        return this.dictionaries;
    }

    /**
     * @author cade
     * @param id
     */
    public void removeDictionary(UUID id) {
        for (Dictionary d : dictionaries) {
            if (d.getID().equals(id)) {
                dictionaries.remove(d);
                return;
            }
        }
    }

    /**
     * @author cade
     *         calls to the dataloader and lets it do the work
     */
    public void loadDictionaries() {
        try {
            ArrayList<Dictionary> list = DataLoader.loadDictionaries(DataLoader.getDictionaryFile());
            for (Dictionary d : list) {
                if (!dictionaries.contains(d)) {
                    this.addDictionary(d);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    /**
     * Saves all managed dictionaries to a data file.
     */
    public void saveDictionary() {
        ArrayList<Dictionary> saveList = new ArrayList();
        for(Dictionary d : dictionaries) {
            if(Users.getInstance().containsDictionary(d.getID()) || d.getID().equals(spanishDictionary)) {
                saveList.add(d);
            }
        }
        DataWriter.writeDictionaries(saveList, DataWriter.getDictionaryFile());
    }

    /**
     * Retrieves a dictionary by its UUID.
     * @param id UUID of the dictionary to retrieve.
     * @return The dictionary with the specified UUID or null if it doesn't exist.
     */
    public Dictionary getDictionaryByID(UUID id) {
        for (Dictionary d : this.dictionaries) {
            if (d.getID().equals(id))
                return d;
        }
        System.out.println(id.toString() + ": Dictionary doesn't exist.");
        return null;
    }

    /**
     * Retrieves a dictionary associated with a specific user.
     * @param u The user whose dictionary is to be retrieved.
     * @return The dictionary associated with the user.
     */
    public Dictionary getDictionaryByUser(User u) {
        return getDictionaryByID(u.getUserDictionaryID());
    }

    /**
     * Duplicates a dictionary identified by UUID.
     * @param id UUID of the dictionary to duplicate.
     * @return The duplicated dictionary or null if the original dictionary isn't found.
     */

    /**
     * @author cade
     * @param id
     * @return the duplicated dictionary
     */
    public Dictionary duplicateDictionary(UUID id) {
        Dictionary ret = new Dictionary();
        if (id != null && getDictionaryByID(id) != null) {
            for (Word w : getDictionaryByID(id).getWords()) {
                ret.addWord(w);
            }
            dictionaries.add(ret);
            //System.out.println("\n\n\nTEST added dictionary\n\n\n");
            return ret;
        }
        System.out.println("Dictionary not found");
        return null;
    }
}
