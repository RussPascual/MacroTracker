package ui.gui;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI initializes the frame and includes the panels for the user and possible functionality for user interaction
 */
public class GUI extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private User user;
    private UserPanel userPanel;
    private JournalPanel journalPanel;
    private ProgressPanel progressPanel;
    private FavouritesPanel favouritesPanel;
    private NewFoodPanel newFoodPanel;
    private JMenuBar menuBar;
    private JPanel master;
    private CardLayout card;
    private Color color;
    private SoundPlayer player;

    // EFFECTS: constructs a GUI for macro tracker
    public GUI() {
        super("Macro Tracker");
        user = new User("Marco", 0, 1);
        color = new Color(100, 100, 150);
        player = new SoundPlayer(this);
        initializeGraphics();
        initializeMasterPanel();
        initializeMenuBar();
        initializeUser();
        initializeJournal();
        initializeProgress();
        initializeFavourites();
        initializeNewFood();
        setVisible(true);
        card.show(master, "0");
    }

    public User getUser() {
        return user;
    }

    // MODIFIES: this
    // EFFECTS: return true if userpanel is instantiated and false otherwise
    public boolean isInstantiated() {
        return userPanel.isInitialized();
    }

    public SoundPlayer getPlayer() {
        return player;
    }

    // MODIFIES: this
    // EFFECTS: initialize master panel
    private void initializeMasterPanel() {
        master = new JPanel();
        card = new CardLayout();
        master.setLayout(card);
        master.setVisible(true);
        add(master);
    }

    // MODIFIES: this
    // EFFECTS: initialize userPanel
    private void initializeUser() {
        userPanel = new UserPanel(this);
        master.add(userPanel.getPanel(), "0");
    }

    // MODIFIES: this
    // EFFECTS: initialize journalPanel
    private void initializeJournal() {
        journalPanel = new JournalPanel(this);
        master.add(journalPanel.getPanel(), "1");
    }

    // MODIFIES: this
    // EFFECTS: initialize progressPanel
    private void initializeProgress() {
        progressPanel = new ProgressPanel(this);
        master.add(progressPanel.getPanel(), "2");
    }

    // MODIFIES: this
    // EFFECTS: initialize favouritesPanel
    private void initializeFavourites() {
        favouritesPanel = new FavouritesPanel(this);
        master.add(favouritesPanel.getPanel(), "3");
    }

    // MODIFIES: this
    // EFFECTS: initialize newFoodPanel
    private void initializeNewFood() {
        newFoodPanel = new NewFoodPanel(this);
        master.add(newFoodPanel.getPanel(), "4");
    }

    // MODIFIES: this
    // EFFECTS: initialize menu bar
    private void initializeMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(color);
        setJMenuBar(menuBar);
        addUserMenu();
        addJournalMenu();
        addProgressMenu();
        addFavouritesMenu();
        addNewFoodMenu();
        addDataOptions();
    }

    // MODIFIES: this
    // EFFECTS: add user menu to bar
    private void addUserMenu() {
        JMenuItem user = new JMenuItem("User");
        user.setBackground(color);
        user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu("0");
            }
        });
        menuBar.add(user);
    }

    // MODIFIES: this
    // EFFECTS: add journal menu to bar
    private void addJournalMenu() {
        JMenuItem journal = new JMenuItem("Journal");
        journal.setBackground(color);
        journal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu("1");
            }
        });
        menuBar.add(journal);
    }

    // MODIFIES: this
    // EFFECTS: add progress menu to bar
    private void addProgressMenu() {
        JMenuItem progress = new JMenuItem("Progress");
        progress.setBackground(color);
        progress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu("2");
            }
        });
        menuBar.add(progress);
    }

    // MODIFIES: this
    // EFFECTS: add favourites menu to bar
    private void addFavouritesMenu() {
        JMenuItem favourites = new JMenuItem("Favourites");
        favourites.setBackground(color);
        favourites.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu("3");
            }
        });
        menuBar.add(favourites);
    }

    // MODIFIES: this
    // EFFECTS: add newFood menu to bar
    private void addNewFoodMenu() {
        JMenuItem newFood = new JMenuItem("New Food");
        newFood.setBackground(color);
        newFood.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMenu("4");
            }
        });
        menuBar.add(newFood);
    }

    // MODIFIES: this
    // EFFECTS: add data option to bar
    private void addDataOptions() {
        JMenu data = new JMenu("Data");
        data.setBackground(color);
        JMenuItem save = new JMenuItem("Save");
        save.setBackground(color);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPanel.saveData();
            }
        });
        JMenuItem load = new JMenuItem("Load");
        load.setBackground(color);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPanel.loadData();
            }
        });
        data.add(save);
        data.add(load);
        menuBar.add(data);
    }

    // MODIFIES: this
    // EFFECTS: change menu shown based on input
    private void changeMenu(String s) {
        if (userPanel.isInitialized()) {
            card.show(master, s);
            if (userPanel.isLoaded()) {
//                copyUserPanel();
                userPanel.setLoaded(false);
            }
        }
        update();
    }

//    // MODIFIES: this
//    // EFFECTS: copy user panel to all other panels
//    private void copyUserPanel() {
//        user = userPanel.getUser();
//        progressPanel.setUser(user);
//        journalPanel.setJournal(user.getJournal());
//        favouritesPanel.updateUser(user);
//        newFoodPanel.updateUser(user);
//        player.setUser(user);
//    }

    // MODIFIES: this
    // EFFECTS: update all panels
    private void update() {
        userPanel.update();
        journalPanel.update();
        progressPanel.update();
        favouritesPanel.update();
        newFoodPanel.update();
        player.playSoundWhenMacrosAreHit();
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where the Macro Tracker will operate
    //          referenced https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Starter
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
