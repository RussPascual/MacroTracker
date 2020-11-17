package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JournalTest {

    private Journal journal;

    @BeforeEach
    public void setUp() {
        journal = new Journal(165);
    }

    @Test
    public void testJournal() {
        assertTrue(journal.getLogs().isEmpty());
        assertTrue(journal.getWeightTracker().isEmpty());
        assertEquals(165, journal.getGoal());
    }

    @Test
    public void testSetGoal() {
        journal.setGoal(200);
        assertEquals(200, journal.getGoal());
    }

    @Test
    public void testNextDay() {
        journal.nextDay(150.4);
        assertEquals(1, journal.getLogs().size());
        assertEquals(150.4, journal.getLastLog().getWeight());
        assertEquals(1, journal.getLastLog().getDay());
        journal.nextDay(151);
        assertEquals(2, journal.getLogs().size());
        assertEquals(151, journal.getLastLog().getWeight());
        assertEquals(2, journal.getLastLog().getDay());
    }

    @Test
    public void testCopyPrevDay() {
        Entry testEntry = new Entry(new FoodItem("test", new Macros()), 1);
        Entry anotherTestEntry = new Entry(new FoodItem("another test", new Macros()), 2);

        journal.nextDay(150.5);
        journal.addEntry(testEntry);
        journal.addEntry(anotherTestEntry);
        journal.nextDay(150.8);

        assertTrue(journal.getLastLog().getEntries().isEmpty());
        assertFalse(journal.getLastLog().getEntries().contains(testEntry));
        assertFalse(journal.getLastLog().getEntries().contains(anotherTestEntry));

        journal.copyPrevDay();

        assertEquals(2, journal.getLastLog().getEntries().size());
        assertTrue(journal.getLastLog().getEntries().contains(testEntry));
        assertTrue(journal.getLastLog().getEntries().contains(anotherTestEntry));
    }

    @Test
    public void testGetLogWithDay() {
        int size = 10;
        for (int i = 0; i < size; i++) {
            journal.nextDay(150 + i);
        }
        assertEquals(journal.getLogs().get(2), journal.getLog(3));
    }

    @Test
    public void testGetLastLog() {
        int size = 10;
        for (int i = 0; i < size; i++) {
            journal.nextDay(150 + i);
        }
        assertEquals(journal.getLogs().get(size - 1), journal.getLastLog());
    }

    @Test
    public void testUpdateWeight() {
        journal.nextDay(151);
        assertEquals(151, journal.getLog(1).getWeight());

        journal.updateWeight(150.8);
        assertEquals(150.8, journal.getLog(1).getWeight());
    }

    @Test
    public void testViewProgress() {
        journal.nextDay(150);
        assertEquals(0.0, journal.viewProgress());

        int size = 10;
        for (int i = 0; i < size; i++) {
            journal.nextDay(150 + i);
        }
        assertEquals(((159.0 - 150.0) / (165.0 - 150.0)) * 100, journal.viewProgress());
    }

    @Test
    public void testRemainingGoalGoalGreaterThanStart() {
        journal.nextDay(150);
        assertEquals(15.0, journal.remainingGoal());

        int size = 10;
        for (int i = 0; i < size; i++) {
            journal.nextDay(150 + i);
        }
        assertEquals(165.0 - 159.0, journal.remainingGoal());
    }

    @Test
    public void testRemainingGoalStartGreaterThanGoal() {
        journal.nextDay(180);
        assertEquals(15.0, journal.remainingGoal());

        int size = 10;
        for (int i = 0; i < size; i++) {
            journal.nextDay(180 - i);
        }
        assertEquals(171.0 - 165.0, journal.remainingGoal());
    }

    @Test
    public void testAddEntry() {
        Entry testEntry = new Entry(new FoodItem("test", new Macros()), 1);

        journal.nextDay(150);
        assertTrue(journal.getLastLog().getEntries().isEmpty());
        journal.addEntry(testEntry);
        assertEquals(1, journal.getLastLog().getEntries().size());
    }
}
