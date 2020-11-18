package ui.gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.util.InputMismatchException;

/**
 * FavouritesPanel initializes the panel that handles the visibility and functionality for a user's favourite foods
 */
public class FavouritesPanel {

    private static final String FOOD = "food";
    private static final String INGREDIENTS = "ingredients";

    private Journal journal;
    private Favourites saved;
    private int currentFood;
    private int currentIngredient;
    private JPanel panel;
    private GUI gui;

    // EFFECTS: constructs a favourites panel
    public FavouritesPanel(User user, GUI gui) {
        updateUser(user);
        currentFood = 0;
        currentIngredient = 0;
        this.gui = gui;
        initializePanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: updates the journal and saved based on user
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
        initializeAddFood();
        initializeFoodInfo();
        clearMessages();
    }

    // MODIFIES: this
    // EFFECTS: initialize header components
    private void initializeHeader() {
        Font font = new Font(Font.SERIF, Font.BOLD, 24);
        JLabel add = new JLabel("Add to Favourites");
        add.setBounds(30, 50, 200, 50);
        add.setFont(font);
        JLabel view = new JLabel("View Favourites");
        view.setBounds(420, 50, 200, 50);
        view.setFont(font);
        panel.add(add); // 0
        panel.add(view); // 1
    }

    // MODIFIES: this
    // EFFECTS: initialize add food components
    private void initializeAddFood() {
        initializeAddFoodLabels();
        initializeAddFoodMacros();
        initializeAddFoodReminders();
        initializeAddFoodButton();
    }

    // MODIFIES: this
    // EFFECTS: initialize add food labels
    private void initializeAddFoodLabels() {
        JLabel name = new JLabel("Food Name");
        name.setBounds(10, 160, 80, 25);
        JTextField nameText = new JTextField(20);
        nameText.setBounds(100, 160, 120, 25);
        JLabel calories = new JLabel("Calories");
        calories.setBounds(10, 210, 80, 25);
        JTextField caloriesText = new JTextField(20);
        caloriesText.setBounds(140, 210, 80, 25);
        panel.add(name); // 2
        panel.add(nameText); // 3
        panel.add(calories); // 4
        panel.add(caloriesText); // 5
    }

    // MODIFIES: this
    // EFFECTS: initialize food macros labels
    private void initializeAddFoodMacros() {
        JLabel protein = new JLabel("Protein (g)");
        protein.setBounds(20, 240, 100, 25);
        JLabel carbs = new JLabel("Carbohydrates (g)");
        carbs.setBounds(20, 270, 110, 25);
        JLabel fat = new JLabel("Fat (g)");
        fat.setBounds(20, 300, 100, 25);
        JTextField proteinText = new JTextField(20);
        proteinText.setBounds(140, 240, 80, 25);
        JTextField carbsText = new JTextField(20);
        carbsText.setBounds(140, 270, 80, 25);
        JTextField fatText = new JTextField(20);
        fatText.setBounds(140, 300, 80, 25);
        panel.add(protein); // 6
        panel.add(proteinText); // 7
        panel.add(carbs); // 8
        panel.add(carbsText); // 9
        panel.add(fat); // 10
        panel.add(fatText); // 11
    }

    // MODIFIES: this
    // EFFECTS: initialize add food input reminders
    private void initializeAddFoodReminders() {
        JLabel typeError = new JLabel("Invalid input type! Please input numbers (eg. '25')");
        typeError.setBounds(10, 380, 300, 25);
        JLabel negativeError = new JLabel("Illegal input! Please input non-negative numbers!");
        negativeError.setBounds(10, 380, 300, 25);
        JLabel meals = new JLabel("To add meals, access 'New Food'!");
        meals.setBounds(20, 110, 250, 25);
        panel.add(typeError); // 12
        panel.add(negativeError); // 13
        panel.add(meals); // 14
    }

