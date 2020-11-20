package persistence;

import model.*;
import model.exceptions.NegativeInputException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonTest {

    protected void checkMacros(double calories, double protein, double carbs, double fat, Macros macros) {
        assertEquals(calories, macros.getCalories());
        checkMacros(protein, carbs, fat, macros);
    }

    protected void checkMacros(double protein, double carbs, double fat, Macros macros) {
        assertEquals(protein, macros.getProtein());
        assertEquals(carbs, macros.getCarbs());
        assertEquals(fat, macros.getFat());
    }

    protected void checkLogs(int day, int entriesSize, int notesSize, double weight, DayLog log) {
        assertEquals(day, log.getDay());
        assertEquals(entriesSize, log.getEntries().size());
        assertEquals(notesSize, log.getNotes().size());
        assertEquals(weight, log.getWeight());
    }

    protected void checkFood(String name, boolean isMeal, double protein, double carbs, double fat, Food food) {
        assertEquals(name, food.getName());
        assertEquals(isMeal, food.isMeal());
        checkMacros(protein, carbs, fat, food.getMacros());
    }

    protected User generalUser() {
        User user = new User("Russell", 164, 180);
        try {
            user.updateMacros(90, 180, 40, 1440);
        } catch (NegativeInputException e) {
            fail();
        }
        generalJournal(user.getJournal());
        generalSaved(user.getSaved());
        return user;
    }

    protected void generalJournal(Journal journal) {
        journal.nextDay(162);
        Entry eggEntry = new Entry(new FoodItem("egg", new Macros(10, 8, 0)), 8);
        Meal friedRice = new Meal("fried rice");
        friedRice.addIngredient(new FoodItem("rice", new Macros(2, 20, 0)));
        friedRice.addIngredient(new FoodItem("pork", new Macros(12, 20, 4)));
        Entry friedRiceEntry = new Entry(friedRice, 12);
        journal.addEntry(eggEntry);
        journal.addEntry(friedRiceEntry);
        journal.nextDay(164);
        journal.getLastLog().addNote("fill later");
        journal.getLastLog().addNote("eat a lot!");
    }

    protected void generalSaved(Favourites saved) {
        FoodItem banana = new FoodItem("banana", new Macros(0, 15, 0));
        Meal spaghettiAndMeatballs = new Meal("spaghetti and meatballs");
        spaghettiAndMeatballs.addIngredient(new FoodItem("noodles", new Macros(2, 25, 0)));
        spaghettiAndMeatballs.addIngredient(new FoodItem("spaghetti sauce", new Macros(5, 15, 2)));
        spaghettiAndMeatballs.addIngredient(new FoodItem("meatballs", new Macros(12, 10, 3)));
        saved.addFood(banana);
        saved.addFood(spaghettiAndMeatballs);
    }

    protected User noEntriesOrNotes() {
        User user = new User("Russell", 164, 180);
        try {
            user.updateMacros(90, 180, 40, 1440);
        } catch (NegativeInputException e) {
            fail();
        }
        Journal journal = user.getJournal();
        journal.nextDay(162);
        return user;
    }

    protected User noLogsOrSaved() {
        User user = new User("Russell", 164, 180);
        try {
            user.updateMacros(90, 180, 40, 1440);
        } catch (NegativeInputException e) {
            fail();
        }
        return user;
    }
}
