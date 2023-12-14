package oop_game;

import javax.swing.*;
import java.awt.*;

public class HangmanGUI {
    private final HangmanMain hangmanGame;
    private final JFrame frame;
    private final JLabel wordLabel;
    private final JLabel guessedLettersLabel;
    private final JLabel remainingTriesLabel;
    private final JTextField guessTextField;
    private final JButton guessButton;
    private final JButton hintButton;
    private final JButton quitButton;

    public HangmanGUI(HangmanMain hangmanGame) {
        this.hangmanGame = hangmanGame;

        // Create and configure the main JFrame
        frame = new JFrame("Hangman Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1));

        
        // Create GUI components
        wordLabel = new JLabel();
        guessedLettersLabel = new JLabel();
        remainingTriesLabel = new JLabel();
        guessTextField = new JTextField();
        guessButton = new JButton("Guess a letter");
        hintButton = new JButton("Hint");
        quitButton = new JButton("Quit Game");
        

        // Add components to the frame
        frame.add(wordLabel);
        frame.add(guessedLettersLabel);
        frame.add(remainingTriesLabel);
        frame.add(guessTextField);
        frame.add(guessButton);
        frame.add(hintButton);
        frame.add(quitButton);

        // Set up event handling
        guessButton.addActionListener(e -> handleGuess());
        guessTextField.addActionListener(e -> handleGuess());
        hintButton.addActionListener(e -> giveHint());
        quitButton.addActionListener(e -> quit());

        // Set initial GUI state
        updateGUI();

        // Pack and display the frame
        frame.pack();
        frame.setVisible(true);
    }

    private void handleGuess() {
        String userGuess = guessTextField.getText().toLowerCase();

        if (userGuess.length() != 1 || !Character.isLetter(userGuess.charAt(0))) {
            JOptionPane.showMessageDialog(frame, "Please enter a single letter. Non-letter characters do not count.", "Invalid Guess", JOptionPane.WARNING_MESSAGE);
            return;
        }

        char guessedLetter = userGuess.charAt(0);

        hangmanGame.makeGuess(String.valueOf(guessedLetter));

        // Update GUI after making a guess
        updateGUI();
        guessTextField.setText("");


        // Check for game over
        if (hangmanGame.isGameOver()) {
            showGameOverDialog();
        }
    }

    private void giveHint() {
        hangmanGame.giveHint();

        // Update GUI after giving a hint
        updateGUI();

        // Check for game over
        if (hangmanGame.isGameOver()) {
            showGameOverDialog();
        }
    }

    private void quit() {
        hangmanGame.giveUp();
        showGameOverDialog();
    }

//    private void updateGUI() {
//        wordLabel.setText("Word: " + hangmanGame.getVisibleWord());
//        guessedLettersLabel.setText("Guessed Letters: " + hangmanGame.getGuessedLetters());
//        remainingTriesLabel.setText("Remaining Tries: " + hangmanGame.getRemainingTries());
//        guessTextField.setText("");  // Clear the text field after each guess
//    }

    private void updateGUI() {
        wordLabel.setText("Word: " + hangmanGame.getVisibleWord());
        guessedLettersLabel.setText("Guessed Letters: " + hangmanGame.getGuessedLetters());
        remainingTriesLabel.setText("Remaining Tries: " + hangmanGame.getRemainingTries());

        // Check if the game is over
        if (hangmanGame.isGameOver()) {
            showGameOverDialog();
        }
    }

    private void showGameOverDialog() {
        String message;
        if (hangmanGame.isGameWon()) {
            message = "Congratulations! You guessed the word.";
        } else {
            message = "Sorry, you lost. The correct word was: " + hangmanGame.getChosenWord();
        }

        // Close the application

        JOptionPane.showMessageDialog(frame, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);

        // Dispose the current HangmanGUI frame
        frame.dispose();

        // Create a new instance of MainMenu
        SwingUtilities.invokeLater(() -> new MainMenu().menuFrame.setVisible(true));    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HangmanMain hangmanGame = new HangmanMain(new FileWordSource(), WordSource.DifficultyLevel.EASY, 6);
            new HangmanGUI(hangmanGame);
        });
    }
}
