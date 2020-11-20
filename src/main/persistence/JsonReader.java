package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * JsonReader reads from json data stored in a file. Class takes aspects of JsonSerializationDemo starter
 * from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonReader {

    private String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a user from json file and returns it
    //          throws IOException if an error occurs reading data from file
    // Copied from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it.
    // Copied from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses user from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double weight = jsonObject.getDouble("weight");
        User user = new User(name, weight, 0);
        setMacrosGoals(user, jsonObject.getJSONObject("macrosNeeded"));
        addJournal(user, jsonObject.getJSONObject("journal"));
        addSaved(user, jsonObject.getJSONObject("saved"));
        return user;
    }

    // MODIFIES: user
    // parses saved foods from JSON object and adds it to user
    private void addSaved(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("favourites");
        for (Object json : jsonArray) {
            JSONObject nextFood = (JSONObject) json;
            boolean isMeal = nextFood.getBoolean("isMeal");
            if (isMeal) {
                Meal meal = getMeal(nextFood);
                user.addFavourite(meal);
            } else {
                Food foodItem = getFoodItem(nextFood);
                user.addFavourite(foodItem);
            }
        }
    }

    // EFFECTS: returns the meal after parsing from JSON object
    private Meal getMeal(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Meal meal = new Meal(name);
        addIngredients(meal, jsonObject);
        return meal;
    }

    // MODIFIES: meal
    // EFFECTS: parses ingredients from JSON array and adds to meal
    private void addIngredients(Meal meal, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("ingredients");
        for (Object json : jsonArray) {
            JSONObject nextIngredient = (JSONObject) json;
            FoodItem foodItem = getFoodItem(nextIngredient);
            meal.addIngredient(foodItem);
        }
    }

    // EFFECTS: returns the food item after parsing from JSON object
    private FoodItem getFoodItem(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        FoodItem foodItem = new FoodItem(name, new Macros());
        setMacros(foodItem, jsonObject.getJSONObject("macros"));
        return foodItem;
    }

    // MODIFIES: user
    // EFFECTS: parses journal from JSON object and adds it to user
    private void addJournal(User user, JSONObject jsonObject) {
        Journal journal = user.getJournal();
        double goal = jsonObject.getDouble("goal");
        journal.setGoal(goal);
        addLogs(journal, jsonObject);
    }

    // MODIFIES: journal
    // EFFECTS: parses logs from JSON object and adds them to journal
    private void addLogs(Journal journal, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("logs");
        for (Object json : jsonArray) {
            JSONObject nextLog = (JSONObject) json;
            journal.nextDay(0);
            addLog(journal, nextLog);
        }
    }

    // MODIFIES: journal
    // EFFECTS: parses a log from JSON object and adds it to journal
    private void addLog(Journal journal, JSONObject jsonObject) {
        DayLog log = journal.getLastLog();
        addEntries(log, jsonObject);
        addNotes(log, jsonObject);
        double weight = jsonObject.getDouble("weight");
        journal.updateWeight(weight);
    }

    // MODIFIES: log
    // EFFECTS: parses entries from JSON object and adds them to log
    private void addEntries(DayLog log, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("entries");
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addEntry(log, nextEntry);
        }
    }

    // MODIFIES: log
    // EFFECTS: parses an entry from JSON object and adds it to log
    private void addEntry(DayLog log, JSONObject jsonObject) {
        int hour = jsonObject.getInt("hour");
        JSONObject food = (JSONObject) jsonObject.get("food");
        boolean isMeal = food.getBoolean("isMeal");
        Food foodEntry;
        if (isMeal) {
            foodEntry = getMeal(food);
        } else {
            foodEntry = getFoodItem(food);
        }
        log.addEntry(new Entry(foodEntry, hour));
    }

    // MODIFIES: log
    // EFFECTS: parses notes from JSON object and adds them to log
    private void addNotes(DayLog log, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("notes");
        for (Object json : jsonArray) {
            JSONObject nextNote = (JSONObject) json;
            addNote(log, nextNote);
        }
    }

    // MODIFIES: log
    // EFFECTS: parses a note from JSON object and adds it to log
    private void addNote(DayLog log, JSONObject jsonObject) {
        String note = jsonObject.getString("note");
        log.addNote(note);
    }

    // MODIFIES: food
    // EFFECTS: parses macros from JSON object and sets food's macros to it
    private void setMacros(Food food, JSONObject jsonObject) {
        double protein = jsonObject.getDouble("protein");
        double carbs = jsonObject.getDouble("carbs");
        double fat = jsonObject.getDouble("fat");
        food.setMacros(new Macros(protein, carbs, fat));
    }

    // MODIFIES: user
    // EFFECTS: parses macros from JSON object and sets user's macro goals to it
    private void setMacrosGoals(User user, JSONObject jsonObject) {
        double calories = jsonObject.getDouble("calories");
        double protein = jsonObject.getDouble("protein");
        double carbs = jsonObject.getDouble("carbs");
        double fat = jsonObject.getDouble("fat");
        user.setMacrosNeeded(new Macros(protein, carbs, fat));
    }
}
