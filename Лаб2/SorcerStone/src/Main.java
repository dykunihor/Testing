import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final int TOP_WORDS = 30;
    private static final Path INPUT_FILE = Path.of("src/txt/harry.txt");

    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        String content = readFile();
        String[] words = extractWords(content);
        Map<String, Integer> frequencies = countWords(words);

        printTopWords(frequencies);

        long finishTime = System.nanoTime();

        System.out.println("------");
        System.out.println((finishTime - startTime) / 1_000_000);
    }

    private static String readFile() throws IOException {
        return Files.readString(INPUT_FILE, StandardCharsets.UTF_8);
    }

    private static String[] extractWords(String content) {
        String normalizedContent = content
                .replaceAll("[^A-Za-z ]", " ")
                .toLowerCase();

        return normalizedContent
                .trim()
                .split("\\s+");
    }

    private static Map<String, Integer> countWords(String[] words) {
        Map<String, Integer> frequencies = new HashMap<>();

        for (String word : words) {
            frequencies.merge(word, 1, Integer::sum);
        }

        return frequencies;
    }

    private static void printTopWords(Map<String, Integer> frequencies) {
        frequencies.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(TOP_WORDS)
                .forEach(entry ->
                        System.out.println(entry.getKey() + " " + entry.getValue()));
    }
}