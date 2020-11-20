package model;

import model.exceptions.NegativeInputException;
import model.exceptions.PercentageException;
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
    public void testSetWeight() {
        user.setWeight(100.0);
        assertEquals(100.0, user.getWeight());
    }

    @Test
    public void testSetMacrosNeeded() {
        Macros macros = new Macros(10, 20, 30);
        user.setMacrosNeeded(macros);
        assertEquals(macros, user.getMacrosNeeded());
    }

    @Test
    public void testSetJournal() {
        Journal journal = new Journal(175);
        user.setJournal(journal);
        assertEquals(journal, user.getJournal());
    }

    @Test
    public void testSetSaved() {
        Favourites saved = new Favourites();
        user.setSaved(saved);
        assertEquals(saved, user.getSaved());
    }

    @Test
    public void testSetMacroGoals() {
        try {
            user.setMacroGoals(2800, 25, 55, 20);
            assertEquals((2800 * 0.25) / 4, user.getMacrosNeeded().getProtein());
            assertEquals((2800 * 0.55) / 4, user.getMacrosNeeded().getCarbs());
            assertEquals((2800 * 0.20) / 9, user.getMacrosNeeded().getFat());
            assertEquals(2800, user.getMacrosNeeded().getCalories());
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            fail();
        }
    }

    @Test
    public void testSetMacroGoalsNotAddUpToOneHundred() {
        try {
            user.setMacroGoals(2800, 25, 50, 20);
            fail();
        } catch (PercentageException e) {
            // expected
        } catch (NegativeInputException e) {
            fail();
        }
    }

    @Test
    public void testSetMacroGoalsNegativeProtein() {
        try {
            user.setMacroGoals(2800, -25, 50, 75);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testSetMacroGoalsNegativeCarbs() {
        try {
            user.setMacroGoals(2800, 75, -50, 75);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testSetMacroGoalsNegativeFat() {
        try {
            user.setMacroGoals(2800, 60, 60, -20);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testSetMacroGoalsDoesNotAddOneHundred() {
        try {
            user.setMacroGoals(2800, 25, 50, 20);
            fail();
        } catch (PercentageException e) {
            // expected
        } catch (NegativeInputException e) {
            fail();
        }
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
        testSetNewMacroGoals();
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
        testSetNewMacroGoals();
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
        testSetNewMacroGoals();
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
        testSetNewMacroGoals();
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
        testSetNewMacroGoals();
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

    private void testSetNewMacroGoals() {
        try {
            user.getJournal().nextDay(160);
            user.setMacroGoals(720, 25, 50, 25);
            assertEquals(45, user.getMacrosNeeded().getProtein());
            assertEquals(90, user.getMacrosNeeded().getCarbs());
            assertEquals(20, user.getMacrosNeeded().getFat());
            assertEquals(720, user.getMacrosNeeded().getCalories());
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            fail();
        }
    }

    @Test
    public void testUpdateWeightNegativeWeight() {
        try {
            assertEquals(160, user.getWeight());
            user.updateWeight(-160.5);
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateWeightEmptyJournal() {
        try {
            assertEquals(160, user.getWeight());
            user.updateWeight(160.5);
            assertEquals(160.5, user.getWeight());
        } catch (NegativeInputException e) {
            fail();
        }
    }

    @Test
    public void testUpdateWeightNonEmptyJournal() {
        try {
            assertEquals(160, user.getWeight());
            user.getJournal().nextDay(159.5);
            user.getJournal().nextDay(160.5);
            assertEquals(160.5, user.getJournal().getLastLog().getWeight());
            user.updateWeight(161.2);
            assertEquals(161.2, user.getWeight());
            assertEquals(161.2, user.getJournal().getLastLog().getWeight());
        } catch (NegativeInputException e) {
            fail();
        }
    }

    @Test
    public void testUpdateMacrosNonNegativeInput() {
        try {
            user.updateMacros(1, 2, 3, 4);
            assertEquals(1, user.getMacrosNeeded().getProtein());
            assertEquals(2, user.getMacrosNeeded().getCarbs());
            assertEquals(3, user.getMacrosNeeded().getFat());
            assertEquals(4, user.getMacrosNeeded().getCalories());
        } catch (NegativeInputException e) {
            fail();
        }
    }

    @Test
    public void testUpdateMacrosNegativeProtein() {
        try {
            user.updateMacros(-1, 2, 3, 4);
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateMacrosNegativeCarbs() {
        try {
            user.updateMacros(1, -2, 3, 4);
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateMacrosNegativeFat() {
        try {
            user.updateMacros(1, 2, -3, 4);
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateMacrosNegativeCalories() {
        try {
            user.updateMacros(1, 2, 3, -4);
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateUser() {
        try {
            user.updateUser("test", 1, 2, 720, 25, 50, 25);
            assertEquals("test", user.getName());
            assertEquals(1, user.getWeight());
            assertEquals(2, user.getJournal().getGoal());
            assertEquals(45, user.getMacrosNeeded().getProtein());
            assertEquals(90, user.getMacrosNeeded().getCarbs());
            assertEquals(20, user.getMacrosNeeded().getFat());
            assertEquals(720, user.getMacrosNeeded().getCalories());
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            fail();
        }
    }

    @Test
    public void testUpdateUserNegativeWeight() {
        try {
            user.updateUser("test", -1, 2, 720, 25, 50, 25);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateUserNegativeGoal() {
        try {
            user.updateUser("test", 1, -2, 720, 25, 50, 25);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateUserNegativeCalories() {
        try {
            user.updateUser("test", 1, 2, -720, 25, 50, 25);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateUserNegativeProtein() {
        try {
            user.updateUser("test", 1, 2, 720, -25, 55, 70);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateUserNegativeCarbs() {
        try {
            user.updateUser("test", 1, 2, 720, 75, -55, 80);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateUserNegativeFat() {
        try {
            user.updateUser("test", 1, 2, 720, 65, 55, -20);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateUserPercentageDoesNotAddToOneHundred() {
        try {
            user.updateUser("test", 1, 2, 720, 25, 50, 20);
            fail();
        } catch (PercentageException e) {
            // expected
        } catch (NegativeInputException e) {
            fail();
        }
    }

    @Test
    public void testUpdateUserNegativeProteinAndPercentageDoesNotAddToOneHundred() {
        try {
            user.updateUser("test", 1, 2, 720, -25, 50, 25);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateUserNegativeCarbsAndPercentageDoesNotAddToOneHundred() {
        try {
            user.updateUser("test", 1, 2, 720, 25, -50, 25);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }

    @Test
    public void testUpdateUserNegativeFatAndPercentageDoesNotAddToOneHundred() {
        try {
            user.updateUser("test", 1, 2, 720, 25, 50, -25);
            fail();
        } catch (PercentageException e) {
            fail();
        } catch (NegativeInputException e) {
            // expected
        }
    }
}
