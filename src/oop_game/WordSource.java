package oop_game;


public interface WordSource extends GameBase {
    enum DifficultyLevel {
        EASY, HARD
    }
    String getRandomWord();  // Method without difficulty
    String getRandomWord(DifficultyLevel difficulty);
}
