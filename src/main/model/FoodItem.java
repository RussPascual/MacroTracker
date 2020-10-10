package model;

/**
 * FoodItem class includes both solid foods and drinks and is a subclass of Food
 */
public class FoodItem extends Food {

    // EFFECTS: constructs a new FoodItem
    public FoodItem(String name, Macros macros) {
        super(name, macros);
    }

}
