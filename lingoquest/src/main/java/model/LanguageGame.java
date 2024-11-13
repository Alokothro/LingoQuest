package lingo_quest;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.HashMap;
/**
 * The main class that drives the language learning game, handling user interactions, 
 * language sessions, and resource management.
 */
class LanguageGame {
    private Users userList;
    private User user;
    private ItemShop itemShop;
    private DictionaryManager dictionaryMan;
    private Dictionary userDictionary;
    private Language currentLanguage;
    private LanguageManager languageManager;
    private LeaderBoard leaderboard;
    private Word userAnswer;
    private QuestionCreator questionCreator;

    /**
     * Initializes the game, setting up necessary components and loading all necessary data.
     * Outputs an initial message to the terminal.
     * 
     * @throws Exception if an error occurs during initialization.
     */
    public LanguageGame() throws Exception {
        // Speak prints out a message to terminal when connected, so we call it here to display
        // it before our questions get displayed
        speak("");
        this.leaderboard = LeaderBoard.getInstance();
        this.userList = Users.getInstance();
        this.dictionaryMan = DictionaryManager.getInstance();
        this.itemShop = ItemShop.getInstance();
        this.languageManager = LanguageManager.getInstance();
        this.questionCreator = new QuestionCreator();
        this.loadAll();
    }

    /**
     * @author cade
     * @return users singleton
     */
    public Users getUsers() {
        return this.userList;
    }

    public void setUsers(Users u) {
        if(u != null) {
            this.userList = u;
        }
    }

    /**
     * @author cade
     * @return the dictionary singleton
     */
    public DictionaryManager getDictionaryManager() {
        return this.dictionaryMan;
    }

    /**
     * @author cade
     * @param dm (dictionarymanager to be assigned)
     */
    public void setDictionaryManager(DictionaryManager dm) {
        if(dm != null) {
            this.dictionaryMan = dm;
        }
    }

    /**
     * @author cade
     * @param lb leaderboard
     */
    public void setLeaderboard(LeaderBoard lb) {
        if(lb != null)
            this.leaderboard = lb;
    }

    /**
     * @author cade
     * @return the leaderboard object
     */
    public LeaderBoard getLeaderBoard() {
        return this.leaderboard;
    }

    /**
     * @author cade
     * @return sorted list of users
     * easy way to get the list of users that have been sorted
     */
    public ArrayList<User> getLeaderboardUsers() {
        return this.leaderboard.getUsers();
    }

    /**
     * @author cade
     * @return item singleton
     */
    public ItemShop getItemShop() {
        return this.itemShop;
    }

    /**
     * @author cade
     * @param iS (item shop to be assigned)
     */
    public void setItemShop(ItemShop iS) {
        if(iS != null) {
            this.itemShop = iS;
        }
    }

    /**
     * @author cade
     * @return question creator
     */
    public QuestionCreator getQuestionCreator() {
        return this.questionCreator;
    }

    /**
     * @author cade
     * @param qC (question creator to be assigned)
     */
    public void setQuestionCreator(QuestionCreator qC) {
        if(qC != null) {
            this.questionCreator = qC;
        }
    }

    public void setLanguageManager(LanguageManager lm) {
        if(lm != null) {
            this.languageManager = lm;
        }
    }

    /**
     * Creates a new user account, logs them in, and sets their initial language to Spanish.
     * If a user is already logged in or the username exists, it outputs an error.
     * 
     * @param username The desired username.
     * @param password The desired password.
     */
    public void createUser(String username, String password) {
        if (this.user != null) {
            System.out.println("Someone is already logged in");
            return;
        }
        if (this.userList.containsUsername(username)) {
            System.out.println("Username already exists.");
            return;
        }
        User createdUser = new User(username, password);
        this.user = createdUser;
        userList.createUser(username, password);

        //this.startLanguage(Languages.SPANISH);
        // attempt to fix bug with practice low understanding
        this.user.setCurrentLangauge(this.startLanguage(Languages.SPANISH));

        System.out.println("Successfully Created Account");
    }

    public void setUser(User u) {
        if(u != null) {
            this.logout();
            this.user = u;
        }
    }
    
