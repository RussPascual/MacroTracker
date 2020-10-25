package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A Meal is a collection of FoodItems. Unlike a singular FoodItem, a Meal can have ingredients added to it
 */
public class Meal extends Food {

    private List<FoodItem> ingredients;

    // EFFECTS: constructs an empty meal
    public Meal(String name) {
        super(name, new Macros(), true);
        this.ingredients = new ArrayList<>();
    }

    // EFFECTS: returns the foods that comprise the meal
    public List<FoodItem> getIngredients() {
        return ingredients;
    }

    // MODIFIES: this
    // EFFECTS: adds the fooditem into the meal as an ingredient even if already there
    public void addIngredient(FoodItem food) {
        ingredients.add(food);
        super.addMacros(food);
    }

    // MODIFIES: this
    // EFFECTS: removes fooditem from meal if its contained, but only one instance
    public void removeIngredient(FoodItem food) {
        ingredients.remove(food);
        super.removeMacros(food);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("isMeal", true);
        json.put("ingredients", ingredientsToJson());
        return json;
    }

    // EFFECTS: adds each ingredient to json array and returns the array
    private JSONArray ingredientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FoodItem i : ingredients) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }
}
