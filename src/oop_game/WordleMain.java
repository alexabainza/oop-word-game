package oop_game;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class WordleMain implements ActionListener, GameBase {
    private JFrame gameFrame;
    private WordleWordPanel[] wordPanelArray;
    private WordleUserPanel userPanel;
    private String wordleString;
    private int count;
    private WordSource wordSource = new FileWordSource();

    public WordleMain() {
        initializeGameFrame();
        initializeWordPanels();
        initializeUserPanel();
        initializeGame();
    }

    private void initializeGameFrame() {
        gameFrame = new JFrame("Wordle Game");
        gameFrame.setSize(300, 300);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setLayout(new GridLayout(7, 1));
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
        gameFrame.revalidate();
    }

    private void initializeGame() {
        count = 0;
        wordleString = wordSource.getRandomWord(WordSource.DifficultyLevel.EASY);
        System.out.println("Word for the day: " + wordleString);
        gameFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordleMain());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userWord = userPanel.getUserInput().getText().trim().toUpperCase();
        if (userWord.length() > 4) {
            if (isWordleWordEqualTo(userWord)) {
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
        userPanel.getUserInput().setText("");
    }

    private void clearAllPanels() {
        Arrays.stream(wordPanelArray).forEach(WordleWordPanel::clearWordleWordPanel);
    }

    private void endGame(boolean isWinner, String winMessage, String loseMessage) {
        clearAllPanels();
        if (isWinner) {
            JOptionPane.showMessageDialog(null, winMessage, "Congrats", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, loseMessage + "\nThe correct word was: " + wordleString, "Oops", JOptionPane.INFORMATION_MESSAGE);
        }
        gameFrame.dispose();
        showMainMenu();
    }

    private void showMainMenu() {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }

    private boolean isWordleWordEqualTo(String userWord) {
        String[] wordleWordsArray = wordleString.split("");
        String[] userWordsArray = userWord.split("");
        System.out.println("Wordle String: " + wordleString);
        System.out.println("Wordle Words Array: " + Arrays.toString(wordleWordsArray));

        for (int i = 0; i < 5; i++) {
            if (wordleWordsArray[i].equalsIgnoreCase(userWordsArray[i])) {
                wordPanelArray[count].setPanelText(userWordsArray[i], i, Color.GREEN);
                wordleWordsArray[i] = "";
                userWordsArray[i] = "";
                System.out.println("Comparison[" + i + "]: Matched - " + userWordsArray[i]);
            } else {
                System.out.println("Comparison[" + i + "]: Not Matched - Wordle: " + wordleWordsArray[i] + ", User: " + userWordsArray[i]);
            }
        }

        for (int i = 0; i < 5; i++) {
            if (!userWordsArray[i].isEmpty()) {
                for (int j = 0; j < 5; j++) {
                    if (i != j && wordleWordsArray[j].equalsIgnoreCase(userWordsArray[i])) {
                        System.out.println("Comparing: " + wordleWordsArray[j] + " with " + userWordsArray[i]);
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
		// TODO Auto-generated method stub
        return wordSource.getRandomWord();
	}

}
