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

    public MacroTracker() {
        scanner = new Scanner(System.in);
        init();
    }

    public User getUser() {
        return user;
    }

    // MODIFIES: this
    // EFFECTS: runs the application
    private void runTracker() {

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
        System.out.println("'info' to update your information\n");
        System.out.println("'prog' to view your progress\n");
        System.out.println("'favs' to view your favourites\n");
        System.out.println("'logs' to access your journal logs\n");
        System.out.println("'food' to create a new food item or meal");
        System.out.println("'quit' to quit");
    }

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
                // go back
            } else if (command.equals("food")) {
                // go back
            } else if (command.equals("quit")) {
                // quit
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
    }

    private void informationMessages() {
        System.out.println("Name: " + user.getName());
        System.out.println("Weight: " + user.getWeight());
        System.out.println("Weight Goal: " + user.getJournal().getGoal());

        System.out.println("'update' to change personal info");
        System.out.println("'back' to go back");
        System.out.println("'quit' to quit");
    }

    private void information() {
        informationMessages();
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("update")) {
                updateInfo();
            } else if (command.equals("back")) {
                processCommand();
            } else if (command.equals("quit")) {
                System.out.println("Shutting down ...");

            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        processCommand();
    }

    private void updateInfoMessages() {
        System.out.println("'name' to update name");
        System.out.println("'weight' to update weight");
        System.out.println("'goal' to update weight goal");
        System.out.println("'back' to go back");
        System.out.println("'quit' to quit");
    }

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
            } else if (command.equals("quit")) {
                // quit
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        information();
    }

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
        updateInfo();
    }

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
        updateInfo();
    }

    private void changeName() {
        System.out.println("Name: " + user.getWeight());
        System.out.println("What's your new name?");
        String name = scanner.nextLine();
        user.setName(name);
        updateInfo();
    }

    private void progress() {
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
        processCommand();
    }

    private void favouritesMessages() {
        System.out.println("'view' to see a list of your favourite foods");
        System.out.println("'add' to add a new food to your favourite foods");
        System.out.println("'back' to go back");
        System.out.println("'quit' to quit");
    }

    private void favourites() {
        favouritesMessages();
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("view")) {
                viewFavourites();
            } else if (command.equals("add")) {
                addToFavourites();
            } else if (command.equals("back")) {
                processCommand();
            } else if (command.equals("quit")) {
                // quit
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        processCommand();
    }

    private void displayFavourites() {
        for (Food food : user.getSaved().getFoods()) {
            System.out.println(
                    food.getName() + ":\n" + "\t" + food.getCalories() + " calories, "
                            + food.getProtein() + " grams of protein, " + food.getCarbs() + " grams of carbs, and "
                            + food.getFat() + " grams of fat"
            );
        }
    }

    private void viewFavourites() {
        displayFavourites();
        System.out.println("'add' to add a favourite food to current day's logs");
        System.out.println("'back' to go back");
        System.out.println("'quit' to quit");
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("add")) {
                System.out.println("Type the name of the food you want to add!");
                selectFoodToAdd();
            } else if (command.equals("back")) {
                favourites();
            } else if (command.equals("quit")) {
                // quit
            } else {
                valid = false;
                System.out.println("Input was not one of the options! Please try again!");
                command = scanner.nextLine();
            }
        }
        favourites();
    }

    private void selectFoodToAdd() {
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            if (user.getSaved().getFood(command) == null) {
                System.out.println("Food selected was not one of the options! Please try again!");
                command = scanner.nextLine();
            } else {
                Food selectedFood = user.getSaved().getFood(command);
                System.out.println("What time (hour from 0-23) are you eating this?");
                int hour = scanner.nextInt();
                scanner.nextLine();
                while (!valid) {
                    if (hour >= 0 && hour < 24) {
                        valid = true;
                        user.getJournal().addEntry(new Entry(selectedFood, hour));
                    } else {
                        System.out.println("Time selected was invalid! Please try again!");
                        command = scanner.nextLine();
                    }
                }
            }
        }
        viewFavourites();
    }

    private void addToFavourites() {
        System.out.println("What is the name of your food?");
        String foodName = scanner.nextLine();
        boolean valid = false;
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
                Food food = new FoodItem(foodName, new Macros(protein, carbs, fat));
                user.addFavourite(food);
            } else {
                System.out.println("One of the values inputted was invalid! Please try again!");
            }
        }
        favourites();
    }

    private void journalMessages() {
        System.out.println("'new' to start a new day's logs");
        System.out.println("'today' to view today's logs");
        System.out.println("'other' to view another day's logs");
        System.out.println("'back' to go back");
        System.out.println("'quit' to quit");
    }

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
            } else if (command.equals("back")) {
                processCommand();
            } else if (command.equals("quit")) {
                // quit
            } else {
                valid = false;
                System.out.println("Input was not one of the options or no logs exist! Please try again!");
                command = scanner.nextLine();
            }
        }
        processCommand();
    }

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
        journal();
    }

    private void dayLogMessages() {
        System.out.println("'food' to view food entries");
        System.out.println("'note' to view notes");
        System.out.println("'back' to go back");
        System.out.println("'quit' to quit");
    }

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
            } else if (command.equals("quit")) {
                // quit
            } else {
                valid = false;
                System.out.println("Input was not one of the options or no logs exist! Please try again!");
                command = scanner.nextLine();
            }
        }
        journal();
    }

    private void noteLogMessages(DayLog log) {
        List<String> notes = log.getNotes();
        System.out.println("Notes:");
        for (String note : notes) {
            System.out.println("\t - " + note);
        }
        System.out.println("'add' to add a note");
        System.out.println("'remove' to remove a note");
        System.out.println("'back' to go back");
        System.out.println("'quit' to quit");
    }

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
            } else if (command.equals("remove")) {
                System.out.println("Which note would you like to remove? Input the position from the top!");
                removeNote(log, day);
            } else if (command.equals("back")) {
                dayLog(day);
            } else if (command.equals("quit")) {
                // quit
            } else {
                valid = false;
                System.out.println("Input was not one of the options or no logs exist! Please try again!");
                command = scanner.nextLine();
            }
        }
        dayLog(day);
    }

    private void removeNote(DayLog log, int day) {
        int command = scanner.nextInt();
        scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            if (command > 0 && command <= log.getNotes().size()) {
                valid = true;
                log.removeNote(command);
            } else {
                System.out.println("Input was invalid! Please try again!");
                command = scanner.nextInt();
                scanner.nextLine();
            }
        }
        noteLog(log, day);
    }

    private void foodLogMessages(DayLog log) {
        System.out.println("Entries: ");
        for (Entry entry : log.getEntries()) {
            System.out.println("\t - " + entry.getFood() + " eaten at hour " + entry.getHour());
        }
        System.out.println("'add' to add a new entry");
        System.out.println("'remove' to remove an entry");
        System.out.println("'back' to go back");
        System.out.println("'quit' to quit");
    }

    private void foodLog(DayLog log, int day) {
        foodLogMessages(log);
        String command = scanner.nextLine();
        boolean valid = false;
        while (!valid) {
            valid = true;
            if (command.equals("add")) {
                addEntry();
            } else if (command.equals("remove")) {
                System.out.println("Which note would you like to remove? Input the position from the top!");
                removeEntry(log, day);
            } else if (command.equals("back")) {
                dayLog(day);
            } else if (command.equals("quit")) {
                // quit
            } else {
                valid = false;
                System.out.println("Input was not one of the options or no logs exist! Please try again!");
                command = scanner.nextLine();
            }
        }
        dayLog(day);
    }

    private void addEntry() {
    }

    private void removeEntry(DayLog log, int day) {

    }
}