    // MODIFIES: this
    // EFFECTS: initialize add food button
    private void initializeAddFoodButton() {
        JButton addFood = new JButton("Add Food");
        addFood.setBounds(50, 340, 100, 25);
        addFood.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearMessages();
                    addNewFood();
                    update();
                } catch (NumberFormatException e1) {
                    panel.getComponent(12).setVisible(true);
                } catch (IllegalArgumentException e1) {
                    panel.getComponent(13).setVisible(true);
                }
            }
        });
        panel.add(addFood); // 15
    }

    // MODIFIES: this
    // EFFECTS: add a new food based on text field input
    //          throw IllegalArgumentException if input is negative
    private void addNewFood() {
        String name = ((JTextField) panel.getComponent(3)).getText();
        double calories = Double.parseDouble(((JTextField) panel.getComponent(5)).getText());
        double protein = Double.parseDouble(((JTextField) panel.getComponent(7)).getText());
        double carbs = Double.parseDouble(((JTextField) panel.getComponent(9)).getText());
        double fat = Double.parseDouble(((JTextField) panel.getComponent(11)).getText());
        if (calories < 0 || protein < 0 || carbs < 0 || fat < 0) {
            throw new IllegalArgumentException();
        }
        saved.addFood(new FoodItem(name, new Macros(protein, carbs, fat)));
        currentFood = saved.getFoods().size();
    }

    // MODIFIES: this
    // EFFECTS: initialize food info components
    private void initializeFoodInfo() {
        initializeMainFoodInfo();
        initializeIngredientsInfo();
        initializeFoodButtons();
    }

    // MODIFIES: this
    // EFFECTS: initialize food info labels
    private void initializeMainFoodInfo() {
        JLabel noEntries = new JLabel("You have no food saved!");
        noEntries.setBounds(440, 230, 250, 25);
        JLabel name = new JLabel("Food Name:");
        name.setBounds(350, 160, 150, 25);
        JLabel calories = new JLabel("Calories:");
        calories.setBounds(350, 210, 150, 25);
        JLabel protein = new JLabel("Protein:");
        protein.setBounds(350, 240, 150, 25);
        JLabel carbs = new JLabel("Carbohydrates:");
        carbs.setBounds(350, 270, 150, 25);
        JLabel fat = new JLabel("Fat:");
        fat.setBounds(350, 300, 150, 25);
        panel.add(noEntries); // 16
        panel.add(name); // 17
        panel.add(calories); // 18
        panel.add(protein); // 19
        panel.add(carbs); // 20
        panel.add(fat); // 21
    }

    // MODIFIES: this
    // EFFECTS: initialize ingredient labels
    private void initializeIngredientsInfo() {
        JLabel ingredients = new JLabel("Ingredients:");
        ingredients.setBounds(560, 160, 150, 25);
        JLabel name = new JLabel("---");
        name.setBounds(560, 210, 150, 25);
        panel.add(ingredients); // 22
        panel.add(name); // 23
        initializePreviousIngredientButton();
        initializeNextIngredientButton();
    }

    // MODIFIES: this
    // EFFECTS: initialize previous ingredient button
    private void initializePreviousIngredientButton() {
        JButton previous = new JButton("Previous");
        previous.setBounds(540, 270, 85, 25);
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                rotate(false, INGREDIENTS);
                update();
            }
        });
        panel.add(previous); // 24
    }

    // MODIFIES: this
    // EFFECTS: initialize next ingredient button
    private void initializeNextIngredientButton() {
        JButton next = new JButton("Next");
        next.setBounds(650, 270, 85, 25);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                rotate(true, INGREDIENTS);
                update();
            }
        });
        panel.add(next); // 25
    }

    // MODIFIES: this
    // EFFECTS: initialize food buttons
    private void initializeFoodButtons() {
        initializeTimeOfEntry();
        initializeAddEntryButton();
        initializeRemoveButton();
        initializePreviousFoodButton();
        initializeNextFoodButton();
    }

    // MODIFIES: this
    // EFFECTS: initialize time eaten input components
    private void initializeTimeOfEntry() {
        JLabel time = new JLabel("Time Eaten:");
        time.setBounds(350, 340, 80, 25);
        JTextField timeText = new JTextField(10);
        timeText.setBounds(450, 340, 80, 25);
        JLabel typeError = new JLabel("Invalid input type! Please input numbers (eg. '25')");
        typeError.setBounds(350, 370, 300, 25);
        JLabel invalidTime = new JLabel("Invalid time! Time must be between 0 and 23!");
        invalidTime.setBounds(350, 370, 300, 25);
        panel.add(time); // 26
        panel.add(timeText); // 27
        panel.add(typeError); // 28
        panel.add(invalidTime); // 29
    }

    // MODIFIES: this
    // EFFECTS: initialize add entry button
    private void initializeAddEntryButton() {
        JButton entry = new JButton("Add Entry");
        entry.setBounds(550, 340, 100, 25);
        entry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearMessages();
                    addEntry();
                    update();
                } catch (NumberFormatException e1) {
                    panel.getComponent(28).setVisible(true);
                } catch (InputMismatchException e1) {
                    panel.getComponent(29).setVisible(true);
                }
            }
        });
        panel.add(entry); // 30
    }

    // MODIFIES: this
    // EFFECTS: add entry to logs based on current food
    //          throw InputMismatchException if time < 0 or time > 23
    private void addEntry() {
        Food food = saved.getFoods().get(currentFood - 1);
        int time = Integer.parseInt(((JTextField) panel.getComponent(27)).getText());
        if (time < 0 || time > 23) {
            throw new InputMismatchException();
        }
        journal.addEntry(new Entry(food, time));
        gui.getPlayer().playSoundWhenMacrosAreHit();
    }

    // MODIFIES: this
    // EFFECTS: initialize remove button
    private void initializeRemoveButton() {
        JButton removeButton = new JButton("Remove Food");
        removeButton.setBounds(430, 420, 120, 25);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                if (currentFood > 0) {
                    saved.removeFood(saved.getFoods().get(currentFood - 1));
                }
                update();
            }
        });
        panel.add(removeButton); // 31
    }

    // MODIFIES: this
    // EFFECTS: initialize previous food button
    private void initializePreviousFoodButton() {
        JButton previous = new JButton("Previous");
        previous.setBounds(300, 420, 100, 25);
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                if (currentFood > 0) {
                    rotate(false, FOOD);
                }
                update();
            }
        });
        panel.add(previous); // 32
    }

    // MODIFIES: this
    // EFFECTS: initialize next food button
    private void initializeNextFoodButton() {
        JButton next = new JButton("Next");
        next.setBounds(580, 420, 100, 25);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMessages();
                if (currentFood > 0) {
                    rotate(true, FOOD);
                }
                update();
            }
        });
        panel.add(next); // 33
    }

    // MODIFIES: this
    // EFFECTS: clear error messages
    private void clearMessages() {
        panel.getComponent(12).setVisible(false);
        panel.getComponent(13).setVisible(false);
        panel.getComponent(28).setVisible(false);
        panel.getComponent(29).setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: find the next or previous element in list based on input
    //          throw InvalidParameterException if list is not "food" or ingredients"
    private void rotate(boolean forward, String list) {
        if (list.equals("food")) {
            if (forward) {
                currentFood = (currentFood == saved.getFoods().size()) ? 1 : currentFood + 1;
            } else {
                currentFood = (currentFood == 1) ? saved.getFoods().size() : currentFood - 1;
            }
        } else if (list.equals("ingredients")) {
            if (saved.getFoods().get(currentFood - 1).isMeal()) {
                Meal meal = (Meal) saved.getFoods().get(currentFood - 1);
                if (forward) {
                    currentIngredient = (currentIngredient == meal.getIngredients().size()) ? 1 : currentIngredient + 1;
                } else {
                    currentIngredient = (currentIngredient == 1) ? meal.getIngredients().size() : currentIngredient - 1;
                }
            }
        } else {
            throw new InvalidParameterException();
        }
    }

    // MODIFIES: this
    // EFFECTS: update labels and fields
    public void update() {
        updateFood();
        if (currentFood > 0) {
            updateIngredients();
        }
    }

    // MODIFIES: this
    // EFFECTS: update food labels and fields
    private void updateFood() {
        if (currentFood > saved.getFoods().size()) {
            currentFood = saved.getFoods().size();
        }
        if (!saved.getFoods().isEmpty() && currentFood == 0) {
            currentFood = 1;
        }
        if (currentFood == 0) {
            showCurrentFood(false);
        } else {
            showCurrentFood(true);
            setCurrentFood();
        }
    }

    // MODIFIES: this
    // EFFECTS: update ingredient labels and fields
    private void updateIngredients() {
        if (saved.getFoods().get(currentFood - 1).isMeal()) {
            if (currentIngredient == 0) {
                currentIngredient = 1;
            }
            showCurrentIngredient(true);
            setCurrentIngredient();
        } else {
            currentIngredient = 0;
            showCurrentIngredient(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: set visibility of food components
    private void showCurrentFood(boolean b) {
        panel.getComponent(16).setVisible(!b);
        for (int i = 17; i < 28; i++) {
            panel.getComponent(i).setVisible(b);
        }
        panel.getComponent(30).setVisible(b);
    }

    // MODIFIES: this
    // EFFECTS: set visibility of ingredient components
    private void showCurrentIngredient(boolean b) {
        panel.getComponent(22).setVisible(b);
        panel.getComponent(23).setVisible(b);
        panel.getComponent(24).setVisible(b);
        panel.getComponent(25).setVisible(b);
    }

    // MODIFIES: this
    // EFFECTS: updates current food info
    private void setCurrentFood() {
        String foodName = saved.getFoods().get(currentFood - 1).getName();
        ((JLabel) panel.getComponent(17)).setText("Food Name: " + foodName);
        double calories = saved.getFoods().get(currentFood - 1).getMacros().getCalories();
        ((JLabel) panel.getComponent(18)).setText("Calories: " + calories);
        double protein = saved.getFoods().get(currentFood - 1).getMacros().getProtein();
        ((JLabel) panel.getComponent(19)).setText("Protein: " + protein);
        double carbs = saved.getFoods().get(currentFood - 1).getMacros().getCarbs();
        ((JLabel) panel.getComponent(20)).setText("Carbohydrates: " + carbs);
        double fat = saved.getFoods().get(currentFood - 1).getMacros().getFat();
        ((JLabel) panel.getComponent(21)).setText("Fat: " + fat);
    }

    // MODIFIES: this
    // EFFECTS: updates current ingredient info
    private void setCurrentIngredient() {
        Meal currentMeal = (Meal) saved.getFoods().get(currentFood - 1);
        String name = currentMeal.getIngredients().get(currentIngredient - 1).getName();
        ((JLabel) panel.getComponent(23)).setText("--- " + name);
    }
}
