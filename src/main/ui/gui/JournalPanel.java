package ui.gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.util.InputMismatchException;

/**
 * JournalPanel initializes the panel that handles the visibility and functionality for a user's journal logs
 */
public class JournalPanel {

    private static final String ENTRIES = "entries";
    private static final String NOTES = "notes";

    private int currentLog;
    private int currentEntry;
    private int currentNote;
    private JPanel panel;
    private GUI gui;

    // EFFECTS: constructs a journal panel
    public JournalPanel(GUI gui) {
        this.gui = gui;
        newDay(gui.getUser().getWeight());
        initializePanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: adds a new day to the journal and day menu and sets it as current day
    private void newDay(double weight) {
        Journal journal = gui.getUser().getJournal();
        journal.nextDay(weight);
        currentLog = journal.getLogs().size();
        currentEntry = 0;
        currentNote = 0;
    }

    // MODIFIES: this
    // EFFECTS: initializes the panel
    private void initializePanel() {
        panel = new JPanel();
        panel.setLayout(null);
        initializeNewEntry();
        initializeNewDay();
        initializeCurrentEntries();
        initializeNewNote();
        initializeCurrentNotes();
        initializeHeaders();
        initializeDaySelect();
        clearMessages();
        update();
    }

    // MODIFIES: this
    // EFFECTS: initializes day select functionality
    private void initializeDaySelect() {
        initializeDayLabels();
        initializeDayButton();
    }

    // MODIFIES: this
    // EFFECTS: create day select labels
    private void initializeDayLabels() {
        JLabel illegalDay = new JLabel("Day does not exist! Make sure day is within (1 - # days)");
        illegalDay.setBounds(350, 70, 350, 25);
        JLabel illegalInput = new JLabel("Invalid input type! Please input numbers (eg. '25')");
        illegalInput.setBounds(350, 70, 300, 25);
        JLabel numberOfDays = new JLabel("# of Days: 1");
        numberOfDays.setBounds(300, 10, 100, 25);
        JLabel currentDay = new JLabel("Day #: 1");
        currentDay.setBounds(300, 40, 100, 25);
        JLabel day = new JLabel("Select Day");
        day.setBounds(400, 10, 100, 25);
        JTextField dayText = new JTextField(10);
        dayText.setBounds(500, 10, 100, 25);
        panel.add(illegalDay); // 43
        panel.add(illegalInput); // 44
        panel.add(numberOfDays); // 45
        panel.add(currentDay); // 46
        panel.add(day); // 47
        panel.add(dayText); // 48
    }

    // MODIFIES: this
    // EFFECTS: create day select button
    private void initializeDayButton() {
        JButton selectDay = new JButton("Select");
        selectDay.setBounds(450, 40, 100, 25);
        selectDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearMessages();
                    selectDay();
                    update();
                } catch (NumberFormatException e1) {
                    panel.getComponent(44).setVisible(true);
                } catch (IllegalArgumentException e1) {
                    panel.getComponent(43).setVisible(true);
                }
            }
        });
        panel.add(selectDay); // 49
    }

    // MODIFIES: this
    // EFFECTS: select day based on input
    //          throw IllegalArgumentException if day does not exist
    private void selectDay() {
        Journal journal = gui.getUser().getJournal();
        int day = Integer.parseInt(((JTextField) panel.getComponent(48)).getText());
        if (day > 0 && day <= journal.getLogs().size()) {
            ((JLabel) panel.getComponent(46)).setText("Day #: " + day);
            currentLog = day;
            currentEntry = journal.getLog(currentLog).getEntries().isEmpty() ? 0 : 1;
            currentNote = journal.getLog(currentLog).getNotes().isEmpty() ? 0 : 1;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // MODIFIES: this
    // EFFECTS: create headers
    private void initializeHeaders() {
        Font font = new Font(Font.SERIF, Font.BOLD, 24);
        JLabel newEntry = new JLabel("Add an Entry!");
        newEntry.setBounds(30, 100, 200, 50);
        newEntry.setFont(font);
        JLabel currentEntry = new JLabel("Current Entries");
        currentEntry.setBounds(420, 100, 200, 50);
        currentEntry.setFont(font);
        JLabel newNote = new JLabel("Add a Note!");
        newNote.setBounds(40, 400, 200, 50);
        newNote.setFont(font);
        JLabel currentNote = new JLabel("Current Notes");
        currentNote.setBounds(430, 400, 200, 50);
        currentNote.setFont(font);
        panel.add(newEntry); // 39
        panel.add(currentEntry); // 40
        panel.add(newNote); // 41
        panel.add(currentNote); // 42
    }

    // MODIFIES: this
    // EFFECTS: create new day functionality
    private void initializeNewDay() {
        initializeNewDayLabels();
        initializeNewDayButton();
    }

    // MODIFIES: this
    // EFFECTS: create new day labels
    private void initializeNewDayLabels() {
        JLabel weight = new JLabel("Weight");
        weight.setBounds(10, 10, 80, 25);
        JTextField weightText = new JTextField(10);
        weightText.setBounds(100, 10, 80, 25);
        JLabel typeError = new JLabel("Invalid input type! Please input numbers (eg. '25')");
        typeError.setBounds(10, 70, 300, 25);
        JLabel negativeError = new JLabel("Illegal input! Please input non-negative numbers!");
        negativeError.setBounds(10, 70, 300, 25);
        panel.add(weight); // 16
        panel.add(weightText); // 17
        panel.add(typeError); // 18
        panel.add(negativeError); // 19
    }

    // MODIFIES: this
    // EFFECTS: create new day button
    private void initializeNewDayButton() {
        JButton newDay = new JButton("New Day");
        newDay.setBounds(50, 40, 100, 25);
        panel.add(newDay); // 20
        newDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearMessages();
                    double weight = Double.parseDouble(((JTextField) panel.getComponent(17)).getText());
                    if (weight < 0) {
                        throw new IllegalArgumentException();
                    } else {
                        newDay(weight);
                        gui.getPlayer().playSoundWhenWeightGoalReached();
                    }
                    update();
                } catch (NumberFormatException e1) {
                    panel.getComponent(18).setVisible(true);
                } catch (IllegalArgumentException e1) {
                    panel.getComponent(19).setVisible(true);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: create new entry functionality
    private void initializeNewEntry() {
        initializeNewEntryLabels();
        initializeNewMacros();
        initializeNewEntryErrorLabels();
        initializeAddEntryButton();
    }

    // MODIFIES: this
    // EFFECTS: create new entry labels
    private void initializeNewEntryLabels() {
        JLabel newName = new JLabel("Food Name");
        newName.setBounds(10, 150, 80, 25);
        JTextField newNameText = new JTextField(20);
        newNameText.setBounds(100, 150, 120, 25);
        JLabel calories = new JLabel("Calories");
        calories.setBounds(10, 230, 80, 25);
        JTextField caloriesText = new JTextField(20);
        caloriesText.setBounds(140, 230, 80, 25);
        JLabel time = new JLabel("Time Eaten");
        time.setBounds(10, 180, 80, 25);
        JTextField timeText = new JTextField(10);
        timeText.setBounds(140, 180, 80, 25);
        panel.add(newName); // 0
        panel.add(newNameText); // 1
        panel.add(calories); // 2
        panel.add(caloriesText); // 3
        panel.add(time); // 4
        panel.add(timeText); // 5
    }

    // MODIFIES: this
    // EFFECTS: create macro input labels
    private void initializeNewMacros() {
        JLabel protein = new JLabel("Protein (g)");
        protein.setBounds(20, 260, 100, 25);
        JLabel carbs = new JLabel("Carbohydrates (g)");
        carbs.setBounds(20, 290, 110, 25);
        JLabel fat = new JLabel("Fat (g)");
        fat.setBounds(20, 320, 100, 25);
        JTextField proteinText = new JTextField(20);
        proteinText.setBounds(140, 260, 80, 25);
        JTextField carbsText = new JTextField(20);
        carbsText.setBounds(140, 290, 80, 25);
        JTextField fatText = new JTextField(20);
        fatText.setBounds(140, 320, 80, 25);
        panel.add(protein); // 6
        panel.add(proteinText); // 7
        panel.add(carbs); // 8
        panel.add(carbsText); // 9
        panel.add(fat); // 10
        panel.add(fatText); // 11
    }

    // MODIFIES: this
    // EFFECTS: create new entry error labels
    private void initializeNewEntryErrorLabels() {
        JLabel typeError = new JLabel("Invalid input type! Please input numbers (eg. '25')");
        typeError.setBounds(10, 380, 300, 25);
        JLabel negativeError = new JLabel("Illegal input! Please input non-negative numbers!");
        negativeError.setBounds(10, 380, 300, 25);
        JLabel invalidTime = new JLabel("Invalid time! Time must be between 0 and 23!");
        invalidTime.setBounds(10, 380, 300, 25);
        panel.add(typeError); // 12
        panel.add(negativeError); // 13
        panel.add(invalidTime); // 14
    }

    // MODIFIES: this
    // EFFECTS: create add entry button
    private void initializeAddEntryButton() {
        JButton addEntry = new JButton("Add Entry");
        addEntry.setBounds(50, 350, 100, 25);
        addEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearMessages();
                    addNewEntry();
                    update();
                } catch (NumberFormatException e1) {
                    panel.getComponent(12).setVisible(true);
                } catch (IllegalArgumentException e1) {
                    panel.getComponent(13).setVisible(true);
                } catch (InputMismatchException e1) {
                    panel.getComponent(14).setVisible(true);
                }
            }
        });
        panel.add(addEntry); // 15
    }

    // MODIFIES: this
    // EFFECTS: clear error messages
    private void clearMessages() {
        panel.getComponent(12).setVisible(false);
        panel.getComponent(13).setVisible(false);
        panel.getComponent(14).setVisible(false);
        panel.getComponent(18).setVisible(false);
        panel.getComponent(19).setVisible(false);
        panel.getComponent(43).setVisible(false);
        panel.getComponent(44).setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: adds an entry based on input
    //          throw IllegalArgumentException if input is negative
    //          throw InputMismatchException if time < 0 or time > 23
    private void addNewEntry() {
        Journal journal = gui.getUser().getJournal();
        String name = ((JTextField) panel.getComponent(1)).getText();
        int time = Integer.parseInt(((JTextField) panel.getComponent(5)).getText());
        double calories = Double.parseDouble(((JTextField) panel.getComponent(3)).getText());
        double protein = Double.parseDouble(((JTextField) panel.getComponent(7)).getText());
        double carbs = Double.parseDouble(((JTextField) panel.getComponent(9)).getText());
        double fat = Double.parseDouble(((JTextField) panel.getComponent(11)).getText());
        if (calories < 0 || protein < 0 || carbs < 0 || fat < 0) {
            throw new IllegalArgumentException();
        }
        if (time < 0 || time > 23) {
            throw new InputMismatchException();
        }
        journal.getLog(currentLog).addEntry(new Entry(new FoodItem(name, new Macros(protein, carbs, fat)), time));
        currentEntry = journal.getLog(currentLog).getEntries().size();
        gui.getPlayer().playSoundWhenMacrosAreHit();
    }

    // MODIFIES: this
    // EFFECTS: create current entries functionality
    private void initializeCurrentEntries() {
        initializeCurrentEntryLabels();
        initializeCurrentEntryButtons();
    }

    // MODIFIES: this
    // EFFECTS: create current entries labels
    private void initializeCurrentEntryLabels() {
        JLabel noEntries = new JLabel("You have no entries recorded!");
        noEntries.setBounds(420, 230, 250, 25);
        JLabel name = new JLabel("Food Name:");
        name.setBounds(400, 150, 150, 25);
        JLabel time = new JLabel("Time Eaten:");
        time.setBounds(400, 180, 150, 25);
        JLabel calories = new JLabel("Calories:");
        calories.setBounds(400, 230, 150, 25);
        JLabel protein = new JLabel("Protein:");
        protein.setBounds(400, 260, 150, 25);
        JLabel carbs = new JLabel("Carbohydrates:");
        carbs.setBounds(400, 290, 150, 25);
        JLabel fat = new JLabel("Fat:");
        fat.setBounds(400, 320, 150, 25);
        panel.add(noEntries); // 21
        panel.add(name); // 22
        panel.add(time); // 23
        panel.add(calories); // 24
        panel.add(protein); // 25
        panel.add(carbs); // 26
        panel.add(fat); // 27
    }

    // MODIFIES: this
    // EFFECTS: create current entry buttons
    private void initializeCurrentEntryButtons() {
        initializeRemoveEntryButton();
        initializePreviousEntryButton();
        initializeNextEntryButton();
    }

    // MODIFIES: this
    // EFFECTS: create remove entry button
    private void initializeRemoveEntryButton() {
        JButton removeButton = new JButton("Remove Entry");
        removeButton.setBounds(440, 350, 120, 25);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                if (currentEntry > 0) {
                    gui.getUser().getJournal().getLog(currentLog).removeEntry(currentEntry);
                    gui.getPlayer().updateMacroProgress();
                }
                update();
            }
        });
        panel.add(removeButton); // 28
    }

    // MODIFIES: this
    // EFFECTS: create previous entry button
    private void initializePreviousEntryButton() {
        JButton previous = new JButton("Previous");
        previous.setBounds(300, 350, 100, 25);
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                if (currentEntry > 0) {
                    rotate(false, ENTRIES);
                }
                update();
            }
        });
        panel.add(previous); // 29
    }

    // MODIFIES: this
    // EFFECTS: create next entry button
    private void initializeNextEntryButton() {
        JButton next = new JButton("Next");
        next.setBounds(600, 350, 100, 25);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                if (currentEntry > 0) {
                    rotate(true, ENTRIES);
                }
                update();
            }
        });
        panel.add(next); // 30
    }

    // MODIFIES: this
    // EFFECTS: updates current entries, notes, and day#
    public void update() {
        updateEntries();
        updateNotes();
        ((JLabel) panel.getComponent(45)).setText("# of Days: " + gui.getUser().getJournal().getLogs().size());
        ((JLabel) panel.getComponent(46)).setText("Day #: " + currentLog);
    }

    // MODIFIES: this
    // EFFECTS: updates current entries
    private void updateEntries() {
        Journal journal = gui.getUser().getJournal();
        if (currentEntry > journal.getLog(currentLog).getEntries().size()) {
            currentEntry = journal.getLog(currentLog).getEntries().size();
        }
        if (!journal.getLog(currentLog).getEntries().isEmpty() && currentEntry == 0) {
            currentEntry = 1;
        }
        if (currentEntry == 0) {
            showCurrentEntry(false);
        } else {
            showCurrentEntry(true);
            setCurrentEntry();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates current notes
    private void updateNotes() {
        if (currentNote == 0) {
            showCurrentNote(false);
        } else {
            showCurrentNote(true);
            setCurrentNote();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates current note information
    private void setCurrentNote() {
        String note = gui.getUser().getJournal().getLog(currentLog).getNotes().get(currentNote - 1);
        ((JLabel) panel.getComponent(35)).setText("Note: " + note);
    }


    // MODIFIES: this
    // EFFECTS: shows warning if no notes available and current note if available
    private void showCurrentNote(boolean b) {
        panel.getComponent(34).setVisible(!b);
        panel.getComponent(35).setVisible(b);
    }

    // MODIFIES: this
    // EFFECTS: updates current entry information
    private void setCurrentEntry() {
        Journal journal = gui.getUser().getJournal();
        String foodName = journal.getLog(currentLog).getEntries().get(currentEntry - 1).getFood().getName();
        ((JLabel) panel.getComponent(22)).setText("Food Name: " + foodName);
        int time = journal.getLog(currentLog).getEntries().get(currentEntry - 1).getHour();
        ((JLabel) panel.getComponent(23)).setText("Time Eaten: " + time);
        double calories = journal.getLog(currentLog).getEntries().get(currentEntry - 1).getFood().getCalories();
        ((JLabel) panel.getComponent(24)).setText("Calories: " + calories);
        double protein = journal.getLog(currentLog).getEntries().get(currentEntry - 1).getFood().getProtein();
        ((JLabel) panel.getComponent(25)).setText("Protein: " + protein);
        double carbs = journal.getLog(currentLog).getEntries().get(currentEntry - 1).getFood().getCarbs();
        ((JLabel) panel.getComponent(26)).setText("Carbohydrates: " + carbs);
        double fat = journal.getLog(currentLog).getEntries().get(currentEntry - 1).getFood().getFat();
        ((JLabel) panel.getComponent(27)).setText("Fat: " + fat);
    }

    // MODIFIES: this
    // EFFECTS: shows warning if no entries available and current entry if available
    private void showCurrentEntry(boolean b) {
        panel.getComponent(21).setVisible(!b);
        panel.getComponent(22).setVisible(b);
        panel.getComponent(23).setVisible(b);
        panel.getComponent(24).setVisible(b);
        panel.getComponent(25).setVisible(b);
        panel.getComponent(26).setVisible(b);
        panel.getComponent(27).setVisible(b);
    }

    // MODIFIES: this
    // EFFECTS: sets current entry or note to next element if true and previous element if false
    //          throw InvalidParameterException if list is not "entries" or "notes"
    private void rotate(boolean forward, String list) {
        Journal journal = gui.getUser().getJournal();
        if (list.equals("entries")) {
            if (forward) {
                currentEntry = (currentEntry == journal.getLog(currentLog).getEntries().size()) ? 1 : currentEntry + 1;
            } else {
                currentEntry = (currentEntry == 1) ? journal.getLog(currentLog).getEntries().size() : currentEntry - 1;
            }
        } else if (list.equals("notes")) {
            if (forward) {
                currentNote = (currentNote == journal.getLog(currentLog).getNotes().size()) ? 1 : currentNote + 1;
            } else {
                currentNote = (currentNote == 1) ? journal.getLog(currentLog).getNotes().size() : currentNote - 1;
            }
        } else {
            throw new InvalidParameterException();
        }
    }

    // MODIFIES: this
    // EFFECTS: create new note functionality
    private void initializeNewNote() {
        initializeNewNoteLabels();
        initializeAddNoteButton();
    }

    // MODIFIES: this
    // EFFECTS: create new note labels
    private void initializeNewNoteLabels() {
        JLabel note = new JLabel("Note:");
        note.setBounds(10, 450, 80, 25);
        JTextField noteText = new JTextField(20);
        noteText.setBounds(100, 450, 120, 25);
        panel.add(note); // 31
        panel.add(noteText); // 32
    }

    // MODIFIES: this
    // EFFECTS: create add note button
    private void initializeAddNoteButton() {
        JButton addNote = new JButton("Add Note");
        addNote.setBounds(50, 480, 100, 25);
        addNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                String note = ((JTextField) panel.getComponent(32)).getText();
                if (note.length() > 0) {
                    Journal journal = gui.getUser().getJournal();
                    journal.getLog(currentLog).addNote(note);
                    currentNote = journal.getLog(currentLog).getNotes().size();
                }
                update();
            }
        });
        panel.add(addNote); // 33
    }

    // MODIFIES: this
    // EFFECTS: create current notes functionality
    private void initializeCurrentNotes() {
        initializeCurrentNoteLabels();
        initializeCurrentNoteButtons();
    }

    // MODIFIES: this
    // EFFECTS: create current note labels
    private void initializeCurrentNoteLabels() {
        JLabel noNotes = new JLabel("You have no notes recorded!");
        noNotes.setBounds(420, 450, 250, 25);
        JLabel note = new JLabel("Note: ");
        note.setBounds(320, 450, 360, 25);
        panel.add(noNotes); // 34
        panel.add(note); // 35
    }

    // MODIFIES: this
    // EFFECTS: create current note buttons
    private void initializeCurrentNoteButtons() {
        initializeRemoveNoteButton();
        initializePreviousNoteButton();
        initializeNextNoteButton();
    }

    // MODIFIES: this
    // EFFECTS: create remove note button
    private void initializeRemoveNoteButton() {
        JButton remove = new JButton("Remove Note");
        remove.setBounds(440, 480, 120, 25);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                Journal journal = gui.getUser().getJournal();
                if (currentNote > 0) {
                    journal.getLog(currentLog).removeNote(currentNote);
                }
                if (currentNote > journal.getLog(currentLog).getNotes().size()) {
                    currentNote = journal.getLog(currentLog).getNotes().size();
                }
                update();
            }
        });
        panel.add(remove); // 36
    }

    // MODIFIES: this
    // EFFECTS: create previous note button
    private void initializePreviousNoteButton() {
        JButton previous = new JButton("Previous");
        previous.setBounds(300, 480, 100, 25);
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                if (currentNote > 0) {
                    rotate(false, NOTES);
                }
                update();
            }
        });
        panel.add(previous); // 37
    }

    // MODIFIES: this
    // EFFECTS: create next note button
    private void initializeNextNoteButton() {
        JButton next = new JButton("Next");
        next.setBounds(600, 480, 100, 25);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                if (currentNote > 0) {
                    rotate(true, NOTES);
                }
                update();
            }
        });
        panel.add(next); // 38
    }
}
