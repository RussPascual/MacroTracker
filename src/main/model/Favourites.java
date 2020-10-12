package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Favourites is a list of foods saved as a 'favourite'. These foods can be accessed later instead of reconstructed
 * for easier replacement as another entry
 */
public class Favourites {

    private List<Food> foods;

    // EFFECTS: constructs an empty favourites item
    public Favourites() {
        foods = new ArrayList<>();
    }

    // EFFECTS: returns the foods
    public List<Food> getFoods() {
        return foods;
    }

    // EFFECTS: returns the food whose name was passed or null if it cannot be found
    public Food getFood(String foodName) {
        for (Food food : foods) {
            if (food.getName() == foodName.toLowerCase()) {
                return food;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: adds food into foods if not already contained
    public void addFood(Food food) {
        if (!foods.contains(food)) {
            foods.add(food);
        }
    }

    // REQUIRES: food must be in this
    // MODIFIES: this
    // EFFECTS: removes food from foods
    public void removeFood(Food food) {
        foods.remove(food);
    }
}
