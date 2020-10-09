package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MacrosTest {

    private Macros macros;

    @BeforeEach
    public void setUp() {
        macros = new Macros(10, 50, 4);
    }

    @Test
    public void testCalculateCalories() {
        assertEquals((10*4)+(50*4)+(4*9), macros.calculateCalories(10, 50, 4));
    }

    @Test
    public void testMacros() {
        assertEquals(10, macros.getProtein());
        assertEquals(50, macros.getCarbohydrates());
        assertEquals(4, macros.getFat());
        assertEquals((10*4)+(50*4)+(4*9), macros.getCalories());
    }
}
