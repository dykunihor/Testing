import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final int TOP_WORDS = 30;
    private static final Path INPUT_FILE = Path.of("src/txt/harry.txt");

    public static void main(String[] args) throws IOException {
        long startMemory = getUsedMemory();
        long startTime = System.nanoTime();

        Map<String, Integer> frequencies = countWords();

        long finishTime = System.nanoTime();
        long finishMemory = getUsedMemory();

        printTopWords(frequencies);

        System.out.println("------");
        System.out.println("Execution time: "
                + (finishTime - startTime) / 1_000_000 + " ms");

        System.out.println("Memory used: "
                + Math.max(0, finishMemory - startMemory) / 1024 + " KB");
    }

    private static Map<String, Integer> countWords() throws IOException {
        Map<String, Integer> frequencies = new HashMap<>();

        try (var lines = Files.lines(INPUT_FILE, StandardCharsets.UTF_8)) {
            lines.forEach(line -> {
                String normalizedLine = line
                        .replaceAll("[^A-Za-z ]", " ")
                        .toLowerCase();

                String[] words = normalizedLine
                        .trim()
                        .split("\\s+");

                for (String word : words) {
                    if (!word.isEmpty()) {
                        frequencies.merge(word, 1, Integer::sum);
                    }
                }
            });
        }

        return frequencies;
    }

    private static void printTopWords(Map<String, Integer> frequencies) {
        frequencies.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(
                        Comparator.reverseOrder()))
                .limit(TOP_WORDS)
                .forEach(entry ->
                        System.out.println(
                                entry.getKey() + " " + entry.getValue()));
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}