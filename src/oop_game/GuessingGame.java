package oop_game;

import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public abstract class GuessingGame {
    protected String chosenWord;
    protected Set<Character> userGuesses;
    protected boolean correctOrNot;
    protected int remainingTries;
    protected WordSource wordSource; // Fix the type to WordSource
    protected JButton backToMainMenuButton;


    public GuessingGame(WordSource wordSource, WordSource.DifficultyLevel difficulty, int maxTries) {
        this.chosenWord = wordSource.getRandomWord(difficulty);
        this.userGuesses = new HashSet<>();
        this.correctOrNot = false;
        this.remainingTries = maxTries;
        this.wordSource = wordSource; // Assign the value
        this.backToMainMenuButton = new JButton("Back to Main Menu");
        customizeButton(backToMainMenuButton, e -> showMainMenu());

    }
    
    private void showMainMenu() {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
    private void customizeButton(JButton button, ActionListener actionListener) {
        button.addActionListener(actionListener);
    }
    public abstract void makeGuess(String guess);

    public boolean isGameOver() {
    	return remainingTries==0;
    }


    public boolean isGameWon() {
    	return correctOrNot == true;
    }
    public boolean isCorrectOrNot() {
		return correctOrNot;
	}
	public void setCorrectOrNot(boolean correctOrNot) {
		this.correctOrNot = correctOrNot;
	}

    public int getRemainingTries() {
    	return remainingTries;
    }

    public String getChosenWord() {
    	return chosenWord;
    }
    
    public WordSource getWordSource() {
        return wordSource;
    }
    
    protected void showGameOverDialog(boolean isWinner) {
        String message;
        if (isWinner) {
            message = "Congratulations! You guessed the word.";
        } else {
            message = "Sorry, you lost. The correct word was: " + chosenWord;
        }

        // Display the game over dialogue
        JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        
//        SwingUtilities.invokeLater(MainMenu::new);

    }
    
    protected void decrementTries() {
        remainingTries--;
    }

}