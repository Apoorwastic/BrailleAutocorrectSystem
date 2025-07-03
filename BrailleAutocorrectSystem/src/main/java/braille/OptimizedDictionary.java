package braille;

import java.util.*;

public class OptimizedDictionary {
    private final Set<String> vocabulary = new HashSet<>();
    private final Map<String, Integer> wordFrequency = new HashMap<>();
    private int totalWords = 0;

    public void loadWords(List<String> words) {
        for (String word : words) {
            word = word.trim().toLowerCase();
            if (!word.isEmpty()) {
                vocabulary.add(word);
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                totalWords++;
            }
        }
    }

    public String autocorrect(String input) {
        input = input.toLowerCase();
        if (vocabulary.contains(input)) return input;

        // 1. Priority: Unjumbled or one-letter changed words with same length
        List<String> candidates = new ArrayList<>();
        for (String word : vocabulary) {
            if (word.length() == input.length()) {
                if (isUnjumbledVersion(input, word) || isOneLetterChanged(input, word)) {
                    candidates.add(word);
                }
            }
        }

        if (!candidates.isEmpty()) {
            return getMostFrequent(candidates);
        }

        // 2. Fallback: Find best word by Levenshtein distance
        String bestMatch = null;
        int bestDistance = Integer.MAX_VALUE;

        for (String word : vocabulary) {
            int dist = levenshtein(input, word);
            if (dist < bestDistance) {
                bestDistance = dist;
                bestMatch = word;
            } else if (dist == bestDistance && frequency(word) > frequency(bestMatch)) {
                bestMatch = word;
            }
        }

        return bestMatch != null ? bestMatch : input;
    }

    private boolean isUnjumbledVersion(String a, String b) {
        char[] x = a.toCharArray();
        char[] y = b.toCharArray();
        Arrays.sort(x);
        Arrays.sort(y);
        return Arrays.equals(x, y);
    }

    private boolean isOneLetterChanged(String a, String b) {
        if (a.length() != b.length()) return false;
        int diff = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) diff++;
            if (diff > 1) return false;
        }
        return diff == 1;
    }

    private int levenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else if (a.charAt(i - 1) == b.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1]));
            }
        }
        return dp[a.length()][b.length()];
    }

    private String getMostFrequent(List<String> words) {
        return words.stream().max(Comparator.comparingInt(this::frequency)).orElse(words.get(0));
    }

    private int frequency(String word) {
        return wordFrequency.getOrDefault(word, 0);
    }
}
