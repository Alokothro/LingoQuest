package lingo_quest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import lingoquestpackage.models.*;


/**
 * @author CADE
 */
public class SectionTest {
    private Section section = new Section();
    private ArrayList<Lesson> lessons;
    private Lesson lesson1;
    private Lesson lesson2;

    @BeforeEach
    public void setup() {
        section = new Section();
        lessons = new ArrayList<>();
        lesson1 = new Lesson();
        lesson2 = new Lesson();
        lesson1.setTotalPoints(50);
        lesson1.setPointsEarned(30);
        lesson2.setTotalPoints(100);
        lesson2.setPointsEarned(60);
        lessons.add(lesson1);
        lessons.add(lesson2);
        section.setLessons(lessons);
        //section = new Section("Section 1", UUID.randomUUID(), lessons);
        section.setName("Section 1");
        section.setID(UUID.randomUUID());
    }

    @AfterEach
    public void tearDown() {
        //section = null;
        section.getAllLessons().clear();
        lessons.clear();
    }

    // get total points
    @Test
    public void testGetTotalPointsWithLessons() {
        assertEquals(150, section.getTotalPoints());
    }

    @Test
    public void testGetTotalPointsEmptyLessons() {
        Section emptySection = new Section();
        assertEquals(0, emptySection.getTotalPoints());
    }

    @Test
    public void testGetTotalPointsSingleLesson() {
        ArrayList<Lesson> singleLesson = new ArrayList<>();
        singleLesson.add(lesson1);
        section.setLessons(singleLesson);
        assertEquals(50, section.getTotalPoints());
    }

    @Test
    public void testGetTotalPointsZeroPoints() {
        Lesson zeroLesson = new Lesson();
        zeroLesson.setTotalPoints(0);
        ArrayList<Lesson> zeroLessons = new ArrayList<>();
        zeroLessons.add(zeroLesson);
        section.setLessons(zeroLessons);
        assertEquals(0, section.getTotalPoints());
    }

    @Test
    public void testGetTotalPointsNegativePoints() {
        lesson1.setTotalPoints(-20);
        lessons.add(lesson1);
        section.setLessons(lessons);
        assertEquals(130, section.getTotalPoints());
    }

    // get points earned
    @Test
    public void testGetPointsEarnedWithLessons() {
        assertEquals(90, section.getPointsEarned());
    }

    @Test
    public void testGetPointsEarnedEmptyLessons() {
        Section emptySection = new Section();
        assertEquals(0, emptySection.getPointsEarned());
    }

    @Test
    public void testGetPointsEarnedSingleLesson() {
        ArrayList<Lesson> singleLesson = new ArrayList<>();
        singleLesson.add(lesson1);
        section.setLessons(singleLesson);
        assertEquals(30, section.getPointsEarned());
    }

    @Test
    public void testGetPointsEarnedZeroPoints() {
        lesson1.setPointsEarned(0);
        lesson2.setPointsEarned(0);
        section.setLessons(lessons);
        assertEquals(0, section.getPointsEarned());
    }

    @Test
    public void testGetPointsEarnedNegativePoints() {
        lesson1.setPointsEarned(-10);
        lessons.add(lesson1);
        section.setLessons(lessons);
        assertEquals(80, section.getPointsEarned());
    }

    // update section progress
    @Test
    public void testUpdateSectionProgressWithLessons() {
        section.updateSectionProgress();
        assertEquals(60.0, section.getSectionProgress());
    }

    @Test
    public void testUpdateSectionProgressEmptyLessons() {
        Section emptySection = new Section();
        emptySection.updateSectionProgress();
        assertEquals(0, emptySection.getSectionProgress());
    }

    @Test
    public void testUpdateSectionProgressSingleLesson() {
        ArrayList<Lesson> singleLesson = new ArrayList<>();
        singleLesson.add(lesson1);
        section.setLessons(singleLesson);
        section.updateSectionProgress();
        assertEquals(60.0, section.getSectionProgress());
    }

    @Test
    public void testUpdateSectionProgressZeroPointsEarned() {
        lesson1.setPointsEarned(0);
        lesson2.setPointsEarned(0);
        section.setLessons(lessons);
        section.updateSectionProgress();
        assertEquals(0, section.getSectionProgress());
    }

    @Test
    public void testUpdateSectionProgressAllPointsEarned() {
        lesson1.setPointsEarned(50);
        lesson2.setPointsEarned(100);
        section.setLessons(lessons);
        section.updateSectionProgress();
        assertEquals(100.0, section.getSectionProgress());
    }

    // set user access
    @Test
    public void testSetUserAccessTrue() {
        section.setUserAccess(true);
        assertTrue(section.getUserAccess());
    }

    @Test
    public void testSetUserAccessFalse() {
        section.setUserAccess(false);
        assertFalse(section.getUserAccess());
    }

    @Test
    public void testSetUserAccessMultipleCalls() {
        section.setUserAccess(true);
        section.setUserAccess(false);
        assertFalse(section.getUserAccess());
    }

    @Test
    public void testSetUserAccessInitialFalse() {
        assertFalse(section.getUserAccess());
    }

    @Test
    public void testSetUserAccessAfterSettingLessons() {
        section.setUserAccess(true);
        section.setLessons(lessons);
        assertTrue(section.getUserAccess());
    }

    
}
