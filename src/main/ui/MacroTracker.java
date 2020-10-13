package ui;

import model.*;

import java.util.List;
import java.util.Scanner;

/**
 * MacroTracker is the ui class that allows for user interaction. Used the TellerApp
 */
public class MacroTracker {

    private User user;
    private Scanner scanner;

    // EFFECTS: initializes scanner and starts the application
    public MacroTracker() {
        scanner = new Scanner(System.in);
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: runs the application
    private void runTracker() {
        init();
        processCommand();
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
    }

    // EFFECTS: displays the various options available to the user
    private void displayOptions() {
        System.out.println("'info' to update your information");
        System.out.println("'prog' to view your progress");
        System.out.println("'favs' to view your favourites");
        System.out.println("'logs' to access your journal logs");
        System.out.println("'food' to create a new food item or meal");
        System.out.println("'quit' to quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand() {
        displayOptions();
        String command = scanner.nextLine();
        boolean valid = false;

        while (!valid) {
            valid = true;
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
            } else if (command.equals("quit")) {
                System.out.println("Shutting down ...");
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
    }

    // EFFECTS: prints the user's information and provides options for next command
    private void informationMessages() {
        System.out.println("Name: " + user.getName());
        System.out.println("Weight: " + user.getWeight());
        System.out.println("Weight Goal: " + user.getJournal().getGoal());

        System.out.println("'update' to change personal info");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for information
    private void information() {
        informationMessages();
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("update")) {
                updateInfo();
            } else if (command.equals("back") || command.equals("home")) {
                processCommand();
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        processCommand();
    }

    // EFFECTS: prints the options for updating user information
    private void updateInfoMessages() {
        System.out.println("'name' to update name");
        System.out.println("'weight' to update weight");
        System.out.println("'goal' to update weight goal");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for updating user information
    private void updateInfo() {
        updateInfoMessages();
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("name")) {
                changeName();
            } else if (command.equals("weight")) {
                changeWeight();
            } else if (command.equals("goal")) {
                changeGoal();
            } else if (command.equals("back")) {
                information();
            } else if (command.equals("home")) {
                processCommand();
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        information();
    }

    // MODIFIES: this
    // EFFECTS: changes the goal of the user based on input
    private void changeGoal() {
        System.out.println("Current goal: " + user.getJournal().getGoal());
        System.out.println("What will your new goal be?");
        double goal = scanner.nextDouble();
        scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            if (goal > 0) {
                valid = true;
                user.getJournal().setGoal(goal);
            } else {
                System.out.println("Input was invalid! Please try again!");
                goal = scanner.nextDouble();
                scanner.nextLine();
            }
        }
        System.out.println("Change successfully made!");
        updateInfo();
    }

    // MODIFIES: this
    // EFFECTS: changes the weight of the user based on input
    private void changeWeight() {
        System.out.println("Current weight: " + user.getWeight());
        System.out.println("What's your weight now?");
        double weight = scanner.nextDouble();
        scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            if (weight > 0) {
                valid = true;
                user.setWeight(weight);
            } else {
                System.out.println("Input was invalid! Please try again!");
                weight = scanner.nextDouble();
                scanner.nextLine();
            }
        }
        System.out.println("Change successfully made!");
        updateInfo();
    }

    // MODIFIES: this
    // EFFECTS: changes the name of the user based on input
    private void changeName() {
        System.out.println("Name: " + user.getName());
        System.out.println("What's your new name?");
        String name = scanner.nextLine();
        user.setName(name);
        System.out.println("Change successfully made!");
        updateInfo();
    }

    // EFFECTS: prints the user's progress if there is any
    private void progress() {
        if (!user.getJournal().getLogs().isEmpty()) {
            double weightProgress = user.getJournal().viewProgress();
            double caloriesToGo = user.getMacrosNeeded().getCalories();
            double proteinToGo = user.getMacrosNeeded().getProtein();
            double carbsToGo = user.getMacrosNeeded().getCarbs();
            double fatToGo = user.getMacrosNeeded().getFat();
            System.out.println("You are " + weightProgress + "% of the way towards your goal!");
            System.out.println("You need " + caloriesToGo + " more calories for today!");
            System.out.println("You need " + proteinToGo + " more grams of protein for today!");
            System.out.println("You need " + carbsToGo + " more grams of carbohydrates for today!");
            System.out.println("You need " + fatToGo + " more grams of fat for today!");
        } else {
            System.out.println("No records available! Try making a new entry first!");
        }
        processCommand();
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
        favouritesMessages();
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("view")) {
                if (!user.getSaved().getFoods().isEmpty()) {
                    viewFavourites();
                } else {
                    System.out.println("No foods have been saved yet!");
                }
            } else if (command.equals("add")) {
                user.addFavourite(makeFood());
                System.out.println("Food was successfully added to favourites!");
            } else if (command.equals("back") || command.equals("home")) {
                processCommand();
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        processCommand();
    }

    // EFFECTS: prints the user's favourite foods if any
    private void displayFavourites() {
        if (!user.getSaved().getFoods().isEmpty()) {
            for (Food food : user.getSaved().getFoods()) {
                System.out.println(
                        food.getName() + ":\n" + "\t" + food.getCalories() + " calories, "
                                + food.getProtein() + " grams of protein, " + food.getCarbs() + " grams of carbs, and "
                                + food.getFat() + " grams of fat"
                );
            }
        } else {
            System.out.println("You have no foods saved yet!");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for favourites
    private void viewFavourites() {
        displayFavourites();
        System.out.println("'add' to add a favourite food to current day's logs");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("add")) {
                System.out.println("Type the name of the food you want to add!");
                selectFoodToAdd();
            } else if (command.equals("back")) {
                favourites();
            } else if (command.equals("home")) {
                processCommand();
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        favourites();
    }

    // MODIFIES: this
    // EFFECTS: adds a food from favourites to logs as an entry
    private void selectFoodToAdd() {
        String command = scanner.nextLine();
        int hour;
        boolean valid = false;
        while (!valid) {
            if (user.getSaved().getFood(command) == null) {
                System.out.println("Food selected was not one of the options! Please try again!");
                command = scanner.nextLine();
            } else {
                valid = true;
                Food selectedFood = user.getSaved().getFood(command);
                hour = timeOfEntry();
                user.getJournal().addEntry(new Entry(selectedFood, hour));
            }
        }
        System.out.println("Entry successfully added!");
        viewFavourites();
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
        System.out.println("'new' to start a new day's logs");
        System.out.println("'today' to view today's logs");
        System.out.println("'other' to view another day's logs");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes user command for journal
    private void journal() {
        journalMessages();
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("new")) {
                newLog();
            } else if (command.equals("today") && !user.getJournal().getLogs().isEmpty()) {
                dayLog(user.getJournal().getLogs().size() - 1);
            } else if (command.equals("other") && !user.getJournal().getLogs().isEmpty()) {
                selectLog();
            } else if (command.equals("back") || command.equals("home")) {
                processCommand();
            } else {
                valid = false;
                System.out.println("Input was not one of the options or no logs exist! Please try again!");
                command = scanner.nextLine();
            }
        }
        processCommand();
    }

    // EFFECTS: views the log specified by user
    private void selectLog() {
        System.out.println("Which log, in day number, would you like to view?");
        int day = scanner.nextInt();
        scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            if (day > 0 && day <= user.getJournal().getLogs().size()) {
                valid = true;
                dayLog(day);
            } else {
                System.out.println("Log selected does not exist! Please try again!");
                day = scanner.nextInt();
                scanner.nextLine();
            }
        }
        journal();
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
            } else {
                System.out.println("Input was invalid! Please try again!");
                weight = scanner.nextDouble();
                scanner.nextLine();
            }
        }
        System.out.println("New log successfully created!");
        journal();
    }

    // EFFECTS: prints the options for a day log
    private void dayLogMessages() {
        System.out.println("'food' to view food entries");
        System.out.println("'note' to view notes");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // REQUIRES: day > 0 && day <= size of this
    // MODIFIES: this
    // EFFECTS: processes user command for a day log
    private void dayLog(int day) {
        DayLog log = user.getJournal().getLog(day);
        dayLogMessages();
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("food")) {
                foodLog(log, day);
            } else if (command.equals("note")) {
                noteLog(log, day);
            } else if (command.equals("back")) {
                journal();
            } else if (command.equals("home")) {
                processCommand();
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        journal();
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
        System.out.println("'add' to add a note");
        System.out.println("'remove' to remove a note");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // REQUIRES: day > 0 && day <= size of this
    // MODIFIES: this
    // EFFECTS: processes user command for notes in a log
    private void noteLog(DayLog log, int day) {
        noteLogMessages(log);
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("add")) {
                System.out.println("What note would you like to add?");
                command = scanner.nextLine();
                log.addNote(command);
                System.out.println("Note was successfully added!");
            } else if (command.equals("remove")) {
                removeNote(log, day);
            } else if (command.equals("back")) {
                dayLog(day);
            } else if (command.equals("home")) {
                processCommand();
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        dayLog(day);
    }

    // REQUIRES: day > 0 && day <= size of this
    // MODIFIES: this
    // EFFECTS: removes a note from the logs if any
    private void removeNote(DayLog log, int day) {
        if (!log.getNotes().isEmpty()) {
            System.out.println("Which note would you like to remove? Input the position from the top!");
            int position = scanner.nextInt();
            scanner.nextLine();
            boolean valid = false;
            while (!valid) {
                if (position > 0 && position <= log.getNotes().size()) {
                    valid = true;
                    log.removeNote(position);
                } else {
                    System.out.println("Input was invalid! Please try again!");
                    position = scanner.nextInt();
                    scanner.nextLine();
                }
            }
            System.out.println("Note was successfully removed!");
        } else {
            System.out.println("You have no notes to remove!");
        }
        noteLog(log, day);
    }

    // EFFECTS: prints the user's entries for the day and the options for modifying the logs
    private void foodLogMessages(DayLog log) {
        if (!log.getEntries().isEmpty()) {
            System.out.println("Entries: ");
            for (Entry entry : log.getEntries()) {
                System.out.println("\t - " + entry.getFood() + " eaten at hour " + entry.getHour());
            }
        } else {
            System.out.println("Log is empty! Please add an entry first!");
        }
        System.out.println("'add' to add a new entry");
        System.out.println("'remove' to remove an entry");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // REQUIRES: day > 0 && day <= size of this
    // MODIFIES: this
    // EFFECTS: processes user command for the food log chosen
    private void foodLog(DayLog log, int day) {
        foodLogMessages(log);
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("add")) {
                addEntry(log, day);
            } else if (command.equals("remove")) {
                System.out.println("Which entry would you like to remove? Input the position from the top!");
                removeEntry(log, day);
            } else if (command.equals("back")) {
                dayLog(day);
            } else if (command.equals("home")) {
                processCommand();
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        dayLog(day);
    }

    // EFFECTS: prints the options for adding an entry
    private void addEntryMessages() {
        System.out.println("'new' to create a new entry");
        System.out.println("'favs' to add an entry from your favourites");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // REQUIRES: day > 0 && day <= size of this
    // MODIFIES: this
    // EFFECTS: processes user command for adding an entry
    private void addEntry(DayLog log, int day) {
        addEntryMessages();
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("new")) {
                Entry entry = makeEntry();
                log.addEntry(entry);
            } else if (command.equals("favs")) {
                displayFavourites();
                System.out.println("Type the name of the food you want to add!");
                selectFoodToAdd();
            } else if (command.equals("back")) {
                foodLog(log, day);
            } else if (command.equals("home")) {
                processCommand();
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        foodLog(log, day);
    }

    // EFFECTS: returns the time for the entry
    private int timeOfEntry() {
        System.out.println("What time (hour from 0-23) are you eating this?");
        int time = 0;
        int hour = scanner.nextInt();
        scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            if (hour >= 0 && hour < 24) {
                valid = true;
                time = hour;
            } else {
                System.out.println("Time selected was invalid! Please try again!");
                hour = scanner.nextInt();
                scanner.nextLine();
            }
        }
        return time;
    }

    // MODIFIES: this
    // EFFECTS: adds a new entry to logs
    private Entry makeEntry() {
        Food food = makeFood();
        int hour = timeOfEntry();
        System.out.println("Entry was successfully created!");
        return new Entry(food, hour);
    }

    // REQUIRES: day > 0 && day <= size of this
    // MODIFIES: this
    // EFFECTS: removes an entry if any
    private void removeEntry(DayLog log, int day) {
        if (!log.getEntries().isEmpty()) {
            int position = scanner.nextInt();
            scanner.nextLine();
            boolean valid = false;
            while (!valid) {
                if (position > 0 && position <= log.getEntries().size()) {
                    valid = true;
                    log.removeEntry(position);
                } else {
                    System.out.println("Input was invalid! Please try again!");
                    position = scanner.nextInt();
                    scanner.nextLine();
                }
            }
        } else {
            System.out.println("Log is empty! There are no entries to remove!");
        }
        foodLog(log, day);
    }

    // EFFECTS: prints the options for creating a new food
    private void newFoodMessages() {
        System.out.println("'item' to create a new food item (ex. white rice)");
        System.out.println("'meal' to create a new meal (ex. fried rice)");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // EFFECTS: processes user command for creating a new food
    private void newFood() {
        newFoodMessages();
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("item")) {
                makeFoodItem();
            } else if (command.equals("meal")) {
                makeMeal();
            } else if (command.equals("back") || command.equals("home")) {
                processCommand();
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        processCommand();
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
            System.out.println("Would you like to add more? 'yes' or 'no'");
            command = scanner.nextLine();
            boolean valid = false;
            while (!valid) {
                if (command.equals("yes")) {
                    valid = true;
                    quantity++;
                } else if (command.equals("no")) {
                    valid = true;
                    keepGoing = false;
                } else {
                    System.out.println("Input was invalid! Please try again!");
                }
            }
        }
        return meal;
    }

    // EFFECTS: creates a new meal
    private void makeMeal() {
        System.out.println("What will you name this meal?");
        String command = scanner.nextLine();
        Meal emptyMeal = new Meal(command);
        Meal meal = addIngredients(emptyMeal);
        makeFoodMessages();
        addFood(meal);
    }

    // EFFECTS: prints the options for where to add the food
    private void makeFoodMessages() {
        System.out.println("'entry' to add food to logs");
        System.out.println("'favs' to add food to favourites");
        System.out.println("'back' to go back");
        System.out.println("'home' to go back to home page");
    }

    // MODIFIES: this
    // EFFECTS: processes command for where to add the food
    private void addFood(Food food) {
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("entry")) {
                int hour = timeOfEntry();
                user.getJournal().addEntry(new Entry(food, hour));
                System.out.println("Successfully added entry to your logs!");
            } else if (command.equals("favs")) {
                user.addFavourite(food);
                System.out.println("Successfully added food to favourites!");
            } else if (command.equals("back")) {
                newFood();
            } else if (command.equals("home")) {
                processCommand();
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        newFood();
    }

    // EFFECTS: creates a new food item
    private void makeFoodItem() {
        FoodItem foodItem = makeFood();
        makeFoodMessages();
        addFood(foodItem);
    }
}
