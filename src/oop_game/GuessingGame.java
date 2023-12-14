package oop_game;

import java.util.HashSet;
import java.util.Set;

public abstract class GuessingGame {
    protected final String chosenWord;
    protected final Set<Character> userGuesses;
    protected boolean correctOrNot;
    protected int remainingTries;

    public GuessingGame(WordSource wordSource, WordSource.DifficultyLevel difficulty, int maxTries) {
        this.chosenWord = wordSource.getRandomWord(difficulty);
        this.userGuesses = new HashSet<>();
        this.correctOrNot = false;
        this.remainingTries = maxTries;
    }

    public abstract void play();

    public abstract void makeGuess(String guess);

    public abstract void giveUp();

    public abstract boolean isGameOver();

    public abstract String getVisibleWord();

    public abstract Set<Character> getGuessedLetters();

    public abstract boolean isGameWon();

    public abstract int getRemainingTries();

    public String getChosenWord() {
        return chosenWord;
    }

    // Add other common methods if needed

    protected void decrementRemainingTries() {
        remainingTries--;
    }
}
