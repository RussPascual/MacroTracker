package model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodJournal {

    private Date date;
    private List<Meal> foodEaten;
    private List<Time> timesEaten;
    private List<Note> notes;

    // EFFECTS: creates a new FoodJournal for a specific date
    public FoodJournal(Date date) {
        this.date = date;
        foodEaten = new ArrayList<>();
        timesEaten = new ArrayList<>();
        notes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds meal and time eaten to records for the day
    public void addMeal(Meal meal, Time time) {
        // stub
    }
}
