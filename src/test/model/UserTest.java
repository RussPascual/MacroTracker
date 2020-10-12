package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("Russell", 160, 175);
    }

    @Test
    public void testUser() {
        assertEquals("Russell", user.getName());
        assertEquals(160, user.getWeight());
        assertEquals(175, user.getJournal().getGoal());
        assertTrue(user.getSaved().getFoods().isEmpty());
        assertTrue(user.getJournal().getLogs().isEmpty());
        assertTrue(user.getJournal().getWeightTracker().isEmpty());
    }

    @Test
    public void testSetMacroGoals() {
        user.setMacroGoals(2800, 25, 55, 20);
        assertEquals((2800 * 0.25) / 4, user.getMacrosNeeded().getProtein());
        assertEquals((2800 * 0.55) / 4, user.getMacrosNeeded().getCarbs());
        assertEquals((2800 * 0.20) / 9, user.getMacrosNeeded().getFat());
        assertEquals(2800, user.getMacrosNeeded().getCalories());
    }
}
