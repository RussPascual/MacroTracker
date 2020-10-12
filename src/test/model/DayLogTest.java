package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DayLogTest {

    private DayLog dayLog;
    private Entry entry1;
    private Entry entry2;
    private Entry entry3;
    private Entry entry4;
    private FoodItem apple;
    private FoodItem rice;
    private Meal chickenBreastAndRice;
    private String note;

    @BeforeEach
    public void setUp() {
        dayLog = new DayLog(1, 150);

        apple = new FoodItem("apple", new Macros(0, 20, 0));
        FoodItem chickenBreast = new FoodItem("chicken breast", new Macros(20, 10, 2));
        rice = new FoodItem("rice", new Macros(4, 50, 2));

        chickenBreastAndRice = new Meal("chicken breast and rice");
        chickenBreastAndRice.addIngredient(chickenBreast);
        chickenBreastAndRice.addIngredient(rice);

        entry1 = new Entry(apple, 6);
        entry2 = new Entry(chickenBreastAndRice, 13);
        entry3 = new Entry(rice, 14);
        entry4 = new Entry(apple, 14);

        note = "This project is very cool";
    }

    @Test
    public void testDayLog() {
        assertTrue(dayLog.getEntries().isEmpty());
        assertEquals(1, dayLog.getDay());
        assertTrue(dayLog.getEntries().isEmpty());
        assertEquals(150, dayLog.getWeight());
    }

    @Test
    public void testAddEntry() {
        dayLog.addEntry(entry1);
        assertEquals(1, dayLog.getEntries().size());
        assertTrue(dayLog.getEntries().contains(entry1));
    }

    @Test
    public void testAddNote() {
        dayLog.addNote(note);
        assertEquals(1, dayLog.getNotes().size());
        assertTrue(dayLog.getNotes().contains(note));
    }

    @Test
    public void testGetFoodAtTimeHour() {
        dayLog.addEntry(entry1);
        dayLog.addEntry(entry2);
        dayLog.addEntry(entry3);
        dayLog.addEntry(entry4);

        assertTrue(dayLog.getFoodAtTime(0).isEmpty());

        assertTrue(dayLog.getFoodAtTime(6).contains(apple));
        assertEquals(1, dayLog.getFoodAtTime(6).size());

        assertTrue(dayLog.getFoodAtTime(14).contains(rice));
        assertTrue(dayLog.getFoodAtTime(14).contains(apple));
        assertEquals(2, dayLog.getFoodAtTime(14).size());
    }

    @Test
    public void testGetFoodAtTimeTimeOfDay() {
        dayLog.addEntry(entry1);
        dayLog.addEntry(entry2);
        dayLog.addEntry(entry3);
        dayLog.addEntry(entry4);

        assertTrue(dayLog.getFoodAtTime("Midnight").isEmpty());

        assertTrue(dayLog.getFoodAtTime("Morning").contains(apple));
        assertEquals(1, dayLog.getFoodAtTime("Morning").size());

        assertTrue(dayLog.getFoodAtTime("Afternoon").contains(rice));
        assertTrue(dayLog.getFoodAtTime("Afternoon").contains(apple));
        assertTrue(dayLog.getFoodAtTime("Afternoon").contains(chickenBreastAndRice));
        assertEquals(3, dayLog.getFoodAtTime("Afternoon").size());
    }

    @Test
    public void testTotalMacros() {
        Macros macros = new Macros();
        dayLog.addEntry(entry1);
        dayLog.addEntry(entry2);
        dayLog.addEntry(entry3);
        dayLog.addEntry(entry4);

        Macros anotherMacros = dayLog.totalMacros();

        macros.addMacros(entry1.getMacros());
        macros.addMacros(entry2.getMacros());
        macros.addMacros(entry3.getMacros());
        macros.addMacros(entry4.getMacros());

        assertEquals(macros.getProtein(), anotherMacros.getProtein());
        assertEquals(macros.getCarbs(), anotherMacros.getCarbs());
        assertEquals(macros.getFat(), anotherMacros.getFat());
        assertEquals(macros.getCalories(), anotherMacros.getCalories());
    }
}
