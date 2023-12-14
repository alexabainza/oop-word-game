package oop_game;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HangmanMain {
    private final String chosenWord;
    private final Set<Character> userGuesses;
    private boolean correctOrNot;
    private int wordChecker;
    private int remainingTries;
    private int hintCounter;  // New field to track hints given

    public HangmanMain(WordSource wordSource, WordSource.DifficultyLevel difficulty, int maxTries) {
        this.chosenWord = wordSource.getRandomWord(difficulty);
        this.userGuesses = new HashSet<>();
        this.correctOrNot = false;
        this.wordChecker = 0;
        this.remainingTries = maxTries;
        this.hintCounter = 0;  // Initialize hint counter
    }

    public void play() {
        if (chosenWord == null) {
            System.out.println("No word available. Exiting game.");
            return;
        }

        System.out.println("Chosen Word: " + chosenWord);
        Scanner keyboard = new Scanner(System.in);

        while (!correctOrNot && remainingTries > 0) {
            System.out.println("Enter a letter or type 'hint' for a hint: ");
            String userGuess = keyboard.nextLine().toLowerCase();

            if (userGuess.equals("hint")) {
                giveHint();
            } else {
                makeGuess(userGuess);
            }

            // Update state and check for game over
            if (isGameOver()) {
                printWordState();
                break;
            }
        }

        keyboard.close();
    }

    public void giveHint() {
        if (hintCounter < getMaxHints()) {
            char hintLetter = findUnrevealedLetter();
            if (hintLetter != '\0') {
                userGuesses.add(hintLetter);
                hintCounter++;
                System.out.println("Hint: The next unrevealed letter is '" + hintLetter + "'");
            } else {
                System.out.println("No more unrevealed letters for a hint.");
            }
        } else {
            System.out.println("No more hints left.");
        }
    }

    private char findUnrevealedLetter() {
        for (int x = 0; x < chosenWord.length(); x++) {
            char currentChar = chosenWord.charAt(x);
            if (!userGuesses.contains(currentChar)) {
                return currentChar;
            }
        }
        return '\0';  // Return null character if all letters are revealed
    }

    private int getMaxHints() {
        return (remainingTries == 6) ? 3 : 2;  // Adjust based on remaining tries
    }

    public void makeGuess(String guess) {
        // The existing makeGuess logic remains unchanged
        char guessedLetter = processGuess(guess);

        // Decrease remainingTries only if the guess is incorrect
        if (!correctOrNot && guessedLetter != '\0' && !chosenWord.contains(String.valueOf(guessedLetter))) {
            remainingTries--;
        }

        printWordState();

        if (remainingTries == 0) {
            System.out.println("\nYou lost. Game Over!");
            System.out.println("The correct word was: " + chosenWord);
        }
    }

    private char processGuess(String guess) {
        if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
            System.out.println("Please enter a single letter. Non-letter characters do not count.");
            return '\0';  // Return null character for invalid guess
        }

        char guessedLetter = guess.charAt(0);

        if (!userGuesses.contains(guessedLetter)) {
            userGuesses.add(guessedLetter);

            if (wordChecker == chosenWord.length()) {
                correctOrNot = true;
                System.out.println("\nYou got it!");
            }
        } else {
            System.out.println("You already guessed that letter. Try again.");
            return '\0';  // Return null character for repeated guess
        }

        return guessedLetter;
    }
    public void giveUp (){
        remainingTries = 0;
    }
    public boolean isGameOver() {
        return correctOrNot || remainingTries == 0;
    }

    private void printWordState() {
        wordChecker = 0;
        for (int x = 0; x < chosenWord.length(); x++) {
            char currentChar = chosenWord.charAt(x);
            if (userGuesses.contains(currentChar)) {
                System.out.print(currentChar);
                wordChecker++;
            } else {
                System.out.print("-");
            }
        }

        System.out.println();
        System.out.println("Guessed Letters: " + userGuesses);
        System.out.println("Remaining Tries: " + remainingTries);
    }
    public String getVisibleWord() {
        StringBuilder visibleWord = new StringBuilder();
        for (int x = 0; x < chosenWord.length(); x++) {
            char currentChar = chosenWord.charAt(x);
            if (userGuesses.contains(currentChar)) {
                visibleWord.append(currentChar);
            } else {
                visibleWord.append("-");
            }
        }
        return visibleWord.toString();
    }
    
    public Set<Character> getGuessedLetters() {
        return userGuesses;
    }
    
    public boolean isGameWon() {
        return correctOrNot;
    }
    public int getRemainingTries() {
        return remainingTries;
    }
    public String getChosenWord() {
        return chosenWord;
    }

}
