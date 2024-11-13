package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonObject;


class DataWriter {
    private String filePath;
    private ItemShop itemShop;
    private Users users;
    private LanguageManager languageManager;
    //public static String userFile = "/data/Users.json";
    public static String userFileJunit = "/data/Users.json";
    public static String userFile = "correct_structure/src/main/resources/data/Users.json";
    public static String itemFileJunit = "/data/ItemShop.json";
    public static String itemFile = "correct_structure/src/main/resources/data/ItemShop.json";
    public static String placementFileJunit = "/data/PlacementTest.json";
    public static String placementFile = "correct_structure/src/main/resources/data/PlacementTest.json";
    public static String wordFileJunit = "/data/Word.json";
    public static String wordFile = "correct_structure/src/main/resources/data/Word.json";
    public static String dictionaryFileJunit = "/data/Dictionaries.json";
    public static String dictionaryFile = "correct_structure/src/main/resources/data/Dictionaries.json";
    public static String languageFileJunit = "/data/Languages2.json";
    public static String languageFile = "correct_structure/src/main/resources/data/Languages2.json";
    public static String studySheetFile = "correct_structure/studySheet/sheet.txt";
    public static String studySheetFileJunit = "/studySheet/sheet.txt";

    /**
     * @author cade
     * @return file path
     */
    public static String getUserFile() {
        return userFile;
    }

    private static String getFileWritingPath(String pathName, String junitPathName) {
        try {
            if(isJUnitTest()) {
                URI url = DataWriter.class.getResource(junitPathName).toURI();
                return url.getPath();
            } else {
                return pathName;
            }
        } catch (Exception e) {
            System.out.println("Difficulty getting resource path");
            return "";
        }
    }

