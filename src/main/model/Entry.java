package model;

import org.json.JSONObject;

/**
 * An Entry represents a user's input of food eaten and time eaten
 */
public class Entry {

    private int hour; // within [0, 23]
    private Food food;

    // REQUIRES: hour must be within [0, 23]
    // EFFECTS: constructs a new entry
    public Entry(Food food, int hour) {
        this.food = food;
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public Food getFood() {
        return food;
    }

    public Macros getMacros() {
        return food.getMacros();
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    // EFFECTS: returns the time of day the food was eaten
    public String getTimeOfDay() {
        if (hour <= 3) {
            return "Midnight";
        } else if (hour <= 12) {
            return "Morning";
        } else if (hour <= 16) {
            return "Afternoon";
        } else {
            return "Evening";
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("hour", hour);
        json.put("food", food.toJson());
        return json;
    }
}
