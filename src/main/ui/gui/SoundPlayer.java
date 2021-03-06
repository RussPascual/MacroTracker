package ui.gui;

import model.User;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * SoundPlayer plays sounds based on wav files and the conditions for playing
 */
public class SoundPlayer {

    private static final String WOW = "./data/wow.wav";
    private static final String YAY = "./data/yay.wav";

    private GUI gui;
    private boolean metCalorieGoal;
    private boolean metProteinGoal;
    private boolean metCarbGoals;
    private boolean metFatGoals;
    private boolean metWeightGoal;

    // EFFECTS: construct a sound player
    public SoundPlayer(GUI gui) {
        this.gui = gui;
        metCalorieGoal = false;
        metProteinGoal = false;
        metCarbGoals = false;
        metFatGoals = false;
        metWeightGoal = false;
    }

    // MODIFIES: this
    // EFFECTS: update macro progress fields
    public void updateMacroProgress() {
        User user = gui.getUser();
        metCalorieGoal = user.metCalorieGoals();
        metProteinGoal = user.metProteinGoals();
        metCarbGoals = user.metCarbGoals();
        metFatGoals = user.metFatGoals();
    }

    // MODIFIES: this
    // EFFECTS: update weight progress field
    public void updateWeightProgress() {
        metWeightGoal = gui.getUser().getJournal().remainingGoal() == 0;
    }

    // MODIFIES: this
    // EFFECTS: play sound if macro target is reached
    public void playSoundWhenMacrosAreHit() {
        if (checkMacros() && gui.isInstantiated()) {
            playSound(WOW);
        }
        updateMacroProgress();
    }

    // MODIFIES: this
    // EFFECTS: return true if one of the targets are reached and sound has not yet been played
    private boolean checkMacros() {
        User user = gui.getUser();
        boolean calories = user.metCalorieGoals() && !metCalorieGoal;
        boolean protein = user.metProteinGoals() && !metProteinGoal;
        boolean carbs = user.metCarbGoals() && !metCarbGoals;
        boolean fat = user.metFatGoals() && !metFatGoals;
        return  calories || protein || carbs || fat;
    }

    // MODIFIES: this
    // EFFECTS: play sound if weight goal is reached
    public void playSoundWhenWeightGoalReached() {
        if (checkWeight() && gui.isInstantiated()) {
            playSound(YAY);
        }
        updateWeightProgress();
    }

    // MODIFIES: this
    // EFFECTS: return true if goal is reached and sound has not been played yet
    private boolean checkWeight() {
        return gui.getUser().getJournal().remainingGoal() <= 0 && !metWeightGoal;
    }

    // MODIFIES: this
    // EFFECTS: play wav file's sound. learned sound playing concept from
    //          https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator
    private void playSound(String wav) {
        try {
            File file = new File(wav).getAbsoluteFile();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}
