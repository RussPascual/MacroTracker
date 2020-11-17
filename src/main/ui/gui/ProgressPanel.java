package ui.gui;

import model.Macros;
import model.User;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;

public class ProgressPanel {

    private User user;
    private JPanel panel;
    private GUI gui;

    // EFFECTS: constructs a progress panel
    public ProgressPanel(User user, GUI gui) {
        this.user = user;
        this.gui = gui;
        initializePanel();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public JPanel getPanel() {
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: initialize panel
    private void initializePanel() {
        panel = new JPanel();
        panel.setLayout(null);
        initializeHeaders();
        initializeMacroProgress();
        initializeWeightProgress();
    }

    // MODIFIES: this
    // EFFECTS: initialize header labels
    private void initializeHeaders() {
        Font font = new Font(Font.SERIF, Font.BOLD, 24);
        Font fancyFont = new Font(Font.SANS_SERIF, Font.ITALIC, 24);
        JLabel macros = new JLabel("Macros Progress");
        macros.setBounds(80, 50, 200, 50);
        macros.setFont(font);
        JLabel weight = new JLabel("Weight Progress");
        weight.setBounds(480, 50, 200, 50);
        weight.setFont(font);
        JLabel quote = new JLabel("A little progress every day adds up to big results.");
        quote.setBounds(120, 10, 600, 50);
        quote.setFont(fancyFont);
        panel.add(macros); // 0
        panel.add(weight); // 1
        panel.add(quote); // 2
    }

    // MODIFIES: this
    // EFFECTS: initialize macro progress components
    private void initializeMacroProgress() {
        initializeMacroLabels();
        initializeCalorieProgress();
        initializeProteinProgress();
        initializeCarbohydratesProgress();
        initializeFatProgress();
    }

    // MODIFIES: this
    // EFFECTS: initialize macro labels
    private void initializeMacroLabels() {
        JLabel calories = new JLabel("Calories");
        calories.setBounds(30, 100, 100, 25);
        JLabel protein = new JLabel("Protein");
        protein.setBounds(30, 200, 100, 25);
        JLabel carbs = new JLabel("Carbohydrates");
        carbs.setBounds(30, 300, 100, 25);
        JLabel fat = new JLabel("Fat");
        fat.setBounds(30, 400, 100, 25);
        panel.add(calories); // 3
        panel.add(protein); // 4
        panel.add(carbs); // 5
        panel.add(fat); // 6
    }

    // MODIFIES: this
    // EFFECTS: initialize calorie progress bar
    private void initializeCalorieProgress() {
        JProgressBar calories = new JProgressBar();
        calories.setBounds(30, 130, 300, 50);
        setVisuals(calories);
        panel.add(calories); // 7
    }

    // MODIFIES: this
    // EFFECTS: initialize protein progress bar
    private void initializeProteinProgress() {
        JProgressBar protein = new JProgressBar();
        protein.setBounds(30, 230, 300, 50);
        setVisuals(protein);
        panel.add(protein); // 8
    }

    // MODIFIES: this
    // EFFECTS: initialize carbohydrates progress bar
    private void initializeCarbohydratesProgress() {
        JProgressBar carbs = new JProgressBar();
        carbs.setBounds(30, 330, 300, 50);
        setVisuals(carbs);
        panel.add(carbs); // 9
    }

    // MODIFIES: this
    // EFFECTS: initialize fat progress bar
    private void initializeFatProgress() {
        JProgressBar fat = new JProgressBar();
        fat.setBounds(30, 430, 300, 50);
        setVisuals(fat);
        panel.add(fat); // 10
    }

    // MODIFIES: this
    // EFFECTS: set visuals for progress bar input
    private void setVisuals(JProgressBar bar) {
        bar.setStringPainted(true);
        bar.setBackground(new Color(180, 100, 100));
        bar.setForeground(new Color(100, 180, 100));
    }

    // MODIFIES: this
    // EFFECTS: initialize weight progress labels
    private void initializeWeightProgress() {
        JLabel days = new JLabel("# of Days: ");
        days.setBounds(520, 90, 120, 25);
        JLabel start = new JLabel("Start: ");
        start.setBounds(470, 470, 100, 25);
        JLabel goal = new JLabel("Goal: ");
        goal.setBounds(470, 120, 100, 25);
        JProgressBar weight = new JProgressBar(SwingConstants.VERTICAL);
        weight.setBounds(550, 130, 100, 350);
        setVisuals(weight);
        panel.add(days); // 11
        panel.add(start); // 12
        panel.add(goal); // 13
        panel.add(weight); // 14
    }

    // MODIFIES: this
    // EFFECTS: update progress
    public void update() {
        updateMacros();
        updateWeight();
    }

    // MODIFIES: this
    // EFFECTS: update macro progress
    private void updateMacros() {
        Macros macros = user.getJournal().getLastLog().totalMacros();
        updateCalories(macros.getCalories());
        updateProtein(macros.getProtein());
        updateCarbs(macros.getCarbs());
        updateFat(macros.getFat());
    }

    // MODIFIES: this
    // EFFECTS: update calorie progress
    private void updateCalories(double calories) {
        JProgressBar progress = (JProgressBar) panel.getComponent(7);
        progress.setMaximum((int) user.getMacrosNeeded().getCalories());
        progress.setValue((int) calories);
    }

    // MODIFIES: this
    // EFFECTS: update protein progress
    private void updateProtein(double protein) {
        JProgressBar progress = (JProgressBar) panel.getComponent(8);
        progress.setMaximum((int) user.getMacrosNeeded().getProtein());
        progress.setValue((int) protein);
    }

    // MODIFIES: this
    // EFFECTS: update carbohydrate progress
    private void updateCarbs(double carbs) {
        JProgressBar progress = (JProgressBar) panel.getComponent(9);
        progress.setMaximum((int) user.getMacrosNeeded().getCarbs());
        progress.setValue((int) carbs);
    }

    // MODIFIES: this
    // EFFECTS: update fat progress
    private void updateFat(double fat) {
        JProgressBar progress = (JProgressBar) panel.getComponent(10);
        progress.setMaximum((int) user.getMacrosNeeded().getFat());
        progress.setValue((int) fat);
    }

    // MODIFIES: this
    // EFFECTS: update weight progress
    private void updateWeight() {
        updateWeightLabels();
        updateWeightProgress();
    }

    // MODIFIES: this
    // EFFECTS: update weight label info
    private void updateWeightLabels() {
        ((JLabel) panel.getComponent(11)).setText("# of Days: " + user.getJournal().getLogs().size());
        ((JLabel) panel.getComponent(12)).setText("Start: " + user.getJournal().getLog(1).getWeight());
        ((JLabel) panel.getComponent(13)).setText("Goal: " + user.getJournal().getGoal());
    }

    // MODIFIES: this
    // EFFECTS: update weight progress info
    private void updateWeightProgress() {
        double goal = user.getJournal().getGoal();
        double starting = user.getJournal().getWeightTracker().get(0);
        double current = user.getJournal().getLastLog().getWeight();
        boolean onTrack = ((goal >= current && current >= starting) || (goal <= current && current <= starting));
        double value = (onTrack) ? Math.abs(current - starting) : 0;
        JProgressBar progress = (JProgressBar) panel.getComponent(14);
        progress.setMaximum((int) Math.abs(goal - starting) * 100);
        progress.setValue((int) value * 100);
    }
}
