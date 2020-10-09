package model;

import java.util.Date;
import java.util.List;

public class User {

    private String name;
    private Date birthdate;
    private String gender; // only "male" or "female"
    private double height; // in centimeters
    private double weight; // in kilograms
    private int physActivity; // 0 (none), 1 (light), 2 (moderate), 3 (intense)
    private int goal; // 0 (lose weight), 1 (maintain weight), 2 (gain muscle)
    private Macros macrosNeeded;
    private List<FoodJournal> foodJournal;

    // EFFECTS: constructs a new User
    public User() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: set the name of the user to input
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: set the birthdate of the user to input
    public void setBirthdate(Date date) {
        birthdate = date;
    }

    // REQUIRES: gender must be "male" or "female"
    // MODIFIES: this
    // EFFECTS: set the gender of the user to input
    public void setGender(String gender) {
        this.gender = gender;
    }

    // REQUIRES: height > 0
    // MODIFIES: this
    // EFFECTS: set the height of the user to input
    public void setHeight(double height) {
        this.height = height;
    }

    // REQUIRES: weight > 0
    // MODIFIES: this
    // EFFECTS: set the weight of the user to input
    public void setWeight(double weight) {
        this.weight = weight;
    }

    // REQUIRES: physActivity is within [0, 3]
    // MODIFIES: this
    // EFFECTS: set the physActivity of the user to input
    public void setPhysActivity(int physActivity) {
        this.physActivity = physActivity;
    }

    // REQUIRES: goal is within [0, 2]
    // MODIFIES: this
    // EFFECTS: set the goal of the user to input
    public void setGoal(int goal) {
        this.goal = goal;
    }
}
