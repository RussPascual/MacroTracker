package persistence;

import model.DayLog;
import model.Favourites;
import model.Journal;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriterTest extends JsonTest{

    @Test
    public void testWriterInvalidFile() {
        try {
            User user = new User("test name", 100, 200);
            JsonWriter writer = new JsonWriter("./data/\0unknownFile.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterGeneralUser() {
        try {
            User user = generalUser();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            user = reader.read();
            assertEquals("Russell", user.getName());
            assertEquals(164, user.getWeight());
            checkMacros(1440, 90, 180, 40, user.getMacrosNeeded());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testWriterNoLogs() {
        try {
            User user = noLogsOrSaved();
            JsonWriter writer = new JsonWriter("./data/testWriterNoLogsOrSaved.json");
            writer.open();
            writer.write(user);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterNoLogsOrSaved.json");
            Journal journal = reader.read().getJournal();
            assertTrue(journal.getLogs().isEmpty());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testWriterNoSaved() {
        try {
            User user = noLogsOrSaved();
            JsonWriter writer = new JsonWriter("./data/testWriterNoLogsOrSaved.json");
            writer.open();
            writer.write(user);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterNoLogsOrSaved.json");
            Favourites saved = reader.read().getSaved();
            assertTrue(saved.getFoods().isEmpty());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testWriterNoEntries() {
        try {
            User user = noEntriesOrNotes();
            JsonWriter writer = new JsonWriter("./data/testWriterNoEntriesOrNotes.json");
            writer.open();
            writer.write(user);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterNoEntriesOrNotes.json");
            DayLog log = reader.read().getJournal().getLastLog();
            assertTrue(log.getEntries().isEmpty());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testReaderNoNotes() {
        try {
            User user = noEntriesOrNotes();
            JsonWriter writer = new JsonWriter("./data/testWriterNoEntriesOrNotes.json");
            writer.open();
            writer.write(user);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterNoEntriesOrNotes.json");
            DayLog log = reader.read().getJournal().getLastLog();
            assertTrue(log.getNotes().isEmpty());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testReaderGeneralJournal() {
        try {
            User user = generalUser();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            Journal journal = reader.read().getJournal();
            assertEquals(180, journal.getGoal());
            assertEquals(2, journal.getLogs().size());
            assertEquals(2, journal.getWeightTracker().size());
            checkLogs(1, 2, 0, 162, journal.getLog(1));
            checkLogs(2, 0, 2, 164, journal.getLastLog());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testReaderGeneralSaved() {
        try {
            User user = generalUser();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            Favourites saved = reader.read().getSaved();
            assertEquals(2, saved.getFoods().size());
            checkFood("banana", false, 0, 15, 0, saved.getFoods().get(0));
            checkFood("spaghetti and meatballs", true, 19, 50, 5, saved.getFoods().get(1));
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }
}
