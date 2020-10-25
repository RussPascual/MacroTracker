package model;

import org.json.JSONObject;

/**
 * FoodItem class includes both solid foods and drinks and is a subclass of Food
 */
public class FoodItem extends Food {

    // EFFECTS: constructs a new FoodItem
    public FoodItem(String name, Macros macros) {
        super(name, macros, false);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("isMeal", false);
        return json;
    }
}
