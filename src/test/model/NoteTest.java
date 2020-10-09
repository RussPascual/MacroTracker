package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoteTest {

    private Note note;
    private Date date;

    @BeforeEach
    public void setUp() {
        date = new Date();
        note = new Note(date, "test message");
    }

    @Test
    public void testNote() {
        assertEquals(date, note.getDate());
        assertEquals("test message", note.getMessage());
    }
}
