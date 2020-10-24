package model;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * User represents the user's data such as name, weight, goals, and personalized macro targets.
 * A User also has a journal to track progress and list of favourite foods for easy access.
 */
public class User {

    private String name;
    private double weight; // in kilograms
    private Macros macrosNeeded;
    private Journal journal;
    private Favourites saved;

    // EFFECTS: constructs a new User
    public User(String name, double weight, double weightGoal) {
        this.name = name;
        this.weight = weight;
        this.macrosNeeded = new Macros();
        this.journal = new Journal(weightGoal);
        this.saved = new Favourites();
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public Macros getMacrosNeeded() {
        return macrosNeeded;
    }

    public Journal getJournal() {
        return journal;
    }

    public Favourites getSaved() {
        return saved;
    }

    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES: input must all be non-negative
    // MODIFIES: this
    // EFFECTS: updates all the macro values to input
    public void updateMacros(double protein, double carbohydrates, double fat, double calories) {
        macrosNeeded.updateMacros(protein, carbohydrates, fat, calories);
    }

    // REQUIRES: protein, carbs, and fat must add up to 100
    // MODIFIES: this
    // EFFECTS: sets macrosNeeded to the calorie input and the protein, carbs, and fat inputs
    //          based on percentage of calories
    public void setMacroGoals(double calories, double protein, double carbs, double fat) {
        double proteinCalories = (protein / 100.0) * calories;
        double carbohydratesCalories = (carbs / 100.0) * calories;
        double fatCalories = (fat / 100.0) * calories;

        double proteinGrams = proteinCalories / 4.0;
        double carbohydratesGrams = carbohydratesCalories / 4.0;
        double fatGrams = fatCalories / 9.0;

        macrosNeeded.updateMacros(proteinGrams, carbohydratesGrams, fatGrams, calories);
    }

    // MODIFIES: this
    // EFFECTS: saves a food item / meal as a favourite
    public void addFavourite(Food food) {
        saved.addFood(food);
    }

    // EFFECTS: returns true if daily calorie goals have been met for the day
    public boolean metCalorieGoals() {
        double caloriesSoFar = journal.getLastLog().totalMacros().getCalories();
        if (caloriesSoFar >= macrosNeeded.getCalories()) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns true if daily calorie goals have been met for the day
    public boolean metProteinGoals() {
        double proteinSoFar = journal.getLastLog().totalMacros().getProtein();
        if (proteinSoFar >= macrosNeeded.getProtein()) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns true if daily calorie goals have been met for the day
    public boolean metCarbGoals() {
        double carbsSoFar = journal.getLastLog().totalMacros().getCarbs();
        if (carbsSoFar >= macrosNeeded.getCarbs()) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns true if daily calorie goals have been met for the day
    public boolean metFatGoals() {
        double fatSoFar = journal.getLastLog().totalMacros().getFat();
        if (fatSoFar >= macrosNeeded.getFat()) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns the remaining macros needed for the day
    public Macros remainingMacros() {
        Macros currentMacros = journal.getLastLog().totalMacros();
        double protein = macrosNeeded.getProtein() - currentMacros.getProtein();
        double carbs = macrosNeeded.getCarbs() - currentMacros.getCarbs();
        double fat = macrosNeeded.getFat() - currentMacros.getFat();
        if (protein < 0) {
            protein = 0;
        }
        if (carbs < 0) {
            carbs = 0;
        }
        if (fat < 0) {
            fat = 0;
        }
        return new Macros(protein, carbs, fat);
    }

    // REQUIRES: weight > 0
    // MODIFIES: this
    // EFFECTS: updates the weight of the user
    public void updateWeight(double weight) {
        this.weight = weight;
        if (!journal.getLogs().isEmpty()) {
            journal.updateWeight(weight);
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("weight", weight);
        json.put("macrosNeeded", macrosNeeded.toJson());
        json.put("journal", journal.toJson());
        json.put("saved", saved.toJson());
        return json;
    }
}