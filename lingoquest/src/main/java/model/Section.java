package lingo_quest;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a section within a course, containing multiple lessons and tracking user progress and achievements.
 */
public class Section {
    private ArrayList<Lesson> lessons;   // List of lessons within this section.
    private boolean userAccess;          // Indicates if the user has access to this section.
    private double sectionProgress;      // Percentage of the section completed by the user.
    private int pointsEarned;            // Points earned by the user in this section.
    private int totalPoints;             // Total points available in this section.
    private int coinValue;               // Coins awarded upon completing this section.
    private String sectionName;          // Name of the section.
    private boolean sectionComplete;     // Indicates if the section has been completed.
    private UUID ID;                     // Unique identifier for the section.
    private int pointRequirement;        // Points required to unlock the next section or reward.

    /**
     * Default constructor initializes a new section with default values.
     */
    public Section() {
        lessons = new ArrayList<>();
        userAccess = false;   // Default access is false until specified.
        sectionProgress = 0;  // Initial progress is 0%.
        pointsEarned = 0;     // No points earned at initialization.
        totalPoints = 0;      // Total points are calculated based on lessons.
        coinValue = 0;        // No coins initially assigned.
        // sectionComplete = false;
    }
    /**
     * Constructs a section with a specified name, UUID, and a list of lessons.
     * Initializes section-specific details and calculates initial values based on the provided lessons.
     *
     * @param sectionName The name of the section.
     * @param sectionUUID The unique identifier for the section.
     * @param lessons The list of lessons that will be part of this section.
     */
    public Section(String sectionName, UUID sectionUUID, ArrayList<Lesson> lessons) {
        this.sectionName = sectionName;
        this.lessons = lessons;
        this.ID = sectionUUID;
        this.sectionProgress = getSectionProgress();
        this.pointsEarned = getPointsEarned();
        this.totalPoints = getTotalPoints();
        this.coinValue = 1000;
    }
    /**
     * Calculates the total points that can be earned from all lessons in the section.
     *
     * @return The sum of points from all lessons within the section.
     */
    public int getTotalPoints() {
        int totalPoints = 0;
        for (Lesson lesson : lessons) {
            totalPoints += lesson.getTotalPoints();
        }
        return totalPoints;
    }

    /**
     * @author cade
     * @param n
     */
    public void setName(String n) {
        this.sectionName = n;
    }

    /**
     * @author cade
     * @return the sectionName
     */
    public String getName() {
        return this.sectionName;
    }

    public int getPointsEarned() {
        int pointsEarned = 0;
        for (Lesson lesson : lessons) {
            pointsEarned += lesson.getPointsEarned();
        }
        return pointsEarned;
    }

    /**
     * changed to be called updateSectionProgress
     */
    public void updateSectionProgress() {
        this.sectionProgress = getSectionProgress();
    }

    public double getSectionProgress() {
        int totalPoints = 0;
        int earnedPoints = 0;
        for (Lesson lesson : lessons) {
            totalPoints += lesson.getTotalPoints();
            earnedPoints += lesson.getPointsEarned();
        }
        return (earnedPoints / totalPoints) * 100;
    }

    /**
     * @author cade
     * @param int of coin balance
     */
    public void setCoinValue(int cb) {
        this.coinValue = cb;
    }

    /**
     * @author cade
     * @return the coin value
     */
    public int getCoinValue() {
        return this.coinValue;
    }

    public boolean getUserAccess() {
        return this.userAccess;
    }

    /**
     * @author cade
     * @param access
     */
    public void setUserAccess(boolean access) {
        this.userAccess = access;
    }

    public boolean getSectionComplete() {
        return this.sectionComplete;
    }

    // what is this method for? - cade
    public Lesson getLesson(Lesson lesson) {
        return lesson;
    }

    /**
     * @author cade
     * @return the list of lessons
     */
    public ArrayList<Lesson> getAllLessons() {
        return this.lessons;
    }

    /**
     * @author cade
     * @param l
     */
    public void setLessons(ArrayList<Lesson> l) {
        if (l != null)
            this.lessons = l;
    }

    /**
     * @author cade
     * @param p
     */
    public void setSectionProgress(double p) {
        this.sectionProgress = p;
    }

    /**
     * @author Cade Stocker
     * @param id the UUID to be set
     *           if the id needs to be a new UUID, the new UUID will be
     *           created in another class and then sent here to be set.
     */
    public void setID(UUID id) {
        this.ID = id;
    }

    /**
     * @author Cade Stocker
     * @return the UUID of the section
     */
    public UUID getID() {
        return this.ID;
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setSectionComplete(boolean complete) {
        this.sectionComplete = complete;
    }

}