    public static boolean isJUnitTest() {
        for(StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if(element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author cade
     * @return file path
     */
    public static String getLanguageFile() {
        return languageFile;
    }

    /**
     * @author cade
     * @return the file path
     */
    public static String getDictionaryFile() {
        return dictionaryFile;
    }

    /**
     * @author cade
     * @return file path
     */
    public static String getWordFile() {
        return wordFile;
    }

    /**
     * @author cade
     * @return string of file path
     */
    public static String getItemFile() {
        return itemFile;
    }

    /**
     * @author cade
     * @return string of file path
     */
    public static String getPlacementFile() {
        return placementFile;
    }

    public static void writeUsers(ArrayList<User> users, String file) {
        // Object that holds everything
        JSONObject root = new JSONObject();
        // the array of users
        JSONArray usersArray = new JSONArray();

        // serialize each user in users class
        for (User user : users) {
            usersArray.add(serializeUser(user));
        }

        // put all of the serialized users into the array
        root.put("users", usersArray);

        // write the whole user json file
        /*try (FileWriter writer = new FileWriter(file)) {
            writer.write(root.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // NEW VERSION OF WRITING SHOWN IN JUNIT VIDEO
        /*try {
            URI url = DataWriter.class.getResource(userFile).toURI();
            FileWriter writer = new FileWriter(url.getPath());

            writer.write(root.toJSONString());
            writer.flush();
            // close the writer
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        // VERSION 3
        try {
            String path = getFileWritingPath(userFile, userFileJunit);
            FileWriter writer = new FileWriter(path);

            writer.write(root.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // turns the user into a jsonObject
    private static JSONObject serializeUser(User user) {
        // make new json object
        JSONObject userJson = new JSONObject();
        // this should put these variables in order but isn't
        userJson.put("userID", user.getUUID().toString());
        if (user.getCurrentLanguage() != null)
            userJson.put("currentLanguageID", user.getCurrentLanguageID().toString());
        userJson.put("username", user.getUsername());
        userJson.put("password", user.getPassword());
        userJson.put("coinsEarned", user.getCoinsEarned());
        userJson.put("coinBalance", user.getCoinBalance());

        // Friends List
        JSONArray friendsArray = new JSONArray();
        // turns the list of UUIDs into a list of strings to be stored in the json
        for (UUID friendID : user.getFriendsList()) {
            friendsArray.add(friendID.toString());
        }
        userJson.put("friendsList", friendsArray);

        userJson.put("dictionaryID", user.getUserDictionaryID().toString());

        // Items
        JSONArray itemsArray = new JSONArray();
        for (UUID item : user.getItems()) {
            itemsArray.add(item.toString());
        }
        userJson.put("items", itemsArray);

        if (user.getWordOfTheDay() != null)
            userJson.put("wordOfTheDay", serializeWord(user.getWordOfTheDay()));

        // Languages
        JSONArray languagesArray = new JSONArray();
        for (UUID languageID : user.getLanguages()) {
            languagesArray.add(languageID.toString());
        }
        userJson.put("languages", languagesArray);

        return userJson;
    }

    public static void writeWords(ArrayList<Word> words, String file) {
        JSONObject root = new JSONObject();
        JSONArray wordsArray = new JSONArray();
        // turn all the words into json objects
        for (Word word : words) {
            wordsArray.add(serializeWord(word));
        }

        root.put("words", wordsArray);

        /*try (FileWriter writer = new FileWriter(file)) {
            writer.write(root.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*try {
            URI url = DataWriter.class.getResource(wordFile).toURI();
            FileWriter writer = new FileWriter(url.getPath());

            writer.write(root.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // version 3

        try {
            String path = getFileWritingPath(userFile, userFileJunit);
            FileWriter writer = new FileWriter(path);

            writer.write(root.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Cade Stocker
     * @param word
     * @return a jsonObject created from the word
     */
    private static JSONObject serializeWord(Word word) {
        JSONObject wordJson = new JSONObject();
        if (word.getLanguage() != null) {
            wordJson.put("language", word.getLanguage().toString());
            wordJson.put("timesPresented", word.getTimesPresented());
            wordJson.put("word", word.getWordinLanguage());
            wordJson.put("timesCorrect", word.getTimesCorrect());
            wordJson.put("userUnderstanding", word.getUserUnderstanding());
            wordJson.put("englishVersion", word.getEnglishVersion());
            wordJson.put("wordUUID", word.getWordUUID().toString());
            wordJson.put("lessonUUID", word.getLessonID().toString());
            // points added to words - 10/25
            wordJson.put("points", word.getPoints());
        }
        return wordJson;
    }

    /**
     * @author Cade
     * @param test
     * @return jsonObject created from a placement test
     */
    private static JSONObject serializePlacementTest(PlacementTest test) {
        JSONObject testJson = new JSONObject();

        testJson.put("correctAnswers", test.getCorrectAnswers());
        testJson.put("score", test.getScore());

        // return the json object
        return testJson;
    }

    public static void writeItems(ArrayList<Item> items, String file) {
        JSONObject root = new JSONObject();
        JSONArray itemsArray = new JSONArray();
        // turn each item into an object and add to jsonarray
        for (Item item : items) {
            JSONObject itemJson = new JSONObject();
            itemJson.put("name", item.getName());
            itemJson.put("description", item.getDescription());
            itemJson.put("price", item.getPrice());
            itemsArray.add(itemJson);
        }
        root.put("items",itemsArray);
            /*try (FileWriter writer = new FileWriter(file)) {
                writer.write(itemsArray.toJSONString());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            /*try {
                URI url = DataWriter.class.getResource(itemFile).toURI();
                FileWriter writer = new FileWriter(url.getPath());
    
                writer.write(root.toJSONString());
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            try {
                String path = getFileWritingPath(userFile, userFileJunit);
                FileWriter writer = new FileWriter(path);
    
                writer.write(root.toJSONString());
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        
    }

    public static void writeLanguages(ArrayList<Language> languages, String file) {
        // Object that holds everything
        JSONObject root = new JSONObject();
        // the array of users
        JSONArray langArray = new JSONArray();

        // serialize each user in users class
        for (Language l : languages) {
            langArray.add(serializeLanguage(l));
        }

        // put all of the serialized users into the array
        root.put("languages", langArray);

        // write the whole user json file
        /*try (FileWriter writer = new FileWriter(file)) {
            writer.write(root.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*try {
            URI url = DataWriter.class.getResource(languageFile).toURI();
            FileWriter writer = new FileWriter(url.getPath());

            writer.write(root.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            String path = getFileWritingPath(userFile, userFileJunit);
            FileWriter writer = new FileWriter(path);

            writer.write(root.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author cade
     * @param language to be serialized
     * @return json object of the language
     */
    public static JSONObject serializeLanguage(Language l) {
        JSONObject root = new JSONObject();
        root.put("userID", l.getUserID().toString());
        root.put("pointsEarned", l.getPointsEarned());
        root.put("totalPoints", l.getTotalPoints());
        root.put("progress", l.getProgress());
        root.put("answerStreak", l.getAnswerStreak());
        if(l.getLanguageName() == null)
        root.put("languageName", "Spanish");
        else
            root.put("languageName", l.getLanguageName().toString());
        root.put("languageID", l.getLanguageID().toString());
        // will implement placement test later
        root.put("PlacementTest", "c8d23cf7-c643-4988-ab6c-8f0fff97b934");
        JSONArray sections = new JSONArray();

        for (Object s : l.getSections()) {
            //System.out.println(s.getClass().getName());
        }

        for (Section s : l.getSections()) {
            sections.add(serializeSection(s));
        }
        root.put("sections", sections);
        return root;
    }

    /**
     * @author cade
     * @param section
     * @return object version of the section
     */
    public static JSONObject serializeSection(Section s) {
        JSONObject root = new JSONObject();
        root.put("sectionName", s.getName());
        root.put("sectionUUID", s.getID().toString());
        JSONArray lessonArray = new JSONArray();
        for (Lesson l : s.getAllLessons()) {
            lessonArray.add(serializeLesson(l));
        }
        root.put("lessons", lessonArray);
        return root;
    }

    /**
     * @author cade
     * @param l
     * @return object version of lesson
     */
    public static JSONObject serializeLesson(Lesson l) {
        JSONObject root = new JSONObject();
        root.put("lessonName", l.getLessonName());
        root.put("lessonUUID", l.getLessonID().toString());
        root.put("languageID", l.getLanguageID().toString());
        return root;
    }

    /**
     * @author cade
     * @param dictionaries
     * @param file
     */
    public static void writeDictionaries(ArrayList<Dictionary> dictionaries, String file) {
        // make the object that holds the whole file's content
        JSONObject root = new JSONObject();
        // make an array to hold each dictionary
        JSONArray jsonDictionaries = new JSONArray();
        // turn each dictionary into an object and add to array
        for (Dictionary d : dictionaries) {
            jsonDictionaries.add(serializeDictionary(d));
        }
        // put the array in root
        root.put("dictionaries", jsonDictionaries);
        // write to the file
        /*try (FileWriter writer = new FileWriter(file)) {
            writer.write(root.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*try {
            URI url = DataWriter.class.getResource(dictionaryFile).toURI();
            FileWriter writer = new FileWriter(url.getPath());

            writer.write(root.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            String path = getFileWritingPath(userFile, userFileJunit);
            FileWriter writer = new FileWriter(path);

            writer.write(root.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author cade
     * @param d
     * @return the dictionary jsonobject
     */
    public static JSONObject serializeDictionary(Dictionary d) {
        // dictionary object
        JSONObject root = new JSONObject();
        root.put("dictionaryID", d.getID().toString());
        // array of words
        JSONArray wordArray = new JSONArray();
        // make each word into jsonobject then add to array
        for (Word w : d.getWords()) {
            JSONObject obj = serializeWord(w);
            wordArray.add(obj);
        }
        // put the array into the dictionary object
        root.put("words", wordArray);
        return root;
    }

    /**
     * @author cade
     * @param words
     */
    public static void writeStudySheet(ArrayList<Word> words) {
        /*try {
            FileWriter myWriter = new FileWriter("correct_structure/studySheet/sheet.txt");
            myWriter.write("");
            for(Word w : words) {
                if(w.getTimesPresented() > 0) {
                myWriter.append(w.toString());
                myWriter.append("\n");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            String path = getFileWritingPath(studySheetFile, studySheetFileJunit);
            FileWriter writer = new FileWriter(path);
            writer.write("");
            for(Word w : words) {
                if(w.getTimesPresented() > 0) {
                    writer.append(w.toString());
                    // go to next line for readability
                    writer.append("\n");
                }
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}