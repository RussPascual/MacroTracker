package model;

/**
 * Macros represents the macronutrients in every food. Each of protein, carbs, and fat are measured in grams
 * and have their own caloric value of 4 calories per gram of protein or carb and 9 calories per gram of fat
 */
public class Macros {

    private double protein;       // in grams
    private double carbohydrates; // in grams
    private double fat;           // in grams
    private double calories; // 4 in gram of protein, 4 in gram of carbohydrate, 9 in gram of fat

    // REQUIRES: input must all be non-negative
    // EFFECTS: constructs the macros
    public Macros(double protein, double carbohydrates, double fat) {
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.calories = calculateCalories(protein, carbohydrates, fat);
    }

    // EFFECTS: constructs Macros with all equal to 0
    public Macros() {
        new Macros(0, 0, 0);
    }

    // EFFECTS: returns the quantity of protein
    public double getProtein() {
        return protein;
    }

    // EFFECTS: returns the quantity of carbohydrates
    public double getCarbs() {
        return carbohydrates;
    }

    // EFFECTS: returns the quantity of fat
    public double getFat() {
        return fat;
    }

    // EFFECTS: returns the quantity of calories
    public double getCalories() {
        return calories;
    }

    // REQUIRES: input must all be non-negative
    // EFFECTS: calculates the number of calories given protein, carbs, and fat
    public double calculateCalories(double protein, double carbohydrates, double fat) {
        double calories = (protein * 4) + (carbohydrates * 4) + (fat * 9);
        return calories;
    }

    // MODIFIES: this
    // EFFECTS: adds this' macros and input's macros together
    public void addMacros(Macros macros) {
        protein += macros.getProtein();
        carbohydrates += macros.getCarbs();;
        fat += macros.getFat();
        calories += macros.getCalories();
    }

    // REQUIRES: input's macros <= this' macros at all fields
    // MODIFIES: this
    // EFFECTS: removes input's macros from this' macros
    public void removeMacros(Macros macros) {
        protein -= macros.getProtein();
        carbohydrates -= macros.getCarbs();;
        fat -= macros.getFat();
        calories -= macros.getCalories();
    }
}
