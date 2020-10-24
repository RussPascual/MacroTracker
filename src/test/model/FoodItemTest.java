package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FoodItemTest {

    private FoodItem food;
    private Macros macros;

    @BeforeEach
    public void setUp() {
        macros = new Macros(1, 5, 0);
        food = new FoodItem("bread slice", macros);
    }

    @Test
    public void testFoodItem() {
        assertEquals("bread slice", food.getName());
        assertEquals(macros, food.getMacros());
        assertFalse(food.isMeal());
        assertEquals(1, food.getProtein());
        assertEquals(5, food.getCarbs());
        assertEquals(0, food.getFat());
        assertEquals(24, food.getCalories());
    }

}
