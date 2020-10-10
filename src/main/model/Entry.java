package model;

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

    // EFFECTS: returns the macros of the food
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
}
