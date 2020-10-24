package persistence;

import model.DayLog;
import model.Food;
import model.Macros;
import model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
