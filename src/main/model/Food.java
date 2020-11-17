package model;

import org.json.JSONObject;

/**
 * Food can be either a single food item or a meal. Food represents the food recorded as an entry and contains
 * information on its macros as well
 */
public abstract class Food {

    private String name;
    private Macros macros;
    private boolean isMeal;

    // EFFECTS: constructs a food item
    public Food(String name, Macros macros, boolean isMeal) {
        this.name = name.toLowerCase();
        this.macros = macros;
        this.isMeal = isMeal;
    }

    // EFFECTS: returns the name of the food
    public String getName() {
        return name;
    }

    // EFFECTS: returns the macros of the food
    public Macros getMacros() {
        return macros;
    }

    // EFFECTS: returns the protein value of the food
    public double getProtein() {
        return macros.getProtein();
    }

    // EFFECTS: returns the carbohydrates value of the food
    public double getCarbs() {
        return macros.getCarbs();
    }

    // EFFECTS: returns the fat value of the food
    public double getFat() {
        return macros.getFat();
    }

    // EFFECTS: returns the calories of the food
    public double getCalories() {
        return macros.getCalories();
    }

    public boolean isMeal() {
        return isMeal;
    }

    public void setMacros(Macros macros) {
        this.macros = macros;
    }

    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds input's macros onto meal's macros
    public void addMacros(FoodItem foodItem) {
        macros.addMacros(foodItem.getMacros());
    }

    // MODIFIES: this
    // EFFECTS: removes input's macros from meal's macros
    public void removeMacros(FoodItem foodItem) {
        macros.removeMacros(foodItem.getMacros());
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("macros", macros.toJson());
        return json;
    }
}