    /**
     * Loads all configurations and user-related data when the game starts.
     * This includes user profiles, language settings, and item shop data.
     * @throws Exception if there is an issue loading any data
     */
    public void loadAll() throws Exception {
        userList.loadUsers();
        languageManager.loadLanguages();
        itemShop.loadItems();


        // load object the user needs by their UUIDs
        if(userList != null && userList.getUsers().isEmpty() == false) {
        for (User u : userList.getUsers()) {
            // use the UUID's to access the language from languagemanager
            u.setCurrentLanguage(languageManager.getLanguageByID(u.getCurrentLanguageID()));
            // figure out how to expand this for when we have multiple dictionaries - cade
            u.setUserDictionary(dictionaryMan.getDictionaryByID(u.getUserDictionaryID()));
            // load all the users into the leaderboard
            leaderboard.addUser(u);
        }
        }

    }
    
    /**
     * Practices words with a user understanding below a specific threshold. It sets up a
     * practice lesson that targets these words specifically to enhance learning.
     */
    public void practiceLowUnderstanding() {
        // ERROR HAPPENING HERE WHEN A USER DOESNT HAVE ENOUGH LOW-UNDERSTANDING WORDS
        // CREATES AN INFINITE LOOP TODO
        Lesson practice = new Lesson();
        
        if(this.user.getCurrentLanguage() == null) {
            System.out.println("User's current language is null in practiceLowUnderstanding in LanguageGame");
            return;
        }
        practice.setLanguageID(this.user.getCurrentLanguage().getLanguageID());
        practice.setTopicWordsByList(this.user.getUserDictionary().getWordsByUnderstanding(70.0));
        user.currentLesson = practice;
    }

    /**
     * @author cade stocker
     * @return whether there is a user stored in this.user
     */
    public boolean hasCurrentUser() {
        return (this.user != null);
    }

    /**
     * @author cade
     * @param lang
     * @return
     */
    public Language startLanguage(Languages lang) {
        // make sure there's a user logged in
        if (this.getUser() == null) {
            System.out.println("Cannot start language without user.");
            return null;
        }
        Language l = new Language();
        // add the new language to the singleton
        languageManager.addLanguage(l);
        // put the user's id into the language
        l.setUserID(this.getUser().getUUID());
        // put the language's id into user
        this.getUser().addLanguage(l);
        this.getUser().setCurrentLangauge(l);
        // make a duplicate of spanish dictionary to track the user's progress
        this.getUser().setUserDictionary(DictionaryManager.getInstance().duplicateDictionary(DictionaryManager.getInstance().getSpanishDictionary()));
        l.setDictionary(this.user.getUserDictionaryID());
        l.setDictionaryID(this.user.getUserDictionaryID());
        // return the language
        // set the type of language it is (this assigns the master dictionary to the
        // language object)
        if (lang == null) 
            l.setLanguageName(Languages.SPANISH);
        else
            l.setLanguageName(lang);

        this.currentLanguage = l;
        return l;
    }

    /**
     * @author Wade Little
     * Sets the language games current language as well as the
     * languagemanagers
     * @param language THe language you want to work on
     */
    public void setCurrentLanguage(Language language) {
        this.currentLanguage = language;
        languageManager.setCurrentLanguage(language);
    }

    /**
     * @author Wade Little
     * Checks the userlist for the entered username and password and returns
     * a valid user or null user
     * @param username The username that the user is trying to login with
     * @param password The password the user is trying to login with
     * @return A valid User or null if it isn't a valid user
     */
    public void login(String username, String password) {
        if(userList.getUser(username, password) == null) {
            System.out.println("Null user" + username + " " + password);
            return;
        }
        this.user = userList.getUser(username, password);

        // attempt to set topic words TODO move to individual class
        for (Language l : languageManager.getLanguages()) {
            for (Section sec : l.getSections()) {
                for (Lesson les : sec.getAllLessons()) {
                    les.setTopicWords(this.user);
                }
            }
        }
    }

    /**
     * @author Wade Little
     * Saves the users and sets the user to null
     */
    public void logout() {
        //System.out.println("in logout");
        this.userList.saveUsers();
        this.dictionaryMan.saveDictionary();
        this.languageManager.saveLanguages();
        // set current user to null
        this.user = null;
        System.out.println("Successfully logged out");
    }

    public Dictionary getLanguageDictionary(String languageUUID) {
        return languageManager.getLanguageByID(UUID.fromString(languageUUID)).getDictionary();
    }

