package model;

import java.util.ArrayList;
import java.util.List;

public class Journal {

    private List<DayLog> logs;
    private List<Note> notes;

    // EFFECTS: constructs an empty journal
    public Journal() {
        logs = new ArrayList<>();
        notes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds day 1's log into logs

}
