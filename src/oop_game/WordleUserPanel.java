package oop_game;

import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class WordleUserPanel extends JPanel{
	 private JTextField userInput;
     private JButton okButton;

     public WordleUserPanel() {
         this.setLayout(new GridLayout(1, 2));
         userInput = new JTextField();
         okButton = new JButton("OK");
         this.add(userInput);
         this.add(okButton);
     }

     public JTextField getUserInput() {
         return userInput;
     }

     public JButton getOkButton() {
         return okButton;
     }

}
