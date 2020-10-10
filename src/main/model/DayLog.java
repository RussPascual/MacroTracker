package model;

import java.util.ArrayList;
import java.util.List;

public class DayLog {

    private int day;
    private List<Entry> entries;

    // EFFECTS: creates a new food log
    public DayLog(int day) {
        this.day = day;
        entries = new ArrayList<>();
    }

    public int getDay() {
        return day;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    // MODIFIES: this
    // EFFECTS: adds entry to log for the day
    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    // EFFECTS: returns the food whose entry's time = input
    public List<Food> getFoodAtTime(int hour) {
        List<Food> foodAtTime = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.getHour() == hour) {
                foodAtTime.add(entry.getFood());
            }
        }
        return foodAtTime;
    }

    // EFFECTS: returns the food whose entry's time = input
    public List<Food> getFoodAtTime(String timeOfDay) {
        List<Food> foodAtTime = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.getTimeOfDay() == timeOfDay) {
                foodAtTime.add(entry.getFood());
            }
        }
        return foodAtTime;
    }
}
