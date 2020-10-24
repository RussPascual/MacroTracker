package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testSetName() {
        user.setName("Russ");
        assertEquals("Russ", user.getName());
    }

    @Test
    public void testSetMacroGoals() {
        user.setMacroGoals(2800, 25, 55, 20);
        assertEquals((2800 * 0.25) / 4, user.getMacrosNeeded().getProtein());
        assertEquals((2800 * 0.55) / 4, user.getMacrosNeeded().getCarbs());
        assertEquals((2800 * 0.20) / 9, user.getMacrosNeeded().getFat());
        assertEquals(2800, user.getMacrosNeeded().getCalories());
    }

    @Test
    public void testAddFavourite() {
        Food testFood = new FoodItem("test", new Macros(0,1,2));
        user.addFavourite(testFood);
        assertEquals(1, user.getSaved().getFoods().size());
        assertTrue(user.getSaved().getFoods().contains(testFood));
    }

    @Test
    public void testMetCalorieGoals() {
        user.getJournal().nextDay(160);
        user.setMacroGoals(720, 25, 50, 25);
        assertEquals(45, user.getMacrosNeeded().getProtein());
        assertEquals(90, user.getMacrosNeeded().getCarbs());
        assertEquals(20, user.getMacrosNeeded().getFat());
        assertEquals(720, user.getMacrosNeeded().getCalories());
        assertFalse(user.metCalorieGoals());
        Entry testEntry = new Entry(new FoodItem("test", new Macros(89, 90, 0)), 0);
        Entry anotherTestEntry = new Entry(new FoodItem("test", new Macros(1, 0, 0)), 0);
        user.getJournal().addEntry(testEntry);
        assertFalse(user.metCalorieGoals());
        user.getJournal().addEntry(anotherTestEntry);
        assertTrue(user.metCalorieGoals());
    }

    @Test
    public void testMetProteinGoals() {
        user.getJournal().nextDay(160);
        user.setMacroGoals(720, 25, 50, 25);
        assertEquals(45, user.getMacrosNeeded().getProtein());
        assertEquals(90, user.getMacrosNeeded().getCarbs());
        assertEquals(20, user.getMacrosNeeded().getFat());
        assertEquals(720, user.getMacrosNeeded().getCalories());
        assertFalse(user.metProteinGoals());
        Entry testEntry = new Entry(new FoodItem("test", new Macros(44, 0, 0)), 0);
        Entry anotherTestEntry = new Entry(new FoodItem("test", new Macros(1, 0, 0)), 0);
        user.getJournal().addEntry(testEntry);
        assertFalse(user.metProteinGoals());
        user.getJournal().addEntry(anotherTestEntry);
        assertTrue(user.metProteinGoals());
    }

    @Test
    public void testMetCarbGoals() {
        user.getJournal().nextDay(160);
        user.setMacroGoals(720, 25, 50, 25);
        assertEquals(45, user.getMacrosNeeded().getProtein());
        assertEquals(90, user.getMacrosNeeded().getCarbs());
        assertEquals(20, user.getMacrosNeeded().getFat());
        assertEquals(720, user.getMacrosNeeded().getCalories());
        assertFalse(user.metCarbGoals());
        Entry testEntry = new Entry(new FoodItem("test", new Macros(0, 89, 0)), 0);
        Entry anotherTestEntry = new Entry(new FoodItem("test", new Macros(0, 1, 0)), 0);
        user.getJournal().addEntry(testEntry);
        assertFalse(user.metCarbGoals());
        user.getJournal().addEntry(anotherTestEntry);
        assertTrue(user.metCarbGoals());
    }

    @Test
    public void testMetFatGoals() {
        user.getJournal().nextDay(160);
        user.setMacroGoals(720, 25, 50, 25);
        assertEquals(45, user.getMacrosNeeded().getProtein());
        assertEquals(90, user.getMacrosNeeded().getCarbs());
        assertEquals(20, user.getMacrosNeeded().getFat());
        assertEquals(720, user.getMacrosNeeded().getCalories());
        assertFalse(user.metFatGoals());
        Entry testEntry = new Entry(new FoodItem("test", new Macros(0, 0, 19)), 0);
        Entry anotherTestEntry = new Entry(new FoodItem("test", new Macros(0, 0, 1)), 0);
        user.getJournal().addEntry(testEntry);
        assertFalse(user.metFatGoals());
        user.getJournal().addEntry(anotherTestEntry);
        assertTrue(user.metFatGoals());
    }

    @Test
    public void testRemainingMacros() {
        user.getJournal().nextDay(160);
        user.setMacroGoals(720, 25, 50, 25);
        assertEquals(45, user.getMacrosNeeded().getProtein());
        assertEquals(90, user.getMacrosNeeded().getCarbs());
        assertEquals(20, user.getMacrosNeeded().getFat());
        assertEquals(720, user.getMacrosNeeded().getCalories());
        Entry testEntry = new Entry(new FoodItem("test", new Macros(5, 10, 15)), 0);
        user.getJournal().addEntry(testEntry);
        assertEquals(40, user.remainingMacros().getProtein());
        assertEquals(80, user.remainingMacros().getCarbs());
        assertEquals(5, user.remainingMacros().getFat());
        assertEquals(525, user.remainingMacros().getCalories());
        Entry anotherTestEntry = new Entry(new FoodItem("test", new Macros(40, 80, 5)), 0);
        user.getJournal().addEntry(anotherTestEntry);
        assertEquals(0, user.remainingMacros().getProtein());
        assertEquals(0, user.remainingMacros().getCarbs());
        assertEquals(0, user.remainingMacros().getFat());
        assertEquals(0, user.remainingMacros().getCalories());
        user.getJournal().addEntry(anotherTestEntry);
        assertEquals(0, user.remainingMacros().getProtein());
        assertEquals(0, user.remainingMacros().getCarbs());
        assertEquals(0, user.remainingMacros().getFat());
        assertEquals(0, user.remainingMacros().getCalories());
    }

    @Test
    public void testUpdateWeightEmptyJournal() {
        assertEquals(160, user.getWeight());
        user.updateWeight(160.5);
        assertEquals(160.5, user.getWeight());
    }

    @Test
    public void testUpdateWeightNonEmptyJournal() {
        assertEquals(160, user.getWeight());
        user.getJournal().nextDay(159.5);
        user.getJournal().nextDay(160.5);
        assertEquals(160.5, user.getJournal().getLastLog().getWeight());
        user.updateWeight(161.2);
        assertEquals(161.2, user.getWeight());
        assertEquals(161.2, user.getJournal().getLastLog().getWeight());
    }

    @Test
    public void testUpdateMacros() {
        user.updateMacros(1, 2, 3, 4);
        assertEquals(1, user.getMacrosNeeded().getProtein());
        assertEquals(2, user.getMacrosNeeded().getCarbs());
        assertEquals(3, user.getMacrosNeeded().getFat());
        assertEquals(4, user.getMacrosNeeded().getCalories());
    }
}
