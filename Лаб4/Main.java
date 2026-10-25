
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.nio.charset.Charset;

public class Main {

    private static final int TOP_WORDS = 30;
    private static final Path INPUT_FILE =
        Path.of("Лаб4", "txt", "harry.txt");

    private static final Comparator<Map.Entry<String, Integer>>
            RANKING_ORDER =
            Comparator.<Map.Entry<String, Integer>>
                    comparingInt(Map.Entry::getValue)
                    .reversed()
                    .thenComparing(Map.Entry::getKey);

    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        Map<String, Integer> frequencies = countWords();
        List<Map.Entry<String, Integer>> topWords =
                selectTopWords(frequencies);

        long elapsedTime = System.nanoTime() - startTime;

        printResults(topWords);

        System.out.println("------");
        System.out.printf("Execution time: %.3f ms%n",
                elapsedTime / 1_000_000.0);
    }

    private static Map<String, Integer> countWords()
            throws IOException {

        Map<String, Integer> frequencies = new HashMap<>();

    try (BufferedReader reader = Files.newBufferedReader(
            INPUT_FILE, Charset.forName("windows-1252")))  {

            String line;
            StringBuilder word = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char ch = line.charAt(i);

                    if (ch >= 'A' && ch <= 'Z') {
                        word.append((char) (ch + ('a' - 'A')));
                    } else if (ch >= 'a' && ch <= 'z') {
                        word.append(ch);
                    } else {
                        addWord(frequencies, word);
                    }
                }

                addWord(frequencies, word);
            }
        }

        return frequencies;
    }

    private static void addWord(
            Map<String, Integer> frequencies,
            StringBuilder word) {

        if (word.length() == 0) {
            return;
        }

        String value = word.toString();
        frequencies.merge(value, 1, Integer::sum);
        word.setLength(0);
    }

    private static List<Map.Entry<String, Integer>>
            selectTopWords(
                    Map<String, Integer> frequencies) {

        PriorityQueue<Map.Entry<String, Integer>> queue =
                new PriorityQueue<>(RANKING_ORDER.reversed());

        for (Map.Entry<String, Integer> entry
                : frequencies.entrySet()) {

            if (queue.size() < TOP_WORDS) {
                queue.offer(Map.entry(
                        entry.getKey(), entry.getValue()));
            } else if (RANKING_ORDER.compare(
                    entry, queue.peek()) < 0) {

                queue.poll();
                queue.offer(Map.entry(
                        entry.getKey(), entry.getValue()));
            }
        }

        List<Map.Entry<String, Integer>> result =
                new ArrayList<>(queue);

        result.sort(RANKING_ORDER);
        return result;
    }

    private static void printResults(
            List<Map.Entry<String, Integer>> words) {

        for (Map.Entry<String, Integer> entry : words) {
            System.out.println(
                    entry.getKey() + " " + entry.getValue());
        }
    }
}
