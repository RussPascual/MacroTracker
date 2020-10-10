package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MacrosTest {

    private Macros macros;
    private Macros anotherMacros;

    @BeforeEach
    public void setUp() {
        macros = new Macros(10, 50, 4);
        anotherMacros = new Macros();
    }

    @Test
    public void testCalculateCalories() {
        assertEquals((10*4)+(50*4)+(4*9), macros.calculateCalories(10, 50, 4));
    }

    @Test
    public void testMacrosWithInput() {
        assertEquals(10, macros.getProtein());
        assertEquals(50, macros.getCarbs());
        assertEquals(4, macros.getFat());
        assertEquals((10*4)+(50*4)+(4*9), macros.getCalories());
    }

    @Test
    public void testMacrosNoInput() {
        assertEquals(0, anotherMacros.getProtein());
        assertEquals(0, anotherMacros.getCarbs());
        assertEquals(0, anotherMacros.getFat());
        assertEquals(0, anotherMacros.getCalories());
    }

    @Test
    public void testAddMacros() {
        anotherMacros = new Macros(10, 20, 30);
        anotherMacros.addMacros(macros);
        assertEquals(10 + 10, anotherMacros.getProtein());
        assertEquals(50 + 20, anotherMacros.getCarbs());
        assertEquals(4 + 30, anotherMacros.getFat());
        assertEquals((20*4)+(70*4)+(34*9), anotherMacros.getCalories());
    }
}
