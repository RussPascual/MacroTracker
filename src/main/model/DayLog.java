package model;

import java.util.ArrayList;
import java.util.List;

/**
 * DayLog represents a day's log/collection of food entries and notes recorded for that day. DayLog also shows the
 * weight change of the user in the day
 */
public class DayLog {

    private int day;
    private List<Entry> entries;
    private List<String> notes;
    private double weight;

    // REQUIRES: weight > 0
    // EFFECTS: creates a new food log
    public DayLog(int day, double weight) {
        this.day = day;
        entries = new ArrayList<>();
        notes = new ArrayList<>();
        this.weight = weight;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public int getDay() {
        return day;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public List<String> getNotes() {
        return notes;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    // MODIFIES: this
    // EFFECTS: adds entry to log for the day
    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    // REQUIRES: pos is a valid index in the list of entries
    // MODIFIES: this
    // EFFECTS: removes this entry from the logs based on position inputted starting at 1
    public void removeEntry(int pos) {
        entries.remove(pos - 1);
    }

    // MODIFIES: this
    // EFFECTS: adds note to log for the day
    public void addNote(String string) {
        notes.add(string);
    }

    // MODIFIES: this
    // EFFECTS: removes this note from the logs based on position inputted starting at 1
    public void removeNote(int pos) {
        notes.remove(pos - 1);
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
            if (entry.getTimeOfDay().equals(timeOfDay)) {
                foodAtTime.add(entry.getFood());
            }
        }
        return foodAtTime;
    }

    // EFFECTS: returns the total macros of all the entries
    public Macros totalMacros() {
        Macros macros = new Macros();
        for (Entry entry : entries) {
            macros.addMacros(entry.getMacros());
        }
        return macros;
    }
}
