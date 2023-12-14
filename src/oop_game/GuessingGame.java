package oop_game;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public abstract class GuessingGame {
    protected String chosenWord;
    protected Set<Character> userGuesses;
    protected boolean correctOrNot;
    protected int remainingTries;
    protected WordSource wordSource; // Fix the type to WordSource

    public GuessingGame(WordSource wordSource, WordSource.DifficultyLevel difficulty, int maxTries) {
        this.chosenWord = wordSource.getRandomWord(difficulty);
        this.userGuesses = new HashSet<>();
        this.correctOrNot = false;
        this.remainingTries = maxTries;
        this.wordSource = wordSource; // Assign the value

    }

    public abstract void makeGuess(String guess);

    public boolean isGameOver() {
    	return remainingTries==0;
    }


    public abstract boolean isGameWon();

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
        
        SwingUtilities.invokeLater(MainMenu::new);

    }
    
    protected void decrementTries() {
        remainingTries--;
    }

}