    public ArrayList<Language> getAllLanguages() {
        return languageManager.getLanguages();
    }

    public boolean openSection(Section section) {
        return false;
    }

    public boolean startLesson(Lesson lesson) {
        return false;
    }

    public boolean startPlacementTest(PlacementTest test) {
        return false;
    }

    /**
     * @author cade stocker
     * @param lesson
     * @return
     */
    public double getLessonProgress(Lesson lesson) {
        return lesson.getLessonProgress();
    }

    public ArrayList<Lesson> getBookMarkedLessons(User user) {
        return null;
    }

    public ArrayList<Section> getAvailableSections() {
        return null;
    }

    public ArrayList<Lesson> getAvailableLessons() {
        return null;
    }

    /**
     * Sets the user answer
     * 
     * @param userAnswer
     */
    public void setUserAnswer(Word userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Word getUserAnswer() {
        return this.userAnswer;
    }

    public User getUser() {
        return user;
    }

    public LanguageManager getLanguageManager() {
        return this.languageManager;
    }

    /**
     * @author Wade Little
     * This class runs itemshop.displayItemShop() to view the item shop.
     */
    public void checkItemShop() {
        itemShop.displayItemShop();
        ;
    }
    /**
     * Selects a language for the user based on a UUID. Sets the user's current language to the one identified by the UUID.
     * @param languageUUID The UUID of the language to be set as the current language.
     */
    public void pickALanguageByUUID(UUID languageUUID) {
        user.setCurrentLangauge(languageManager.getInstance().getLanguageByID(languageUUID));
    }
    /**
     * Generates a study sheet for the user based on words they are less familiar with.
     * This method targets words where the user's understanding is below 50%.
     */
    public void makeStudySheet() {
        // Write all words that aren't understood well to the study sheet
        DataWriter.writeStudySheet(user.getUserDictionary().getWordsByUnderstanding(50.0));
    }
    /**
     * Uses the narrator to play a sound for a given string. This is typically used to
     * pronounce words or sentences in the language learning context.
     * @param s The string to be spoken.
     */
    public void speak(String s) {
        Narriator.playSound(s);

    }
    /**
     * Selects a section for the user based on a UUID. Updates the user's current section.
     * @param sectionUUID The UUID of the section to be set as the current section.
     */
    public void pickASection(UUID sectionUUID) {
        user.currentSection = languageManager.getSectionByID(sectionUUID);
        System.out.println("You switched to section " + user.currentSection.getName());
    }
    /**
     * Selects a lesson for the user based on a UUID. Updates the user's current lesson.
     * @param lessonUUID The UUID of the lesson to be set as the current lesson.
     */
    public void pickALesson(UUID lessonUUID) {
        user.currentLesson = languageManager.getLessonByID(lessonUUID, user);
        
        System.out.println("You switched to lesson " + user.currentLesson.getLessonName());
    }
    /**
     * Generates and displays a question from the current lesson.
     */
    public void getAQuestion() {
        Question question = questionCreator.createQuestion(user.currentLesson);
        user.currentLesson.currentQuestion = question;
        System.out.println(question.toString());
        speak(question.toString());
    }
    /**
     * Collects the user's answer from the console and checks if it is correct.
     * @param k The scanner to read the user's input.
     */
    public void answerQuestion(Scanner k) {
        System.out.println("Please enter your answer");
        String userAnswer = k.nextLine().toLowerCase().trim();
        user.currentLesson.currentQuestion.setUserAnswer(userAnswer);
        user.currentLesson.currentQuestion.isCorrect(user);
    }
    /**
     * Displays the progress of the user for the current lesson. Shows the percentage of the lesson completed
     * and details about words learned.
     */
    public void getProgressScreen() {
        System.out.println("Here is your progress on the words in the current lesson");
        System.out.println("The lesson you are currently working on is " + user.currentLesson.getLessonName());
        System.out.println("You have completed " + String.format("%.2f", user.currentLesson.getLessonProgress())
                + "% of the lesson");
        for (Word w : user.currentLesson.topicWords) {
            if (w.getTimesPresented() > 0) {
                System.out.println(w.toString());
            }
        }
        //System.out.println("\n\nCurrent Language Progress: " + user.getCurrentLanguageProgress());
    }

}
