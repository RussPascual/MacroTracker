package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderGeneralUser() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
            User user = reader.read();
            assertEquals("Russell", user.getName());
            assertEquals(164, user.getWeight());
            checkMacros(1440, 90, 180, 40, user.getMacrosNeeded());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testReaderNoLogs() {
        JsonReader reader = new JsonReader("./data/testReaderNoLogsOrSaved.json");
        try {
            Journal journal = reader.read().getJournal();
            assertTrue(journal.getLogs().isEmpty());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testReaderNoSaved() {
        JsonReader reader = new JsonReader("./data/testReaderNoLogsOrSaved.json");
        try {
            Favourites saved = reader.read().getSaved();
            assertTrue(saved.getFoods().isEmpty());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testReaderNoEntries() {
        JsonReader reader = new JsonReader("./data/testReaderNoEntriesOrNotes.json");
        try {
            DayLog log = reader.read().getJournal().getLastLog();
            assertTrue(log.getEntries().isEmpty());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testReaderNoNotes() {
        JsonReader reader = new JsonReader("./data/testReaderNoEntriesOrNotes.json");
        try {
            DayLog log = reader.read().getJournal().getLastLog();
            assertTrue(log.getNotes().isEmpty());
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testReaderGeneralJournal() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
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
        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
            Favourites saved = reader.read().getSaved();
            assertEquals(2, saved.getFoods().size());
            checkFood("banana", false, 0, 15, 0, saved.getFoods().get(0));
            checkFood("spaghetti and meatballs", true, 19, 50, 5, saved.getFoods().get(1));
        } catch (IOException e) {
            fail("Exception not expected");
        }
    }
}
