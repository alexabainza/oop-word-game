package oop_game;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FileWordSource implements WordSource {
    private final List<String> words;

    // Default file path
    private static final String DEFAULT_FILE_PATH = "/oop_game/words_alpha.txt";

    public FileWordSource() {
        this.words = new ArrayList<>();
        try {
            // Use the default file path
            URL resource = getClass().getResource(DEFAULT_FILE_PATH);
            if (resource != null) {
                Scanner scanner = new Scanner(new File(resource.toURI()));
                while (scanner.hasNext()) {
                    words.add(scanner.nextLine());
                }
            } else {
                System.err.println("Error loading resource: " + DEFAULT_FILE_PATH);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public FileWordSource(String filePath) {
        this.words = new ArrayList<>();
        try {
            // Use the provided file path or fall back to the default
            String finalFilePath = (filePath != null) ? filePath : DEFAULT_FILE_PATH;
            URL resource = getClass().getResource(finalFilePath);
            if (resource != null) {
                Scanner scanner = new Scanner(new File(resource.toURI()));
                while (scanner.hasNext()) {
                    words.add(scanner.nextLine());
                }
            } else {
                System.err.println("Error loading resource: " + finalFilePath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public FileWordSource(InputStream inputStream) {
        this.words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRandomWord() {
        Random rngWord = new Random();
        if (words.isEmpty()) {
            return null;
        }
        return words.get(rngWord.nextInt(words.size()));
    }

    @Override
    public String getRandomWord(DifficultyLevel difficulty) {
        Random rngWord = new Random();

        if (difficulty == DifficultyLevel.EASY) {
            // Filter words for Wordle with 5 letters
            List<String> fiveLetterWords = words.stream()
                    .filter(word -> word.length() == 5)
                    .toList();  // Requires Java 16 or above

            if (fiveLetterWords.isEmpty()) {
                return null; // No 5-letter words found
            }

            return fiveLetterWords.get(rngWord.nextInt(fiveLetterWords.size()));
        } else {
            List<String> filteredWords = words.stream()
                    .filter(word -> word.length() == getWordLength(difficulty))
                    .toList();  // Requires Java 16 or above

            if (filteredWords.isEmpty()) {
                return null; // No word of specified length found
            }

            return filteredWords.get(rngWord.nextInt(filteredWords.size()));
        }
    }

    private int getWordLength(DifficultyLevel difficulty) {
        return (difficulty == DifficultyLevel.EASY) ? 5 : 8;
    }
}
