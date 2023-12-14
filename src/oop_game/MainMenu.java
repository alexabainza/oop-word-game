package oop_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    JFrame menuFrame;
    private JLabel chooseGameLabel;
    private JButton wordleButton;
    private JButton hangmanButton;

    public MainMenu() {
        initializeUI();
        addComponents();
    }

    private void initializeUI() {
        menuFrame = new JFrame("Main Menu");
        menuFrame.setSize(300, 300);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLayout(new GridLayout(4, 1));
        menuFrame.setLocationRelativeTo(null);
        menuFrame.getContentPane().setBackground(Color.black);
    }

    private void addComponents() {
        chooseGameLabel = new JLabel("Choose Game");
        chooseGameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chooseGameLabel.setFont(new Font("Arial", Font.BOLD, 30));
        chooseGameLabel.setForeground(Color.WHITE);
        int labelPadding = 30;
        chooseGameLabel.setBorder(BorderFactory.createEmptyBorder(labelPadding, 0, 0, 0));

        menuFrame.add(chooseGameLabel);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonsPanel.setBackground(Color.black);
        wordleButton = new JButton("Wordle");
        hangmanButton = new JButton("Hangman");

        customizeButton(wordleButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                new WordleMain();
            }
        });

        customizeButton(hangmanButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDifficultySelection();
            }
        });

        buttonsPanel.add(wordleButton);
        buttonsPanel.add(hangmanButton);

        int buttonPadding = 10;
        wordleButton.setBorder(BorderFactory.createEmptyBorder(buttonPadding, buttonPadding, buttonPadding, buttonPadding));
        hangmanButton.setBorder(BorderFactory.createEmptyBorder(buttonPadding, buttonPadding, buttonPadding, buttonPadding));

        menuFrame.add(buttonsPanel);
        menuFrame.setVisible(true);
    }
    
    private void showDifficultySelection() {
        menuFrame.getContentPane().removeAll(); // Clear existing components

        JLabel difficultyLabel = new JLabel("Select Difficulty");
        difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 30));
        difficultyLabel.setForeground(Color.WHITE);
        int labelPadding = 30;
        difficultyLabel.setBorder(BorderFactory.createEmptyBorder(labelPadding, 0, 0, 0));

        JPanel difficultyPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        difficultyPanel.setBackground(Color.black);

        JButton easyButton = new JButton("Easy");
        JButton hardButton = new JButton("Hard");

        customizeButton(easyButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
//                new HangmanMain(new FileWordSource(), WordSource.DifficultyLevel.EASY, 6).startHangman();
            }
        });


        customizeButton(hardButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
//                new HangmanMain(new FileWordSource(), WordSource.DifficultyLevel.HARD, 6).startHangman();
            }
        });

        difficultyPanel.add(easyButton);
        difficultyPanel.add(hardButton);

        int buttonPadding = 10;
        easyButton.setBorder(BorderFactory.createEmptyBorder(buttonPadding, buttonPadding, buttonPadding, buttonPadding));
        hardButton.setBorder(BorderFactory.createEmptyBorder(buttonPadding, buttonPadding, buttonPadding, buttonPadding));

        menuFrame.add(difficultyLabel);
        menuFrame.add(difficultyPanel);

        menuFrame.revalidate();
        menuFrame.repaint();
    }
    
    
    private void customizeButton(JButton button, ActionListener actionListener) {
        button.setBackground(new Color(50, 205, 50)); // Green color
        button.setForeground(Color.WHITE);
        button.setFont(new Font("MonoSpaced", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 50));

        button.addActionListener(actionListener);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().menuFrame.setVisible(true));
    }
}
