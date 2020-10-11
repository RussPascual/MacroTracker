package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FavouritesTest {

    private Favourites fav;
    private FoodItem apple;
    private FoodItem chickenBreast;
    private FoodItem rice;
    private Meal chickenBreastAndRice;

    @BeforeEach
    public void setUp() {
        fav = new Favourites();
        apple = new FoodItem("apple", new Macros(0, 20, 0));
        chickenBreast = new FoodItem("chicken breast", new Macros(20, 10, 2));
        rice = new FoodItem("rice", new Macros(4, 50, 2));

        chickenBreastAndRice = new Meal("chicken breast and rice");
        chickenBreastAndRice.addIngredient(chickenBreast);
        chickenBreastAndRice.addIngredient(rice);
    }

    @Test
    public void testFavourites() {
        assertTrue(fav.getFoods().isEmpty());
    }

    @Test
    public void testGetFood() {
        fav.addFood(apple);
        fav.addFood(chickenBreastAndRice);
        fav.addFood(rice);
        assertEquals(chickenBreastAndRice, fav.getFood("chicken breast and rice"));
        assertNull(fav.getFood("tuna"));
    }

    @Test
    public void testRemoveFood() {
        fav.addFood(chickenBreastAndRice);
        fav.addFood(apple);
        fav.addFood(rice);
        assertEquals(3, fav.getFoods().size());
        assertTrue(fav.getFoods().contains(chickenBreastAndRice));
        fav.removeFood(chickenBreastAndRice);
        assertEquals(2, fav.getFoods().size());
        assertFalse(fav.getFoods().contains(chickenBreastAndRice));
    }
}
