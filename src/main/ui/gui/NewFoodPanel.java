package ui.gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.InputMismatchException;

/**
 * NewFoodPanel initializes the panel that handles the visibility and functionality for a user to create foods
 */
public class NewFoodPanel {

    private Journal journal;
    private Favourites saved;
    private JPanel panel;
    private Meal meal;
    private boolean setName;
    private boolean isMeal;
    private GUI gui;

    // EFFECTS: constructs a newFoodPanel
    public NewFoodPanel(User user, GUI gui) {
        updateUser(user);
        meal = null;
        setName = false;
        isMeal = false;
        this.gui = gui;
        initializePanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: update journal and saved based on input
    public void updateUser(User user) {
        journal = user.getJournal();
        saved = user.getSaved();
    }

    // MODIFIES: this
    // EFFECTS: initialize panel components
    private void initializePanel() {
        panel = new JPanel();
        panel.setLayout(null);
        initializeHeader();
        initializeMealOption();
        initializeNewFood();
        initializeErrorLabels();
        initializeButtons();
        clearMessages();
    }

    // MODIFIES: this
    // EFFECTS: initialize header labels
    private void initializeHeader() {
        Font font = new Font(Font.SERIF, Font.BOLD, 24);
        JLabel meal = new JLabel("Meal Name");
        meal.setBounds(100, 20, 200, 50);
        meal.setFont(font);
        JLabel create = new JLabel("Create Food");
        create.setBounds(300, 120, 200, 50);
        create.setFont(font);
        JLabel ingredient = new JLabel("Add Ingredient");
        ingredient.setBounds(300, 120, 200, 50);
        ingredient.setFont(font);
        panel.add(meal); // 0
        panel.add(create); // 1
        panel.add(ingredient); // 2
    }

    // MODIFIES: this
    // EFFECTS: initialize meal option components
    private void initializeMealOption() {
        initializeCheckBox();
        initializeMealNameInput();
        initializeSetMealButton();
    }

    // MODIFIES: this
    // EFFECTS: initialize check box
    private void initializeCheckBox() {
        JCheckBox checkBox = new JCheckBox("Meal");
        checkBox.setBounds(20, 30, 80, 25);
        checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                isMeal = e.getStateChange() == ItemEvent.SELECTED;
                meal = (isMeal) ? new Meal("Meal") : null;
                setName = false;
                update();
            }
        });
        panel.add(checkBox); // 3
    }

    // MODIFIES: this
    // EFFECTS: initialize meal name text field
    private void initializeMealNameInput() {
        JTextField name = new JTextField(20);
        name.setBounds(250, 20, 300, 50);
        name.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        JLabel reminder = new JLabel("Set meal name before proceeding");
        reminder.setBounds(300, 80, 300, 25);
        panel.add(name); // 4
        panel.add(reminder); // 5
    }

    // MODIFIES: this
    // EFFECTS: initialize set meal name button
    private void initializeSetMealButton() {
        JButton setMeal = new JButton("Set Meal Name");
        setMeal.setBounds(580, 20, 150, 50);
        setMeal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                meal.setName(((JTextField) panel.getComponent(4)).getText());
                setName = true;
                update();
            }
        });
        panel.add(setMeal); // 6
    }

    // MODIFIES: this
    // EFFECTS: initialize new food components
    private void initializeNewFood() {
        initializeFoodLabels();
        initializeMacroLabels();
        initializeAddIngredientButton();
        initializeTimeOfEntry();
        initializeIngredientTracker();
    }

    // MODIFIES: this
    // EFFECTS: initialize food labels
    private void initializeFoodLabels() {
        JLabel name = new JLabel("Food Name");
        name.setBounds(250, 180, 80, 25);
        JTextField nameText = new JTextField(20);
        nameText.setBounds(350, 180, 120, 25);
        JLabel calories = new JLabel("Calories");
        calories.setBounds(250, 240, 80, 25);
        JTextField caloriesText = new JTextField(20);
        caloriesText.setBounds(390, 240, 80, 25);
        panel.add(name); // 7
        panel.add(nameText); // 8
        panel.add(calories); // 9
        panel.add(caloriesText); // 10
    }

    // MODIFIES: this
    // EFFECTS: initialize macro labels
    private void initializeMacroLabels() {
        JLabel protein = new JLabel("Protein (g)");
        protein.setBounds(260, 270, 100, 25);
        JLabel carbs = new JLabel("Carbohydrates (g)");
        carbs.setBounds(260, 300, 110, 25);
        JLabel fat = new JLabel("Fat (g)");
        fat.setBounds(260, 330, 100, 25);
        JTextField proteinText = new JTextField(20);
        proteinText.setBounds(390, 270, 80, 25);
        JTextField carbsText = new JTextField(20);
        carbsText.setBounds(390, 300, 80, 25);
        JTextField fatText = new JTextField(20);
        fatText.setBounds(390, 330, 80, 25);
        panel.add(protein); // 11
        panel.add(proteinText); // 12
        panel.add(carbs); // 13
        panel.add(carbsText); // 14
        panel.add(fat); // 15
        panel.add(fatText); // 16
    }

    // MODIFIES: this
    // EFFECTS: initialize add ingredient button
    private void initializeAddIngredientButton() {
        JButton ingredient = new JButton("Add Ingredient");
        ingredient.setBounds(550, 240, 120, 40);
        ingredient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearMessages();
                    addNewIngredient();
                    update();
                } catch (NumberFormatException e1) {
                    panel.getComponent(12).setVisible(true);
                } catch (IllegalArgumentException e1) {
                    panel.getComponent(13).setVisible(true);
                }
            }
        });
        panel.add(ingredient); // 17
    }

    // MODIFIES: this
    // EFFECTS: add ingredient to current meal
    //          throw IllegalArgumentException if user input is negative
    private void addNewIngredient() {
        String name = ((JTextField) panel.getComponent(8)).getText();
        double calories = Double.parseDouble(((JTextField) panel.getComponent(10)).getText());
        double protein = Double.parseDouble(((JTextField) panel.getComponent(12)).getText());
        double carbs = Double.parseDouble(((JTextField) panel.getComponent(14)).getText());
        double fat = Double.parseDouble(((JTextField) panel.getComponent(16)).getText());
        if (calories < 0 || protein < 0 || carbs < 0 || fat < 0) {
            throw new IllegalArgumentException();
        }
        meal.addIngredient(new FoodItem(name, new Macros(protein, carbs, fat)));
    }

    // MODIFIES: this
    // EFFECTS: initialize time eaten components
    private void initializeTimeOfEntry() {
        JLabel time = new JLabel("Time Eaten");
        time.setBounds(250, 370, 80, 25);
        JTextField timeText = new JTextField(10);
        timeText.setBounds(390, 370, 80, 25);
        panel.add(time); // 18
        panel.add(timeText); // 19
    }

    // MODIFIES: this
    // EFFECTS: initialize ingredient tracker
    private void initializeIngredientTracker() {
        JLabel count = new JLabel("Ingredient Count: ");
        count.setBounds(550, 300, 200, 25);
        JLabel last = new JLabel("Just added: ");
        last.setBounds(550, 330, 200, 25);
        JLabel ingredient = new JLabel("---");
        ingredient.setBounds(550, 360, 200, 25);
        panel.add(count); // 20
        panel.add(last); // 21
        panel.add(ingredient); // 22
    }

    // MODIFIES: this
    // EFFECTS: initialize error labels
    private void initializeErrorLabels() {
        JLabel typeError = new JLabel("Invalid input type! Please input numbers (eg. '25')");
        typeError.setBounds(250, 400, 300, 25);
        JLabel negativeError = new JLabel("Illegal input! Please input non-negative numbers!");
        negativeError.setBounds(250, 400, 300, 25);
        JLabel invalidTime = new JLabel("Invalid time! Time must be between 0 and 23!");
        invalidTime.setBounds(250, 400, 300, 25);
        JLabel noIngredients = new JLabel("This meal has no ingredients!");
        noIngredients.setBounds(250, 400, 300, 25);
        panel.add(typeError); // 23
        panel.add(negativeError); // 24
        panel.add(invalidTime); // 25
        panel.add(noIngredients); // 26
    }

    // MODIFIES: this
    // EFFECTS: initialize buttons
    private void initializeButtons() {
        initializeAddEntryButton();
        initializeAddFavouriteButton();
        initializeResetButton();
    }

    // MODIFIES: this
    // EFFECTS: initialize add entry button
    private void initializeAddEntryButton() {
        JButton addEntry = new JButton("Add To Logs");
        addEntry.setBounds(250, 450, 120, 40);
        addEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearMessages();
                    addNewEntry();
                    update();
                } catch (NumberFormatException e1) {
                    panel.getComponent(23).setVisible(true);
                } catch (IllegalArgumentException e1) {
                    panel.getComponent(24).setVisible(true);
                } catch (InputMismatchException e1) {
                    panel.getComponent(25).setVisible(true);
                }
            }
        });
        panel.add(addEntry); // 27
    }

    // MODIFIES: this
    // EFFECTS: add new entry
    //          throw IllegalArgumentException if negative inputs are found
    private void addNewEntry() {
        if (isMeal) {
            if (meal.getIngredients().isEmpty()) {
                panel.getComponent(26).setVisible(true);
            } else {
                int time = getTime();
                journal.addEntry(new Entry(meal, time));
            }
        } else {
            String name = ((JTextField) panel.getComponent(8)).getText();
            double calories = Double.parseDouble(((JTextField) panel.getComponent(10)).getText());
            double protein = Double.parseDouble(((JTextField) panel.getComponent(12)).getText());
            double carbs = Double.parseDouble(((JTextField) panel.getComponent(14)).getText());
            double fat = Double.parseDouble(((JTextField) panel.getComponent(16)).getText());
            if (calories < 0 || protein < 0 || carbs < 0 || fat < 0) {
                throw new IllegalArgumentException();
            }
            int time = getTime();
            journal.addEntry(new Entry(new FoodItem(name, new Macros(protein, carbs, fat)), time));
        }
        gui.getPlayer().playSoundWhenMacrosAreHit();
    }

    // MODIFIES: this
    // EFFECTS: return time text field input
    //          throw InputMismatchException if time < 0 or time > 23
    private int getTime() {
        int time = Integer.parseInt(((JTextField) panel.getComponent(19)).getText());
        if (time < 0 || time > 23) {
            throw new InputMismatchException();
        }
        return time;
    }

    // MODIFIES: this
    // EFFECTS: initialize add favourite button
    private void initializeAddFavouriteButton() {
        JButton addFavourite = new JButton("Add Favourite");
        addFavourite.setBounds(450, 450, 120, 40);
        addFavourite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearMessages();
                    addToFavourite();
                    update();
                } catch (NumberFormatException e1) {
                    panel.getComponent(23).setVisible(true);
                } catch (IllegalArgumentException e1) {
                    panel.getComponent(24).setVisible(true);
                }
            }
        });
        panel.add(addFavourite); // 28
    }

    // MODIFIES: this
    // EFFECTS: add to favourite
    //          throw IllegalArgumentException if user input is negative
    private void addToFavourite() {
        if (isMeal) {
            if (meal.getIngredients().isEmpty()) {
                panel.getComponent(26).setVisible(true);
            } else {
                saved.addFood(meal);
            }
        } else {
            String name = ((JTextField) panel.getComponent(8)).getText();
            double calories = Double.parseDouble(((JTextField) panel.getComponent(10)).getText());
            double protein = Double.parseDouble(((JTextField) panel.getComponent(12)).getText());
            double carbs = Double.parseDouble(((JTextField) panel.getComponent(14)).getText());
            double fat = Double.parseDouble(((JTextField) panel.getComponent(16)).getText());
            if (calories < 0 || protein < 0 || carbs < 0 || fat < 0) {
                throw new IllegalArgumentException();
            }
            saved.addFood(new FoodItem(name, new Macros(protein, carbs, fat)));
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize reset button
    private void initializeResetButton() {
        JButton reset = new JButton("Reset");
        reset.setBounds(50, 240, 120, 40);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                meal = (isMeal) ? new Meal("Meal") : null;
                setName = false;
            }
        });
        panel.add(reset); // 29
    }

    // MODIFIES: this
    // EFFECTS: clear messages
    private void clearMessages() {
        panel.getComponent(23).setVisible(false);
        panel.getComponent(24).setVisible(false);
        panel.getComponent(25).setVisible(false);
        panel.getComponent(26).setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: update components and info
    public void update() {
        updateVisibility();
        if (isMeal) {
            updateIngredientInfo();
        }
    }

    // MODIFIES: this
    // EFFECTS: update component visibility
    private void updateVisibility() {
        panel.getComponent(0).setVisible(isMeal);
        panel.getComponent(1).setVisible(!isMeal);
        panel.getComponent(2).setVisible(isMeal);
        panel.getComponent(4).setVisible(isMeal);
        panel.getComponent(5).setVisible(isMeal);
        panel.getComponent(6).setVisible(isMeal);
        panel.getComponent(17).setVisible(isMeal && setName);
        panel.getComponent(20).setVisible(isMeal && setName);
        panel.getComponent(21).setVisible(isMeal && setName);
        panel.getComponent(22).setVisible(isMeal && setName);
        panel.getComponent(29).setVisible(isMeal && setName);
    }

    // MODIFIES: this
    // EFFECTS: update ingredient info
    private void updateIngredientInfo() {
        int size = meal.getIngredients().size();
        ((JLabel) panel.getComponent(20)).setText("Ingredient Count: " + size);
        if (size > 0) {
            ((JLabel) panel.getComponent(22)).setText("--- " + meal.getIngredients().get(size - 1).getName());
        }
    }
}
