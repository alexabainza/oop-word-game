package oop_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class HangmanGUI extends GuessingGame implements ActionListener, GameBase {
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
        super(hangmanGame.getWordSource(), WordSource.DifficultyLevel.EASY, 6);
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
        frame.add(backToMainMenuButton);

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
//            JOptionPane.showMessageDialog(null, "The correct word is " + getChosenWord(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            showMainMenu();
        }
    }
    private void giveHint() {
        hangmanGame.giveHint();

        // Update GUI after giving a hint
        updateGUI();

        // Check for game over
        if (hangmanGame.isGameOver()) {
//            showGameOverDialog(false);
            frame.dispose();
            showMainMenu();
        }
    }
    
    
    
    private void quit() {
        hangmanGame.giveUp();
//        showGameOverDialog(false);
        frame.dispose();
        showMainMenu();
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
//            showGameOverDialog(false);
//            frame.dispose();
            showMainMenu();
        }
        if(hangmanGame.isGameWon()) {
//            frame.dispose();
            showMainMenu();
        }
    }

    
    private void showMainMenu() {
    	frame.dispose();
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HangmanMain hangmanGame = new HangmanMain(new FileWordSource(), WordSource.DifficultyLevel.EASY, 6);
            new HangmanGUI(hangmanGame);
        });
    }


	@Override
	public String getRandomWord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean isGameWon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRemainingTries() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getChosenWord() {
		// TODO Auto-generated method stub
		return chosenWord;
	}

	@Override
	public void makeGuess(String guess) {
		// TODO Auto-generated method stub
		
	}
	
//	 @Override
//	    protected void showGameOverDialog(boolean isWinner) {
//	        String message;
//	        if (isWinner) {
//	            message = "Congratulations! You guessed the word.";
//	        } else {
//	            message = "Sorry, you lost. The correct word was: " + getChosenWord();
//	        }
//
//	        // Display the game over dialogue
//	        JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
//
//	        // Add any additional logic or actions specific to HangmanMain after game over
//	        // For example, you might want to start a new game or return to the main menu.
//	    }

}