package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntryTest {

    private Entry entry;
    private Food food;
    private Macros macros;

    @BeforeEach
    public void setUp() {
        food = new FoodItem("Fries", new Macros(0, 20, 4));
        entry = new Entry(food, 20);
        macros = food.getMacros();
    }

    @Test
    public void testEntry() {
        assertEquals(food, entry.getFood());
        assertEquals(20, entry.getHour());
        assertEquals(macros, entry.getMacros());
    }

    @Test
    public void testGetTimeOfDayMidnight() {
        entry.setHour(0);
        assertEquals("Midnight" , entry.getTimeOfDay());
        entry.setHour(2);
        assertEquals("Midnight" , entry.getTimeOfDay());
        entry.setHour(3);
        assertEquals("Midnight" , entry.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDayMorning() {
        entry.setHour(4);
        assertEquals("Morning" , entry.getTimeOfDay());
        entry.setHour(8);
        assertEquals("Morning" , entry.getTimeOfDay());
        entry.setHour(12);
        assertEquals("Morning" , entry.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDayAfternoon() {
        entry.setHour(13);
        assertEquals("Afternoon" , entry.getTimeOfDay());
        entry.setHour(14);
        assertEquals("Afternoon" , entry.getTimeOfDay());
        entry.setHour(16);
        assertEquals("Afternoon" , entry.getTimeOfDay());
    }

    @Test
    public void testGetTimeOfDayEvening() {
        entry.setHour(17);
        assertEquals("Evening" , entry.getTimeOfDay());
        entry.setHour(20);
        assertEquals("Evening" , entry.getTimeOfDay());
        entry.setHour(23);
        assertEquals("Evening" , entry.getTimeOfDay());
    }
}
