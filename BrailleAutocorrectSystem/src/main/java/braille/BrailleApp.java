package braille;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BrailleApp extends Application {
    private static final Map<Set<Character>, Character> brailleToChar = new HashMap<>();
    private final OptimizedDictionary dictionary = new OptimizedDictionary();
    private final TextArea outputArea = new TextArea();
    private final TextField inputField = new TextField();
    private final StringBuilder decodedLiveWord = new StringBuilder();

    static {
        brailleToChar.put(Set.of('d'), 'a');
        brailleToChar.put(Set.of('d', 'w'), 'b');
        brailleToChar.put(Set.of('d', 'k'), 'c');
        brailleToChar.put(Set.of('d', 'k', 'o'), 'd');
        brailleToChar.put(Set.of('d', 'o'), 'e');
        brailleToChar.put(Set.of('d', 'w', 'k'), 'f');
        brailleToChar.put(Set.of('d', 'w', 'k', 'o'), 'g');
        brailleToChar.put(Set.of('d', 'w', 'o'), 'h');
        brailleToChar.put(Set.of('w', 'k'), 'i');
        brailleToChar.put(Set.of('w', 'k', 'o'), 'j');
        brailleToChar.put(Set.of('d', 'q'), 'k');
        brailleToChar.put(Set.of('d', 'w', 'q'), 'l');
        brailleToChar.put(Set.of('d', 'q', 'k'), 'm');
        brailleToChar.put(Set.of('d', 'q', 'k', 'o'), 'n');
        brailleToChar.put(Set.of('d', 'q', 'o'), 'o');
        brailleToChar.put(Set.of('d', 'w', 'q', 'k'), 'p');
        brailleToChar.put(Set.of('d', 'w', 'q', 'k', 'o'), 'q');
        brailleToChar.put(Set.of('d', 'w', 'q', 'o'), 'r');
        brailleToChar.put(Set.of('w', 'q', 'k'), 's');
        brailleToChar.put(Set.of('w', 'q', 'k', 'o'), 't');
        brailleToChar.put(Set.of('d', 'q', 'p'), 'u');
        brailleToChar.put(Set.of('d', 'w', 'q', 'p'), 'v');
        brailleToChar.put(Set.of('w', 'k', 'o', 'p'), 'w');
        brailleToChar.put(Set.of('d', 'q', 'k', 'p'), 'x');
        brailleToChar.put(Set.of('d', 'q', 'k', 'o', 'p'), 'y');
        brailleToChar.put(Set.of('d', 'q', 'o', 'p'), 'z');
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<String> words = Files.readAllLines(Paths.get("src/main/resources/words.txt"));
        dictionary.loadWords(words);

        inputField.setPromptText("Enter Braille sequences like: dk dwk dko.");

        inputField.setOnKeyReleased(event -> {
            String text = inputField.getText();

            if (text.endsWith(".")) {
                String[] sequences = text.trim().substring(0, text.length() - 1).split(" ");
                StringBuilder finalDecoded = new StringBuilder();

                for (String seq : sequences) {
                    Set<Character> keys = new HashSet<>();
                    for (char c : seq.toCharArray()) keys.add(c);
                    Character letter = brailleToChar.get(keys);
                    finalDecoded.append(letter != null ? letter : '?');
                }

                String fullWord = finalDecoded.toString();
                String suggestion = dictionary.autocorrect(fullWord);

                outputArea.appendText("\n[Decoded]: " + fullWord + "\n");
                outputArea.appendText("[Suggestion]: " + suggestion + "\n\n");

                inputField.clear();
                decodedLiveWord.setLength(0);
            } else if (event.getText().equals(" ")) {
                // Space detected, decode the last sequence and show live letter
                String[] parts = text.trim().split(" ");
                if (parts.length > 0) {
                    String lastSeq = parts[parts.length - 1];
                    Set<Character> keys = new HashSet<>();
                    for (char c : lastSeq.toCharArray()) keys.add(c);
                    Character letter = brailleToChar.get(keys);

                    decodedLiveWord.append(letter != null ? letter : '?');
                    outputArea.appendText((letter != null ? letter : '?') + "");
                }
            }
        });

        VBox root = new VBox(10, inputField, outputArea);
        root.setPadding(new Insets(15));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Braille Autocorrect & Suggestion System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
