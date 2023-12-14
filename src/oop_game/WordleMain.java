package oop_game;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class WordleMain extends GuessingGame implements ActionListener, GameBase {
    private JFrame gameFrame;
    private WordleWordPanel[] wordPanelArray;
    private WordleUserPanel userPanel;
    private int count;
    private JLabel remainingTriesLabel;

    public WordleMain() {
        super(new FileWordSource(), WordSource.DifficultyLevel.EASY, 6);
        initializeGameFrame();
        initializeWordPanels();
        initializeUserPanel();
        initializeGame();
        remainingTriesLabel = new JLabel("Remaining Tries: " + getRemainingTries());
        gameFrame.add(remainingTriesLabel);
    }

    private void initializeGameFrame() {
        gameFrame = new JFrame("Wordle Game");
        gameFrame.setSize(300, 300);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setLayout(new GridLayout(8, 1));
        gameFrame.setLocationRelativeTo(null);
        
        
    }

    private void initializeWordPanels() {
        wordPanelArray = new WordleWordPanel[6];
        for (int i = 0; i < 6; i++) {
            wordPanelArray[i] = new WordleWordPanel();
            gameFrame.add(wordPanelArray[i]);
        }
    }

    private void initializeUserPanel() {
        userPanel = new WordleUserPanel();
        userPanel.getOkButton().addActionListener(this);
        gameFrame.add(userPanel);

        // Add KeyListener to the text field
        userPanel.getUserInput().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    actionPerformed(new ActionEvent(userPanel.getOkButton(), ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });
        gameFrame.revalidate();
    }

    private void initializeGame() {
        count = 0;
        System.out.println("Word for the day: " + getChosenWord());
        gameFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordleMain());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userWord = userPanel.getUserInput().getText().trim().toUpperCase();
        makeGuess(userWord);
    }

    @Override
    public void makeGuess(String guess) {
        if (guess.length() > 4) {
            if (isWordleWordEqualTo(guess)) {
                clearAllPanels();
                endGame(true, "You win", "Congrats");
                return;
            }
        }
        if (count >= 5) {
            endGame(false, "You lost. Do better next time!", "Oops");
            return;
        }
        count++;
        decrementTries();

        remainingTriesLabel.setText("Remaining Tries: " + getRemainingTries());

        userPanel.getUserInput().setText("");
    }

    private void clearAllPanels() {
        Arrays.stream(wordPanelArray).forEach(WordleWordPanel::clearWordleWordPanel);
    }

    private void endGame(boolean isWinner, String winMessage, String loseMessage) {
        clearAllPanels();
        if (isWinner) {
            showGameOverDialog(true);
        } else {
            showGameOverDialog(false);
        }
        gameFrame.dispose();
        showMainMenu();
    }
    private void showMainMenu() {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }

    private boolean isWordleWordEqualTo(String userWord) {
        String[] wordleWordsArray = getChosenWord().split("");
        String[] userWordsArray = userWord.split("");
        System.out.println("Wordle String: " + getChosenWord());
        System.out.println("Wordle Words Array: " + Arrays.toString(wordleWordsArray));

        for (int i = 0; i < 5; i++) {
            if (wordleWordsArray[i].equalsIgnoreCase(userWordsArray[i])) {
                wordPanelArray[count].setPanelText(userWordsArray[i], i, Color.GREEN);
                wordleWordsArray[i] = "";
                userWordsArray[i] = "";
            }
        }

        for (int i = 0; i < 5; i++) {
            if (!userWordsArray[i].isEmpty()) {
                for (int j = 0; j < 5; j++) {
                    if (i != j && wordleWordsArray[j].equalsIgnoreCase(userWordsArray[i])) {
                        wordPanelArray[count].setPanelText(userWordsArray[i], i, Color.YELLOW);
                        wordleWordsArray[j] = "";
                        userWordsArray[i] = "";
                        break;
                    }
                }
                if (!userWordsArray[i].isEmpty()) {
                    wordPanelArray[count].setPanelText(userWordsArray[i], i, Color.GRAY);
                }
            }
        }

        System.out.println("Comparison Result: " + Arrays.equals(wordleWordsArray, userWordsArray));

        return Arrays.equals(wordleWordsArray, userWordsArray);
    }

    @Override
    public String getRandomWord() {
        return super.wordSource.getRandomWord(WordSource.DifficultyLevel.EASY);
    }

	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void printWordState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getVisibleWord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Character> getGuessedLetters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isGameWon() {
		// TODO Auto-generated method stub
		return false;
	}

}
