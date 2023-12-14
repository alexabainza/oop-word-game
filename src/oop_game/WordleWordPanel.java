package oop_game;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class WordleWordPanel extends JPanel{
	JLabel[] wordColumns = new JLabel[5];

    public WordleWordPanel() {
        this.setLayout(new GridLayout(1, 5));
        Border blackBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        for (int i = 0; i < 5; i++) {
            wordColumns[i] = new JLabel();
            wordColumns[i].setHorizontalAlignment(JLabel.CENTER);
            wordColumns[i].setOpaque(true);
            wordColumns[i].setBorder(blackBorder);
            this.add(wordColumns[i]);
        }
    }

    public void clearWordleWordPanel() {
        for (int i = 0; i < 5; i++) {
            wordColumns[i].setText("");
            wordColumns[i].setBackground(null);
        }
    }

    public void setPanelText(String charValue, int position, Color color) {
        this.wordColumns[position].setText(charValue);
        this.wordColumns[position].setBackground(color);
    }
}
