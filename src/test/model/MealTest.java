package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MealTest {

    private Meal meal;
    private FoodItem patty;
    private FoodItem buns;
    private FoodItem lettuce;
    private Macros macros;

    @BeforeEach
    public void setUp() {
        meal = new Meal("Burger");
        patty = new FoodItem("patty", new Macros(10, 15, 5));
        buns = new FoodItem("buns", new Macros(1, 10, 3));
        lettuce = new FoodItem("lettuce", new Macros(0, 5, 0));
        macros = meal.getMacros();
    }

    @Test
    public void testMeal() {
        assertEquals("burger", meal.getName());
        assertTrue(meal.getIngredients().isEmpty());
    }

    @Test
    public void testAddIngredient() {
        meal.addIngredient(buns);
        meal.addIngredient(patty);
        meal.addIngredient(patty);
        meal.addIngredient(lettuce);
        assertEquals(4, meal.getIngredients().size());

        macros.addMacros(patty.getMacros());
        macros.addMacros(patty.getMacros());
        macros.addMacros(buns.getMacros());
        macros.addMacros(lettuce.getMacros());
        assertEquals(macros.getProtein(), meal.getMacros().getProtein());
        assertEquals(macros.getCarbs(), meal.getMacros().getCarbs());
        assertEquals(macros.getFat(), meal.getMacros().getFat());
        assertEquals(macros.getCalories(), meal.getMacros().getCalories());
        assertEquals(macros, meal.getMacros());
    }
}
