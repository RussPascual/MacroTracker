package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JournalTest {

    private Journal journal;
    private List<Double> weights;

    @BeforeEach
    public void setUp() {
        journal = new Journal(165);
        weights = new ArrayList<>();
    }

    @Test
    public void testJournal() {
        assertTrue(journal.getLogs().isEmpty());
        assertTrue(journal.getWeightTracker().isEmpty());
        assertEquals(165, journal.getGoal());
    }

    @Test
    public void testNextDay() {
        journal.nextDay(150.4);
        assertEquals(1, journal.getLogs().size());
        assertEquals(150.4, journal.getLog(1).getWeight());
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
    public void testGetLogNoInput() {
        int size = 10;
        for (int i = 0; i < size; i++) {
            journal.nextDay(150 + i);
        }
        assertEquals(journal.getLogs().get(size - 1), journal.getLog());
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
    public void testRemainingGoal() {
        journal.nextDay(150);
        assertEquals(15.0, journal.remainingGoal());

        int size = 10;
        for (int i = 0; i < size; i++) {
            journal.nextDay(150 + i);
        }
        assertEquals(165.0 - 159.0, journal.remainingGoal());
    }
}
