package oop_game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;

public class HangmanMain extends GuessingGame implements ActionListener, GameBase{
    private final Set<Character> userGuesses; 
    private int wordChecker;
    private int hintCounter;  // New field to track hints given

    public HangmanMain(WordSource wordSource, WordSource.DifficultyLevel difficulty, int maxTries) {
        super(wordSource, difficulty, maxTries);
        this.userGuesses = new HashSet<>();
        this.wordChecker = 0;
        this.remainingTries = maxTries;
        this.hintCounter = 0;  // Initialize hint counter
        System.out.println("Chosen Word (initial): " + getChosenWord());
    }


    public void play() {
        if (getChosenWord() == null) {
            System.out.println("No word available. Exiting game.");
            return;
        }

        System.out.println("Chosen Word: " + getChosenWord());
        Scanner keyboard = new Scanner(System.in);

        while (!isCorrectOrNot() && remainingTries > 0) {
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
        for (int x = 0; x < getChosenWord().length(); x++) {
            char currentChar = getChosenWord().charAt(x);
            if (!userGuesses.contains(currentChar)) {
                return currentChar;
            }
        }
        return '\0';  // Return null character if all letters are revealed
    }

    private int getMaxHints() {
        return (getRemainingTries() == 6) ? 3 : 2;  // Adjust based on remaining tries
    }

    public void makeGuess(String guess) {
        // The existing makeGuess logic remains unchanged
        char guessedLetter = processGuess(guess);

        // Decrease remainingTries only if the guess is incorrect
        if (!isCorrectOrNot() && guessedLetter != '\0' && !getChosenWord().contains(String.valueOf(guessedLetter))) {
        	decrementTries();
        }

        printWordState();
        if (getRemainingTries() == 0 || isGameWon()) {
            // Display the message after checking for game won or remaining tries
            displayGameOverMessage();
        }
//        if (isGameWon()) {
//            System.out.println("\nYou got it right!");
//            JOptionPane.showMessageDialog(null, "You got it right!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
//        }
//
//        if (getRemainingTries() == 0) {
//            System.out.println("\nYou lost. Game Over!");
//            System.out.println("The correct word was: " + getChosenWord());
//            JOptionPane.showMessageDialog(null, "The correct word is " + getChosenWord(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
//
//        }
    }
    
    private void displayGameOverMessage() {
        if (isGameWon()) {
            JOptionPane.showMessageDialog(null, "You got it right!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("\nYou lost. Game Over!");
            System.out.println("The correct word was: " + getChosenWord());
            JOptionPane.showMessageDialog(null, "The correct word is " + getChosenWord(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }}

    private char processGuess(String guess) {
        if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
            System.out.println("Please enter a single letter. Non-letter characters do not count.");
            return '\0';  // Return null character for invalid guess
        }

        char guessedLetter = guess.charAt(0);

        if (!userGuesses.contains(guessedLetter)) {
            userGuesses.add(guessedLetter);

            if (wordChecker == getChosenWord().length()) {
                setCorrectOrNot(true);
                JOptionPane.showMessageDialog(null, "You got it right!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
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
        System.out.println("Visible Word: " + getVisibleWord());
        System.out.println("Chosen Word: " + getChosenWord());
        return getRemainingTries() <= 0;
    }

    protected void printWordState() {
        wordChecker = 0;
        for (int x = 0; x < getChosenWord().length(); x++) {
            char currentChar = getChosenWord().charAt(x);
            if (userGuesses.contains(currentChar)) {
                System.out.print(currentChar);
                wordChecker++;
            } else {
                System.out.print("-");
            }
        }

        System.out.println();
        System.out.println("Guessed Letters: " + userGuesses);
        System.out.println("Remaining Tries: " + getRemainingTries());
    }

    public String getVisibleWord() {
        StringBuilder visibleWord = new StringBuilder();
        for (int x = 0; x < getChosenWord().length(); x++) {
            char currentChar = getChosenWord().charAt(x);
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

    public String getChosenWord() {
        return chosenWord;
    }
    

//    
//    @Override
//    protected void showGameOverDialog(boolean isWinner) {
//        String message;
//        if (isWinner) {
//            message = "Congratulations! You guessed the word.";
//        } else {
//            message = "Sorry, you lost. The correct word was: " + getChosenWord();
//        }
//
//        // Display the game over dialogue
//        JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
//        
//        // Add any additional logic or actions specific to HangmanMain after game over
//        // For example, you might want to start a new game or return to the main menu.
//    }
    
    


	@Override
	public String getRandomWord() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}