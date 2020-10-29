package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * MacroTracker represents the console interaction of the project and is the ui class that allows for user interaction.
 * Used the TellerApp from https://github.students.cs.ubc.ca/CPSC210/TellerApp for reference.
 * Used the JsonSerializationDemo from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo for reference.
 */
public class MacroTracker {

    private static final String JSON_FILE = "./data/user.json";
    private User user;
    private Scanner scanner;
    private JsonReader reader;
    private JsonWriter writer;
    private boolean keepGoing;

    // EFFECTS: initializes scanner and starts the application
    public MacroTracker() {
        scanner = new Scanner(System.in);
        reader = new JsonReader(JSON_FILE);
        writer = new JsonWriter(JSON_FILE);
        keepGoing = true;
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: runs the application
    private void runTracker() {
        while (true) {
            System.out.println("'new' to for a new user account");
            System.out.println("'load' to load saved data");
            String command = scanner.nextLine();
            if (command.equals("new")) {
                init();
                break;
            } else if (command.equals("load")) {
                loadData();
                break;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
        processCommand();
        saveBeforeQuit();
    }

    // MODIFIES: this
    // EFFECTS: processes user command for whether to save before quitting
    private void saveBeforeQuit() {
        while (true) {
            System.out.println("\nWould you like to save your data?");
            System.out.println("'save' to save your data");
            System.out.println("'quit' to quit");
            String command = scanner.nextLine();
            if (command.equals("save")) {
                saveData();
                break;
            } else if (command.equals("quit")) {
                break;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the User's information based on console log input
    private void init() {
        System.out.println("What's your name?");
        String name = scanner.nextLine();
        System.out.println("What's your current weight?");
        double weight = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("What's your weight goal?");
        double weightGoal = scanner.nextDouble();
        scanner.nextLine();
        user = new User(name, weight, weightGoal);
        setMacroGoals();
        user.getJournal().nextDay(weight);
    }

    // EFFECTS: returns the calories selected by user
    private double selectCalories() {
        while (true) {
            System.out.println("How many calories do you need in a day?");
            double command = scanner.nextDouble();
            scanner.nextLine();
            if (command >= 0) {
                return command;
            } else {
                System.out.println("Input is invalid! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the macro goals based on the calories input
    private void setMacroGoals() {
        double calories = selectCalories();
        while (true) {
            System.out.println("Ensuring that protein % + carbohydrates % + fat % = 100%");
            System.out.println("What percentage of your calories should be protein?");
            double protein = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("What percentage of your calories should be carbohydrates?");
            double carbs = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("What percentage of your calories should be fat?");
            double fat = scanner.nextDouble();
            scanner.nextLine();
            if (protein >= 0 && carbs >= 0 && fat >= 0 && protein + carbs + fat == 100) {
                user.setMacroGoals(calories, protein, carbs, fat);
                System.out.println("Macro goals have successfully been set!");
                break;
            } else {
                System.out.println("At least one of the values inputted was invalid! Please try again!");
            }
        }
    }

    // EFFECTS: displays the various options available to the user
    private void displayOptions() {
        System.out.println("\n'info' to update your information");
        System.out.println("'prog' to view your progress");
        System.out.println("'favs' to view your favourites");
        System.out.println("'logs' to access your journal logs");
        System.out.println("'food' to create a new food item or meal");
        System.out.println("'data' to access data options (save / load)");
        System.out.println("'quit' to quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand() {
        while (keepGoing) {
            displayOptions();
            String command = scanner.nextLine();
            if (command.equals("info")) {
                information();
            } else if (command.equals("prog")) {
                progress();
            } else if (command.equals("favs")) {
                favourites();
            } else if (command.equals("logs")) {
                journal();
            } else if (command.equals("food")) {
                newFood();
            } else if (command.equals("data")) {
                data();
            } else if (command.equals("quit")) {
                keepGoing = false;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // EFFECTS: provides options for managing data
    private void dataOptions() {
        System.out.println("\n'save' to save current data");
        System.out.println("'load' to load data on file");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for data
    private void data() {
        while (true) {
            dataOptions();
            String command = scanner.nextLine();
            if (command.equals("save")) {
                saveData();
                break;
            } else if (command.equals("load")) {
                loadData();
                break;
            } else if (command.equals("back") || command.equals("home")) {
                break;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads user data from file
    private void loadData() {
        try {
            user = reader.read();
            System.out.println("Loaded " + user.getName() + "'s data from " + JSON_FILE);
        } catch (IOException e) {
            System.out.println(JSON_FILE + " is unable to be read!");
        }
    }

    // MODIFIES: this
    // EFFECTS: saves user data to file
    private void saveData() {
        try {
            writer.open();
            writer.write(user);
            writer.close();
            System.out.println("Saved " + user.getName() + "'s data to " + JSON_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save data to " + JSON_FILE);
        }
    }

    // EFFECTS: prints the user's information and provides options for next command
    private void informationMessages() {
        System.out.println("Name: " + user.getName());
        System.out.println("Weight: " + user.getWeight());
        System.out.println("Weight Goal: " + user.getJournal().getGoal());
        System.out.println("Macro Goals: " + (int) user.getMacrosNeeded().getCalories() + " calories, "
                + (int) user.getMacrosNeeded().getProtein() + " grams of protein, "
                + (int) user.getMacrosNeeded().getCarbs() + " grams of carbohydrates, and "
                + (int) user.getMacrosNeeded().getFat() + " grams of fat");
        System.out.println("\n'update' to change personal info");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for information
    private void information() {
        boolean redo = true;
        while (redo) {
            informationMessages();
            String command = scanner.nextLine();
            if (command.equals("update")) {
                redo = updateInfo();
            } else if (command.equals("back") || command.equals("home")) {
                redo = false;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // EFFECTS: prints the options for updating user information
    private void updateInfoMessages() {
        System.out.println("\n'name' to update name");
        System.out.println("'weight' to update weight");
        System.out.println("'goal' to update weight goal");
        System.out.println("'macro' to update macro goals");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for updating user information, returns false if 'home', true if 'back'
    private boolean updateInfo() {
        while (true) {
            updateInfoMessages();
            String command = scanner.nextLine();
            if (command.equals("name")) {
                changeName();
            } else if (command.equals("weight")) {
                changeWeight();
            } else if (command.equals("goal")) {
                changeGoal();
            } else if (command.equals("macro")) {
                setMacroGoals();
            } else if (command.equals("back")) {
                return true;
            } else if (command.equals("home")) {
                return false;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the goal of the user based on input
    private void changeGoal() {
        System.out.println("Current goal: " + user.getJournal().getGoal());
        while (true) {
            System.out.println("What will your new goal be?");
            double goal = scanner.nextDouble();
            scanner.nextLine();
            if (goal > 0) {
                user.getJournal().setGoal(goal);
                System.out.println("Change successfully made!");
                break;
            } else {
                System.out.println("Input was invalid! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the weight of the user based on input
    private void changeWeight() {
        System.out.println("Current weight: " + user.getWeight());
        while (true) {
            System.out.println("What's your weight now?");
            double weight = scanner.nextDouble();
            scanner.nextLine();
            if (weight > 0) {
                user.updateWeight(weight);
                System.out.println("Change successfully made!");
                break;
            } else {
                System.out.println("Input was invalid! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the name of the user based on input
    private void changeName() {
        System.out.println("Name: " + user.getName());
        System.out.println("What's your new name?");
        String name = scanner.nextLine();
        user.setName(name);
        System.out.println("Change successfully made!");
    }

    // EFFECTS: prints macro progress depending on progress
    private void macroProgressMessages(double calories, double protein, double carbs, double fat) {
        if (user.metCalorieGoals()) {
            System.out.println("You have reached your calorie goals!");
        } else {
            System.out.println("You need " + (int) calories + " more calories for today!");
        }
        if (user.metProteinGoals()) {
            System.out.println("You have reached your protein goals!");
        } else {
            System.out.println("You need " + (int) protein + " more grams of protein for today!");
        }
        if (user.metCarbGoals()) {
            System.out.println("You have reached your carbohydrates goals!");
        } else {
            System.out.println("You need " + (int) carbs + " more grams of carbohydrates for today!");
        }
        if (user.metFatGoals()) {
            System.out.println("You have reached your fat goals!");
        } else {
            System.out.println("You need " + (int) fat + " more grams of fat for today!");
        }
    }

    // EFFECTS: prints the macro progress for the day
    private void macroProgress() {
        Macros macros = user.remainingMacros();
        double caloriesToGo = macros.getCalories();
        double proteinToGo = macros.getProtein();
        double carbsToGo = macros.getCarbs();
        double fatToGo = macros.getFat();
        macroProgressMessages(caloriesToGo, proteinToGo, carbsToGo, fatToGo);
    }

    // EFFECTS: prints the user's progress if there is any
    private void progress() {
        double weightProgress = user.getJournal().viewProgress();
        System.out.println("You are " + (int) weightProgress + "% of the way towards your goal!");
        macroProgress();
    }

    // EFFECTS: prints the options for the user's favourites
    private void favouritesMessages() {
        System.out.println("'view' to see a list of your favourite foods");
        System.out.println("'add' to add a new food to your favourite foods");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for favourites
    private void favourites() {
        boolean redo = true;
        while (redo) {
            favouritesMessages();
            String command = scanner.nextLine();
            if (command.equals("view")) {
                redo = viewFavourites();
            } else if (command.equals("add")) {
                user.addFavourite(makeFood());
                System.out.println("Food was successfully added to favourites!");
            } else if (command.equals("back") || command.equals("home")) {
                redo = false;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // EFFECTS: prints the user's favourite foods
    private void displayFavourites() {
        for (Food food : user.getSaved().getFoods()) {
            System.out.println(
                    food.getName() + ":\n" + "\t" + food.getCalories() + " calories, "
                            + food.getProtein() + " grams of protein, " + food.getCarbs() + " grams of carbs, and "
                            + food.getFat() + " grams of fat"
            );
        }
    }

    // EFFECTS: prints the options after viewing favourites
    private void viewFavouritesMessages() {
        System.out.println("\n'add' to add a favourite food to current day's logs");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // EFFECTS: prints the user's favourites if there are any
    private void displayFavouritesIfAvailable() {
        if (!user.getSaved().getFoods().isEmpty()) {
            displayFavourites();
        } else {
            System.out.println("You have no foods saved yet!");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for favourites, returns false if 'home', true if 'back'
    private boolean viewFavourites() {
        while (true) {
            displayFavouritesIfAvailable();
            viewFavouritesMessages();
            String command = scanner.nextLine();
            if (command.equals("add")) {
                selectFoodToAdd();
            } else if (command.equals("back")) {
                return true;
            } else if (command.equals("home")) {
                return false;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a food from favourites to logs as an entry
    private void selectFoodToAdd() {
        if (!user.getSaved().getFoods().isEmpty()) {
            while (true) {
                System.out.println("Type the name of the food you want to add!");
                String command = scanner.nextLine();
                int hour;
                if (user.getSaved().getFood(command) == null) {
                    System.out.println("Food selected was not one of the options! Please try again!");
                } else {
                    Food selectedFood = user.getSaved().getFood(command);
                    hour = timeOfEntry();
                    user.getJournal().addEntry(new Entry(selectedFood, hour));
                    System.out.println("Entry successfully added!");
                    macroProgress();
                    break;
                }
            }
        }
    }

    // EFFECTS: returns a created food item
    private FoodItem makeFood() {
        System.out.println("What is the name of your food?");
        String foodName = scanner.nextLine();
        boolean valid = false;
        FoodItem food = null;
        while (!valid) {
            System.out.println("How many grams of protein does it contain?");
            double protein = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("How many grams of carbohydrates does it contain?");
            double carbs = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("How many grams of fat does it contain?");
            double fat = scanner.nextDouble();
            scanner.nextLine();
            if (protein >= 0 && carbs >= 0 && fat >= 0) {
                valid = true;
                food = new FoodItem(foodName, new Macros(protein, carbs, fat));
            } else {
                System.out.println("At least one of the values inputted was invalid! Please try again!");
            }
        }
        return food;
    }

    // EFFECTS: prints the options for the user's journal
    private void journalMessages() {
        System.out.println("\n'new' to start a new day's logs");
        System.out.println("'today' to view today's logs");
        System.out.println("'other' to view another day's logs");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for journal
    private void journal() {
        boolean redo = true;
        while (redo) {
            journalMessages();
            String command = scanner.nextLine();
            if (command.equals("new")) {
                newLog();
            } else if (command.equals("today")) {
                redo = dayLog(user.getJournal().getLogs().size());
            } else if (command.equals("other")) {
                redo = selectLog();
            } else if (command.equals("back") || command.equals("home")) {
                redo = false;
            } else {
                System.out.println("Input was not one of the options or no logs exist! Please try again!");
            }
        }
    }

    // EFFECTS: views the log specified by user, returns value of dayLog()
    private boolean selectLog() {
        while (true) {
            System.out.println("Which log, in day number, would you like to view?");
            int day = scanner.nextInt();
            scanner.nextLine();
            if (day > 0 && day <= user.getJournal().getLogs().size()) {
                return dayLog(day);
            } else {
                System.out.println("Log selected does not exist! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new log in the journal
    private void newLog() {
        System.out.println("What's your weight today?");
        double weight = scanner.nextDouble();
        scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            if (weight > 0) {
                valid = true;
                user.getJournal().nextDay(weight);
                user.updateWeight(weight);
                System.out.println("New log successfully created!");
            } else {
                System.out.println("Input was invalid! Please try again!");
                weight = scanner.nextDouble();
                scanner.nextLine();
            }
        }
    }

    // EFFECTS: prints the options for a day log
    private void dayLogMessages() {
        System.out.println("\n'food' to view food entries");
        System.out.println("'note' to view notes");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // REQUIRES: day > 0 && day <= size of this
    // MODIFIES: this
    // EFFECTS: processes user command for a day log, returns false if 'home' is selected in any branch, true if 'back
    private boolean dayLog(int day) {
        DayLog log = user.getJournal().getLog(day);
        while (true) {
            dayLogMessages();
            String command = scanner.nextLine();
            if (command.equals("food")) {
                foodLogMessages(log);
                if (!foodLog(log)) {
                    return false;
                }
            } else if (command.equals("note")) {
                if (!noteLog(log)) {
                    return false;
                }
            } else if (command.equals("back")) {
                return true;
            } else if (command.equals("home")) {
                return false;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // EFFECTS: prints the notes present in the log
    private void noteLogMessages(DayLog log) {
        List<String> notes = log.getNotes();
        if (!notes.isEmpty()) {
            System.out.println("Notes:");
            for (String note : notes) {
                System.out.println("\t - " + note);
            }
        } else {
            System.out.println("You have no notes for today");
        }
    }

    // EFFECTS: prints the options for editing notes in a log
    private void noteLogOptions() {
        System.out.println("\n'add' to add a note");
        System.out.println("'remove' to remove a note");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for editing notes in a log, returns false if 'home', true if 'back'
    private boolean noteLog(DayLog log) {
        while (true) {
            noteLogMessages(log);
            noteLogOptions();
            String command = scanner.nextLine();
            if (command.equals("add")) {
                System.out.println("What note would you like to add?");
                command = scanner.nextLine();
                log.addNote(command);
                System.out.println("Note was successfully added!");
            } else if (command.equals("remove")) {
                removeNote(log);
            } else if (command.equals("back")) {
                return true;
            } else if (command.equals("home")) {
                return false;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a note from the logs if any
    private void removeNote(DayLog log) {
        if (!log.getNotes().isEmpty()) {
            while (true) {
                noteLogMessages(log);
                System.out.println("Which note would you like to remove? Input the position from the top!");
                int position = scanner.nextInt();
                scanner.nextLine();
                if (position > 0 && position <= log.getNotes().size()) {
                    log.removeNote(position);
                    System.out.println("Note was successfully removed!");
                    break;
                } else {
                    System.out.println("Input was invalid! Please try again!");
                }
            }
        } else {
            System.out.println("You have no notes to remove!");
        }
    }

    // EFFECTS: prints the user's entries for the day
    private void foodLogMessages(DayLog log) {
        if (!log.getEntries().isEmpty()) {
            System.out.println("Entries: ");
            for (Entry entry : log.getEntries()) {
                System.out.println("\t - " + entry.getFood().getName() + " eaten at hour " + entry.getHour());
            }
        } else {
            System.out.println("Log is empty! Please add an entry first!");
        }
    }

    // EFFECTS: prints the options for editing the food log
    private void foodLogOptions() {
        System.out.println("\n'add' to add a new entry");
        System.out.println("'remove' to remove an entry");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for editing the food log, returns false if 'home' is selected in any branch
    //          true if 'back' is selected in any branch
    private boolean foodLog(DayLog log) {
        while (true) {
            foodLogOptions();
            String command = scanner.nextLine();
            if (command.equals("add")) {
                return addEntry(log);
            } else if (command.equals("remove")) {
                removeEntry(log);
            } else if (command.equals("back")) {
                return true;
            } else if (command.equals("home")) {
                return false;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // EFFECTS: prints the options for adding an entry
    private void addEntryMessages() {
        System.out.println("\n'new' to create a new entry");
        System.out.println("'favs' to add an entry from your favourites");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for adding an entry, returns false if 'home', true if 'back'
    private boolean addEntry(DayLog log) {
        while (true) {
            addEntryMessages();
            String command = scanner.nextLine();
            if (command.equals("new")) {
                Entry entry = makeEntry();
                log.addEntry(entry);
                macroProgress();
            } else if (command.equals("favs")) {
                displayFavouritesIfAvailable();
                selectFoodToAdd();
            } else if (command.equals("back")) {
                return true;
            } else if (command.equals("home")) {
                return false;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // EFFECTS: returns the time for the entry
    private int timeOfEntry() {
        while (true) {
            System.out.println("What time (hour from 0-23) are you eating this?");
            int hour = scanner.nextInt();
            scanner.nextLine();
            if (hour >= 0 && hour < 24) {
                return hour;
            } else {
                System.out.println("Time selected was invalid! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: returns a created entry
    private Entry makeEntry() {
        Food food = makeFood();
        int hour = timeOfEntry();
        System.out.println("Entry was successfully created!");
        return new Entry(food, hour);
    }

    // MODIFIES: this
    // EFFECTS: removes an entry if any
    private void removeEntry(DayLog log) {
        if (!log.getEntries().isEmpty()) {
            while (true) {
                foodLogMessages(log);
                System.out.println("Which entry would you like to remove? Input the position from the top!");
                int position = scanner.nextInt();
                scanner.nextLine();
                if (position > 0 && position <= log.getEntries().size()) {
                    log.removeEntry(position);
                    System.out.println("Entry was successfully removed!");
                    macroProgress();
                    break;
                } else {
                    System.out.println("Input was invalid! Please try again!");
                }
            }
        } else {
            System.out.println("Log is empty! There are no entries to remove!");
        }
    }

    // EFFECTS: prints the options for creating a new food
    private void newFoodMessages() {
        System.out.println("\n'item' to create a new food item (ex. white rice)");
        System.out.println("'meal' to create a new meal (ex. fried rice)");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // EFFECTS: processes user command for creating a new food
    private void newFood() {
        boolean redo = true;
        while (redo) {
            newFoodMessages();
            String command = scanner.nextLine();
            if (command.equals("item")) {
                redo = makeFoodItem();
            } else if (command.equals("meal")) {
                redo = makeMeal();
            } else if (command.equals("back") || command.equals("home")) {
                redo = false;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // MODIFIES: meal
    // EFFECTS: returns a meal after adding ingredients to it
    private Meal addIngredients(Meal meal) {
        String command;
        boolean keepGoing = true;
        int quantity = 1;
        while (keepGoing) {
            System.out.println("Making meal ingredient #" + quantity + "!");
            meal.addIngredient(makeFood());
            System.out.println("Ingredient successfully added!");
            while (true) {
                System.out.println("Would you like to add more? 'yes' or 'no'");
                command = scanner.nextLine();
                if (command.equals("yes")) {
                    quantity++;
                    break;
                } else if (command.equals("no")) {
                    keepGoing = false;
                    break;
                } else {
                    System.out.println("Input was invalid! Please try again!");
                }
            }
        }
        return meal;
    }

    // EFFECTS: creates a new meal and returns the value of addFood()
    private boolean makeMeal() {
        System.out.println("What will you name this meal?");
        String command = scanner.nextLine();
        Meal emptyMeal = new Meal(command);
        Meal meal = addIngredients(emptyMeal);
        return addFood(meal);
    }

    // EFFECTS: prints the options for where to add the food
    private void makeFoodMessages() {
        System.out.println("\n'entry' to add food to logs");
        System.out.println("'favs' to add food to favourites");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes command for where to add the food, returns false if 'home' and true otherwise
    private boolean addFood(Food food) {
        while (true) {
            makeFoodMessages();
            String command = scanner.nextLine();
            if (command.equals("entry")) {
                int hour = timeOfEntry();
                user.getJournal().addEntry(new Entry(food, hour));
                System.out.println("Successfully added entry to your logs!");
                macroProgress();
                return true;
            } else if (command.equals("favs")) {
                user.addFavourite(food);
                System.out.println("Successfully added food to favourites!");
                return true;
            } else if (command.equals("back")) {
                return true;
            } else if (command.equals("home")) {
                return false;
            } else {
                System.out.println("Input was not one of the options! Please try again!");
            }
        }
    }

    // EFFECTS: creates a new food item and returns the value of addFood()
    private boolean makeFoodItem() {
        FoodItem foodItem = makeFood();
        return addFood(foodItem);
    }
}
