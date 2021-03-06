package ui.actionhandlers;

import model.game.Game;
import persistence.JsonReader;
import ui.ChessGameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

/**
 * Action listener for new game button
 */
public class LoadGameAction implements ActionListener {

    private static final String JSON_DIRECTORY = "./data/"; // General directory of all save files
    private static List<String> gameNames; // Names of the games that were already loaded 

    private final JFrame parentFrame; // Main window frame
    private JDialog frame; // Frame that contains all the games that user
    private JScrollPane contentPanel; // Panel with all the screen contests

    // EFFECTS : Constructs a new load game action listener
    public LoadGameAction(JFrame parentFrame, List<String> gameNames) {
        this.parentFrame = parentFrame;
        LoadGameAction.gameNames = gameNames;
    }

    // MODIFIES : This
    // EFFECTS : On activation creates a new JDialog which displays all the save files
    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel buttonPanel = createSaveFilePanel();
        contentPanel = new  JScrollPane(buttonPanel);
        contentPanel.createVerticalScrollBar();
        displayLoadGameScreen();
    }


    // MODIFIES : This
    // EFFECTS : Constructs a panel with buttons that have names of already existing games
    private JPanel createSaveFilePanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(gameNames.size(),1));
        for (String str : gameNames) {
            JButton jbutton = new JButton(str);
            jbutton.setPreferredSize(new Dimension(buttonPanel.getWidth(), 100));
            jbutton.addActionListener(new LoadGameName(jbutton));
            buttonPanel.add(jbutton,Component.CENTER_ALIGNMENT);
        }
        return buttonPanel;
    }


    // MODIFIES : This, parentFrame
    // EFFECTS : Disables the main menu, adds all the contents, and makes the frame visible
    private void displayLoadGameScreen() {
        parentFrame.setEnabled(false);
        frame = new JDialog();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parentFrame.setEnabled(true);
            }
        });
        frame.setPreferredSize(new Dimension(500, 500));
        frame.add(contentPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }


    /**
     * Action listener that loads game with name of the button on click
     */
    private class LoadGameName implements ActionListener {

        private final JButton buttonPressed; //Button that has been pressed
        private JsonReader jsonReader;

        // EFFECTS : Constructs new loadGameName action listener
        public LoadGameName(JButton jbutton) {
            this.buttonPressed = jbutton;
        }


        // EFFECTS : On activation loads the game with the name of the button, and displays it
        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.setVisible(false);
            String gameName = buttonPressed.getText();
            Game g = null;
            try {
                g = loadGame(gameName);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            frame.dispose();
            new ChessGameGUI(g, gameNames, parentFrame);
        }

        // EFFECTS: Loads a game with a given name
        private Game loadGame(String command) throws IOException {
            String dir = JSON_DIRECTORY + command + ".json";
            jsonReader = new JsonReader(dir);
            Game g = jsonReader.read();
            return g;
        }
    }
}
