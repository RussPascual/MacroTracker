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

    // REQUIRES: foodName must be in foods
    // EFFECTS: returns the food whose name was passed
    public Food getFood(String foodName) {
        for (Food food : foods) {
            if (food.getName() == foodName.toLowerCase()) {
                return food;
            }
        }
        throw new IllegalArgumentException("food was not found in the list");
    }

    // REQUIRES: food must be in this
    // MODIFIES: this
    // EFFECTS: adds food into foods
    public void addFood(Food food) {
        foods.add(food);
    }

    // REQUIRES: meal must be in this
    // MODIFIES: this
    // EFFECTS: removes food from foods
    public void removeFood(Food food) {
        foods.remove(food);
    }
}
