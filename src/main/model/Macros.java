package model;

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

    // EFFECTS: returns the quantity of protein
    public double getProtein() {
        return protein;
    }

    // EFFECTS: returns the quantity of carbohydrates
    public double getCarbohydrates() {
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
}
