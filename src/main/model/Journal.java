package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Journal is a user's collection of DayLogs and can show the progress of the user so far
 */
public class Journal {

    private double goal;
    private List<DayLog> logs;
    private List<Double> weightTracker;

    // EFFECTS: constructs an empty journal
    public Journal(double goal) {
        this.goal = goal;
        logs = new ArrayList<>();
        weightTracker = new ArrayList<>();
    }

    public double getGoal() {
        return goal;
    }

    // MODIFIES: this
    // EFFECTS: adds another log into logs one day forward
    public void nextDay(double weight) {
        logs.add(new DayLog(logs.size() + 1, weight));
        weightTracker.add(weight);
    }

    // REQUIRES: day <= size of this
    // EFFECTS: returns log whose day matches input
    public DayLog getLog(int day) {
        return logs.get(day - 1);
    }

    // REQUIRES: this must have content
    // EFFECTS: returns the most recent log
    public DayLog getLog() {
        return logs.get(logs.size() - 1);
    }

    public List<DayLog> getLogs() {
        return logs;
    }

    public List<Double> getWeightTracker() {
        return weightTracker;
    }

    // REQUIRES: this must have content
    // MODIFIES: this
    // EFFECTS: sets the latest weight to the input
    public void updateWeight(double weight) {
        logs.get(logs.size() - 1).setWeight(weight);
        weightTracker.remove(weightTracker.size() - 1);
        weightTracker.add(weight);
    }

    // REQUIRES: this must have content
    // EFFECTS: returns the weight change progress so far as a percentage
    public double viewProgress() {
        double changeSoFar = getTotalChange();
        double changeGoal = goal - weightTracker.get(0);
        return (double) (changeSoFar / changeGoal) * 100;
    }

    // REQUIRES: this must have content
    // EFFECTS: returns the total weight change so far
    public double getTotalChange() {
        double starting = weightTracker.get(0);
        double current = weightTracker.get(weightTracker.size() - 1);
        return current - starting;
    }

    // EFFECTS: returns the remaining change needed
    public double remainingGoal() {
        double current = weightTracker.get(weightTracker.size() - 1);
        return goal - current;
    }
}
