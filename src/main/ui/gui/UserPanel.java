package ui.gui;

import model.exceptions.NegativeInputException;
import model.exceptions.PercentageException;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * UserPanel initializes the panel that handles the visibility and functionality for a user's information
 */
public class UserPanel {

    private static final String JSON_FILE = "./data/user.json";

    private JPanel panel;
    private boolean initialized;
    private JsonReader reader;
    private JsonWriter writer;
    private boolean loaded;
    private GUI gui;

    // EFFECTS: constructs a user panel
    public UserPanel(GUI gui) {
        initialized = false;
        reader = new JsonReader(JSON_FILE);
        writer = new JsonWriter(JSON_FILE);
        loaded = false;
        this.gui = gui;
        initializeUserPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes panel for input
    private void initializeUserPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        setName();
        setWeight();
        setWeightGoal();
        setCalorieGoals();
        setMacroGoals();
        setUserButton();
        setDisplay();
        setHeaders();
        setLoadButton();
        displayInfo(false);
        clearMessages();
    }

    public JPanel getPanel() {
        return panel;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    // MODIFIES: this
    // EFFECTS: adds a button to enter values and instructions if any errors occur
    private void setUserButton() {
        JButton enter = new JButton("Enter");
        enter.setBounds(10, 390, 80, 25);
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearMessages();
                    setUser();
                    update();
                } catch (NumberFormatException exception) {
                    panel.getComponent(16).setVisible(true);
                } catch (NegativeInputException exception) {
                    panel.getComponent(17).setVisible(true);
                } catch (PercentageException exception) {
                    panel.getComponent(18).setVisible(true);
                }
            }
        });
        panel.add(enter); // 15
        setUserErrors();
    }

    // MODIFIES: this
    // EFFECTS: sets up error messages
    private void setUserErrors() {
        JLabel typeError = new JLabel("Invalid input type! Please input numbers (eg. '25')");
        typeError.setBounds(10, 420, 300, 25);
        JLabel negativeError = new JLabel("Illegal input! Please input non-negative numbers!");
        negativeError.setBounds(10, 420, 300, 25);
        JLabel sumError = new JLabel("%s don't add up to 100! Try again!");
        sumError.setBounds(10, 420, 300, 25);
        panel.add(typeError); // 16
        panel.add(negativeError); // 17
        panel.add(sumError); // 18
    }

    // MODIFIES: this
    // EFFECTS: sets user based on text field inputs
    //          throw IllegalArgumentException if input is negative
    //          throw InputMismatchException if percentages don't add to 100
    private void setUser() throws NegativeInputException, PercentageException {
        String name = ((JTextField) panel.getComponent(1)).getText();
        double weight = Double.parseDouble(((JTextField) panel.getComponent(3)).getText());
        double goal = Double.parseDouble(((JTextField) panel.getComponent(5)).getText());
        double calories = Double.parseDouble(((JTextField) panel.getComponent(7)).getText());
        double protein = Double.parseDouble(((JTextField) panel.getComponent(9)).getText());
        double carbs = Double.parseDouble(((JTextField) panel.getComponent(11)).getText());
        double fat = Double.parseDouble(((JTextField) panel.getComponent(13)).getText());
        gui.getUser().updateUser(name, weight, goal, calories, protein, carbs, fat);
        initialized = true;
        panel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: adds name input functionality
    private void setName() {
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(10, 130, 80, 25);
        JTextField nameText = new JTextField(20);
        nameText.setBounds(100, 130, 165, 25);
        panel.add(nameLabel); // 0
        panel.add(nameText); // 1
    }

    // MODIFIES: this
    // EFFECTS: adds weight input functionality
    private void setWeight() {
        JLabel weightLabel = new JLabel("Weight");
        weightLabel.setBounds(10, 160, 80, 25);
        JTextField weightText = new JTextField(20);
        weightText.setBounds(100, 160, 165, 25);
        panel.add(weightLabel); // 2
        panel.add(weightText); // 3
    }

    // MODIFIES: this
    // EFFECTS: adds weight goal input functionality
    private void setWeightGoal() {
        JLabel weightLabel = new JLabel("Weight Goal");
        weightLabel.setBounds(10, 190, 80, 25);
        JTextField weightText = new JTextField(20);
        weightText.setBounds(100, 190, 165, 25);
        panel.add(weightLabel); // 4
        panel.add(weightText); // 5
    }

    // MODIFIES: this
    // EFFECTS: adds calorie input functionality
    private void setCalorieGoals() {
        JLabel calorieLabel = new JLabel("Calories");
        calorieLabel.setBounds(10, 230, 80, 25);
        JTextField calorieText = new JTextField(20);
        calorieText.setBounds(140, 230, 165, 25);
        panel.add(calorieLabel); // 6
        panel.add(calorieText); // 7
    }

    // MODIFIES: this
    // EFFECTS: adds macro goal input functionality
    private void setMacroGoals() {
        JLabel proteinPortion = new JLabel("Protein %");
        proteinPortion.setBounds(20, 260, 100, 25);
        JLabel carbsPortion = new JLabel("Carbohydrates %");
        carbsPortion.setBounds(20, 290, 100, 25);
        JLabel fatPortion = new JLabel("Fat %");
        fatPortion.setBounds(20, 320, 100, 25);
        JTextField proteinText = new JTextField(20);
        proteinText.setBounds(140, 260, 165, 25);
        JTextField carbsText = new JTextField(20);
        carbsText.setBounds(140, 290, 165, 25);
        JTextField fatText = new JTextField(20);
        fatText.setBounds(140, 320, 165, 25);
        JLabel portionNote = new JLabel("Note: %'s must add to 100");
        portionNote.setBounds(10, 350, 200, 25);
        panel.add(proteinPortion); // 8
        panel.add(proteinText); // 9
        panel.add(carbsPortion); // 10
        panel.add(carbsText); // 11
        panel.add(fatPortion); // 12
        panel.add(fatText); // 13
        panel.add(portionNote); // 14
    }

    // MODIFIES: this
    // EFFECTS: adds info to panel for display
    private void setDisplay() {
        User user = gui.getUser();
        JLabel name = new JLabel("Name: " + user.getName());
        name.setBounds(410, 130, 150, 25);
        JLabel weight = new JLabel("Weight: " + user.getWeight());
        weight.setBounds(410, 160, 150, 25);
        JLabel weightGoal = new JLabel("Weight Goal: " + user.getJournal().getGoal());
        weightGoal.setBounds(410, 190, 150, 25);
        panel.add(name); // 19
        panel.add(weight); // 20
        panel.add(weightGoal); // 21
        setMacrosDisplay();
    }

    // MODIFIES: this
    // EFFECTS: adds macro information to panel for display
    private void setMacrosDisplay() {
        JLabel calories = new JLabel("Calories Needed: ");
        calories.setBounds(410, 230, 200, 25);
        JLabel protein = new JLabel("Protein (grams): ");
        protein.setBounds(420, 260, 200, 25);
        JLabel carbs = new JLabel("Carbohydrates (grams): ");
        carbs.setBounds(420, 290, 200, 25);
        JLabel fat = new JLabel("Fat (grams): ");
        fat.setBounds(420, 320, 200, 25);
        panel.add(calories); // 22
        panel.add(protein); // 23
        panel.add(carbs); // 24
        panel.add(fat); // 25
    }

    // MODIFIES: this
    // EFFECTS: adds page headers
    private void setHeaders() {
        Font font = new Font(Font.SERIF, Font.BOLD, 24);
        JLabel update = new JLabel("Update Information");
        update.setBounds(10, 60, 250, 50);
        update.setFont(font);
        JLabel info = new JLabel("User Information");
        info.setBounds(410, 60, 250, 50);
        info.setFont(font);
        JLabel reminder = new JLabel("Input your information before accessing other functions!");
        reminder.setBounds(100, 450, 600, 50);
        reminder.setFont(font);
        panel.add(update); // 26
        panel.add(info); // 27
        panel.add(reminder); // 28
    }

    // MODIFIES: this
    // EFFECTS: adds load functionality
    private void setLoadButton() {
        JLabel noFile = new JLabel(JSON_FILE + " is unable to be read!");
        noFile.setBounds(200, 40, 300, 25);
        JButton load = new JButton("Load");
        load.setBounds(340, 10, 100, 25);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });
        panel.add(noFile); // 29
        panel.add(load); // 30
    }

    private void updateUser(User userCopy) {
        User user = gui.getUser();
        user.setName(userCopy.getName());
        user.setWeight(userCopy.getWeight());
        user.setMacrosNeeded(userCopy.getMacrosNeeded());
        user.setJournal(userCopy.getJournal());
        user.setSaved(userCopy.getSaved());
    }

    // MODIFIES: this
    // EFFECTS: updates info and displays them
    public void update() {
        if (initialized) {
            displayInfo(true);
            setCurrentInfo();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates current info
    private void setCurrentInfo() {
        User user = gui.getUser();
        ((JLabel) panel.getComponent(19)).setText("Name: " + user.getName());
        ((JLabel) panel.getComponent(20)).setText("Weight: " + user.getWeight());
        ((JLabel) panel.getComponent(21)).setText("Weight Goal: " + user.getJournal().getGoal());
        ((JLabel) panel.getComponent(22)).setText("Calories Needed: " + (int) user.getMacrosNeeded().getCalories());
        ((JLabel) panel.getComponent(23)).setText("Protein (grams): " + (int) user.getMacrosNeeded().getProtein());
        ((JLabel) panel.getComponent(24)).setText("Carbohydrates (grams): " + (int) user.getMacrosNeeded().getCarbs());
        ((JLabel) panel.getComponent(25)).setText("Fat (grams): " + (int) user.getMacrosNeeded().getFat());
    }

    // MODIFIES: this
    // EFFECTS: makes error messages invisible
    private void clearMessages() {
        panel.getComponent(16).setVisible(false);
        panel.getComponent(17).setVisible(false);
        panel.getComponent(18).setVisible(false);
        panel.getComponent(29).setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: makes info labels visible and reminder invisible if b is true and vice versa
    private void displayInfo(boolean b) {
        for (int i = 19; i < 26; i++) {
            panel.getComponent(i).setVisible(b);
        }
        panel.getComponent(27).setVisible(b);
        panel.getComponent(28).setVisible(!b);
    }

    // EFFECTS: saves current data to data file if initialized
    public void saveData() {
        if (initialized) {
            try {
                writer.open();
                writer.write(gui.getUser());
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to save data to " + JSON_FILE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: set user based on user read from data file
    public void loadData() {
        try {
            clearMessages();
            updateUser(reader.read());
            initialized = true;
            loaded = true;
            update();
        } catch (IOException e1) {
            panel.getComponent(29).setVisible(true);
        }
    }
}
