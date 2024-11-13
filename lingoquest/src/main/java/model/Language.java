package lingo_quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Language {
    private User user;
    private UUID userID;
    private UUID languageID;
    private PlacementTest placementTest;
    private UUID placementTestID;
    private int pointsEarned;
    private int totalPoints;
    private double progress;
    private int placementScore;
    private ArrayList<Section> sections;
    private UUID dictionaryID;
    private Dictionary dictionary;
    private int answerStreak;
    private Languages languageName;
    private ArrayList<Section> sectionsComplete;
    private HashMap<Section, Boolean> sectionAccess;

    /**
     * @author Cade Stocker
     * Default constructor for language
     * Initializes all of the ArrayLists and HashMaps
     */
    public Language() {
        this.sections = new ArrayList<Section>();
        this.sectionsComplete = new ArrayList<Section>();
        this.sectionAccess = new HashMap<Section, Boolean>();
        this.languageID = UUID.randomUUID();
        this.placementTest = new PlacementTest();
    }

    /**
     * Parameterized constructor for language
     * each language needs a user so that the user can make progress as they
     * complete questions
     * 
     * @param user The user who is learning this language
     */
    public Language(User user) {
        this.sections = new ArrayList<Section>();
        this.sectionsComplete = new ArrayList<Section>();
        this.sectionAccess = new HashMap<Section, Boolean>();
        this.user = user;
        this.languageID = UUID.randomUUID();
        this.placementTest = new PlacementTest();
    }

    // Methods

    /**
     * @author CADE STOCKER
     * @param section
     * @return whether or not the user has access to a specific section
     * Sections should be locked based on points (a user must have
     * accumulated a certain amount
     * of points to gain access to a section)
     */
    public boolean hasAccessToSection(Section section) {
        return sectionAccess.get(section);
    }

    /**
     * @author cade
     * @return the placement test's id
     */
    public UUID getPlacementTestID() {
        return this.placementTestID;
    }

    /**
     * @author cade
     * @param id
     */
    public void setPlacementTestID(UUID id) {
        if (id != null)
            this.placementTestID = id;
    }

    /**
     * @author Cade Stocker
     * @param UUID that will be added to the language
     * UUID will be used to access the user who owns this object of
     * language
     * UUID should be used to find the user in the Users class
     */
    public void setLanguageID(UUID id) {
        this.languageID = id;
    }

    /**
     * @author cade
     * @return the language's dictionary
     */
    public Dictionary getDictionary() {
        return this.dictionary;
    }

    /**
     * @author cade
     * @param id
     */
    public void setDictionary(UUID id) {
        if (id != null) {
            this.dictionary = DictionaryManager.getInstance().getDictionaryByID(id);
            this.dictionaryID = this.dictionary.getID();
        }
    }

    /**
     * @author cade
     * @param l language type
     * Used for dataloader
     */
    public void setLanguageName(Languages l) {
        if (l != null) {
            this.languageName = l;
        } else {
            System.out.println("\n\n\nlanguage is null in setLanguagename\n\n\n");
        }
        // when more languages are created, this function would be expanded
        if (l.equals(Languages.SPANISH)) {
            this.dictionary = DictionaryManager.getInstance()
                    .duplicateDictionary(DictionaryManager.getInstance().getSpanishDictionary());
        } else {
            System.out.println("\n\n\nlanguage isnt spanish\n\n\n");
        }
    }

    /**
     * @author cade
     * @return the dictionary's id
     */
    public UUID getDictionaryID() {
        return this.dictionaryID;
    }

    /**
     * @author cade
     * @param id
     */
    public void setDictionaryID(UUID id) {
        if (id == null)
            return;
        this.dictionaryID = id;
        if (DictionaryManager.getInstance().getDictionaryByID(id) != null) {
            this.dictionary = DictionaryManager.getInstance().getDictionaryByID(id);
        }
    }

    /**
     * @author cade
     * @param progress
     */
    public void setUserProgress(double progress) {
        this.progress = progress;
    }

    public void setPlacementScore(int score) {
        this.placementScore = score;
    }

    /**
     * @author cade
     * @param id
     */
    public void setUserID(UUID id) {
        this.userID = id;
    }

    /**
     * @author cade
     * @return
     */
    public UUID getUserID() {
        return this.userID;
    }

    /**
     * @author Cade
     * @param points
     */
    public void setPointsEarned(int points) {
        this.pointsEarned = points;
    }

    /**
     * @author cade
     * @return the pointsEarned
     */
    public int getPointsEarned() {
        return this.pointsEarned;
    }

    /**
     * @author Cade Stocker
     * @return String of the Language's UUID
     * UUID is the unique identifier of this object of language
     */
    public UUID getLanguageID() {
        return this.languageID;
    }

    /**
     * 
     * @param points (total number of points possible in the language)
     */
    public void setTotalPoints(int points) {
        if (points >= 0)
            this.totalPoints = points;
    }

    /**
     * @author Cade Stocker
     * @return the total points held within the language
     */
    public int getTotalPoints() {
        return this.totalPoints;
    }

    /**
     * @author Cade Stocker
     * @return the progress the user has made in the language
     */
    public double getProgress() {
        this.updateProgress();
        return this.progress;
    }

    public User getUser() {
        return this.user;
    }

    /**
     * @author Cade Stocker
     * @return the enumeration of language (the type of language being learned)
     */
    public Languages getLanguageName() {
        return this.languageName;
    }

    public void setPlacementTest(PlacementTest pt) {
        if (pt != null)
            this.placementTest = pt;
    }

    /**
     * @author Cade Stocker
     * Increase the user's answer streak if they get a problem correct.
     */
    public void increaseAnswerStreak() {
        this.answerStreak++;
    }

    /**
     * @author cade
     * @param i
     */
    public void setAnswerStreak(int i) {
        if (i >= 0)
            this.answerStreak = i;
    }

    /**
     * @author Cade Stocker
     * Set the answer streak to 0 if the user gets a problem wrong.
     */
    public void resetAnswerStreak() {
        this.answerStreak = 0;
    }

    /**
     * @author Cade Stocker
     * @return the current answer streak of the user
     */
    public int getAnswerStreak() {
        return this.answerStreak;
    }

    /**
     * @author Cade Stocker
     * Finds the current progress by dividing the points earned
     * by the total amount of points of the language
     */
    private void updateProgress() {
        if (this.totalPoints == 0)
            this.progress = 0;
        else
            this.progress = this.pointsEarned / this.totalPoints;
    }

    /**
     * @author Cade Stocker
     * @return the arraylist of sections that have been completed
     */
    public ArrayList<Section> getSectionsComplete() {
        return this.sectionsComplete;
    }

    public ArrayList<Section> getSections() {
        return this.sections;
    }

    public void setSections(ArrayList<Section> sections) {
        if (sections != null)
            this.sections = sections;
    }

    /**
     * @author CADE STOCKER
     * Goes through sections and adds them to completed if they need to be
     * also returns if either of the lists hasn't been initialized.
     */
    public void setSectionsComplete() {
        if (this.sections == null || this.sectionsComplete == null) {
            return;
            // throw new IllegalStateException("Sections or sectionsComplete is not
            // initialized.");
        }

        for (Section section : this.sections) {
            // If the section is complete and isnt in the completed list, add it to
            // completed list
            if (section.getSectionComplete() && !this.sectionsComplete.contains(section)) {
                this.sectionsComplete.add(section);
            }
        }
    }

    /**
     * @author CADE STOCKER
     * @return whether or not the placement test has been taken.
     */
    public boolean takenPlacementTest() {
        return this.placementTest.getTaken();
    }

    /**
     * @author CADE STOCKER
     * @return arraylist of lessons
     * Goes through to see available sections
     * Goes through each available section and adds each available lesson
     */
    /*public ArrayList<Lesson> getAvailableLessons() {
        ArrayList<Lesson> lessons = new ArrayList();

        for (Section sec : this.getAvailableSections()) {
            for (Lesson lesson : sec.getAvailableLessons())
                lessons.add(lesson);
        }
        return lessons;
    }*/

    /**
     * @author CADE STOCKER
     * @return arraylist of sections that are available
     */
    public ArrayList<Section> getAvailableSections() {
        ArrayList<Section> sections = new ArrayList();
        for (Section sec : this.sections) {
            if (this.sectionAccess.get(sec)) {
                sections.add(sec);
            }
        }
        return sections;
    }
}
