package model;

import java.util.Date;

public class Note {

    private Date date;
    private String message;

    // EFFECTS: constructs a new note
    public Note(Date date, String message) {
        this.date = date;
        this.message = message;
    }

    // EFFECTS: returns the time of the note
    public Date getDate() {
        return date;
    }

    // EFFECTS: returns the message of the note
    public String getMessage() {
        return message;
    }
}
