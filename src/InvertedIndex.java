import utils.PatternMatchingEnum;
import utils.PatternMatchingUtils;
import utils.TST;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Write a program that, using the Web pages given in the resources, it constructs an inverted index,
 * as explained in class. The words (keys) are stored in a trie. The value of each word is the index to a list of
 * occurrences of that word in each Web page.
 * You can keep the lists of occurrences in a two-dimensional array as explained in class
 */
public class InvertedIndex {

    // The single instance to be retrieved from other classes
    private static InvertedIndex instance;
    // The Trie structure that contains all the words
    private static TST<Integer> tst;
    // A map with the list of occurrences by word
    private static Map<Integer, List<Integer>> occurrencesMap;
    // A list with all the URLs
    private static List<String> urlList;
    // A list of "stop-words" that won't be included in the trie
    private static List<String> stopWords;

    private InvertedIndex() {
        // private constructor for singleton class
        stopWords = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "am", "an", "and", "any", "are", "aren't", "as", "at", "be", "but", "by", "can", "can't", "cannot", "co", "could", "couldn't", "do", "ed", "em", "en", "get", "got", "go", "have", "has", "had", "hasn't", "hadn't", "haven't", "he", "his", "her", "she", "it", "my", "your", "they", "their", "theirs", "hers", "you", "were", "was", "its", "if", "in", "whether", "is", "isn't", "me", "of", "off", "or", "our", "out", "the", "then", "them", "this", "that", "these", "those", "though", "thus", "to", "from", "for", "on", "over", "too", "what", "when", "where", "which", "while", "who", "whom", "whose", "why", "with", "without", "within", "should", "would", "shouldn't", "wouldn't", "yours"));
        urlList = new ArrayList<>();
        tst = buildTSTFromTextFiles();
        occurrencesMap = buildInitialEmptyOccurrencesMap();
    }

    // Method to return the single instance of the class
    public static InvertedIndex getInstance() {
        if (instance == null) {
            // if instance is null, initialize
            instance = new InvertedIndex();
        }
        return instance;
    }

    /**
     * Method to retrieve all occurrences for a single key
     *
     * @param key the key to find in the TST
     * @return the list of occurrences
     */
    public List<Integer> getOccurrencesForSingleKey(String key) {
        // Initially, make the key all lower case
        key = key.toLowerCase();
        // Only process the key if it is present in the TST data structure
        if (tst.contains(key)) {
            // This stream gets each one of the TEXT files for processing from (./static/text/)
            try (Stream<Path> stream = Files.walk(Paths.get("./static/text/"))) {
                List<Path> pathList = stream.filter(Files::isRegularFile).collect(Collectors.toList());
                for (Path path : pathList) {
                    try {
                        // Get text from the file
                        String content = "";
                        if (Files.readAllLines(path).size() > 1) {
                            content = Files.readAllLines(path).get(1);
                        } else {
                            System.out.println("File without two lines: " + path.getFileName());
                            continue;
                        }
                        // Check whether the key is present in the text and get the number of occurrences, if it is.
                        // Get list of offsets (occurrences) for the key
                        List<Integer> keyOffsets =
                                PatternMatchingUtils.getSinglePatternOffsetsInText(key,
                                        content.toLowerCase(),
                                        PatternMatchingEnum.BOYER_MOORE.value);
                        // Retrieve the list of occurrences from the map, for that key
                        List<Integer> keyOccurrences = occurrencesMap.get(tst.get(key));
                        // Add the number of occurrences to this list
                        keyOccurrences.add(keyOffsets.size());
                        // Put it back to the map (update the map)
                        occurrencesMap.put(tst.get(key), keyOccurrences);
                    } catch (IOException e) {
                        System.out.println("Something wrong has happened while building occurrences map (inside stream):");
                        System.out.println("File name: " + path.getFileName());
                        System.out.println(e.toString());
                    }
                }
            } catch (IOException e) {
                System.out.println("Something wrong has happened while building occurrences map (outside stream):");
                System.out.println(e.toString());
            }
            return occurrencesMap.get(tst.get(key));
        } else {
            // Key not found, simply return an empty list back to the caller
            return new ArrayList<>();
        }
    }

    /**
     * Getter for the map of occurrences
     *
     * @return the map of occurrences
     */
    public Map<Integer, List<Integer>> getOccurrencesMap() {
        return occurrencesMap;
    }

    /**
     * Getter for the list of URLs
     *
     * @return the list of URLs
     */
    public List<String> getUrlList() {
        return urlList;
    }

    private Map<Integer, List<Integer>> buildInitialEmptyOccurrencesMap() {
        Map<Integer, List<Integer>> occurrencesMapTemp = new HashMap<>();
        // Get keys from TST
        List<String> keysList = StreamSupport.stream(tst.keys().spliterator(), false)
                .collect(Collectors.toList());
        // Initialize map with all keys (indexes for each word) and an empty list
        for (String key : keysList) {
            List<Integer> list = new ArrayList<>();
            occurrencesMapTemp.put(tst.get(key), list);
        }
        return occurrencesMapTemp;
    }

    /**
     * A method that creates the TST based on each text file collected by the system
     *
     * @return the TST with all the words/keys
     */
    private TST<Integer> buildTSTFromTextFiles() {
        TST<Integer> tst = new TST<>();
        // A count variable to be used as the value in the trie (TST)
        int count = 0;
        // This stream gets each one of the TEXT files for processing from (./static/text/)
        try (Stream<Path> stream = Files.walk(Paths.get("./static/text/"))) {
            List<Path> pathList = stream.filter(Files::isRegularFile).collect(Collectors.toList());
            for (Path path : pathList) {
                try {
                    String content = "";
                    if (Files.readAllLines(path).size() > 1) {
                        String url = Files.readAllLines(path).get(0);
                        urlList.add(url);
                        content = Files.readAllLines(path).get(1);
                    } else {
                        System.out.println("File without two lines: " + path.getFileName());
                        continue;
                    }

                    // Use StringTokenizer to extract the content of the text into a list
                    List<String> tokens = Collections.list(
                            new StringTokenizer(
                                    content,
                                    "\"“”/[]{}£€≈•…$!&§%=?*:+#@;,._() \t\n\r\f"))
                            .stream().map(token -> token.toString().toLowerCase()).collect(Collectors.toList());

                    // Insert list elements into the Trie
                    for (String key : tokens) {
                        // Insert in the TST only if key is not there yet AND is not a stop-word
                        if (!tst.contains(key) && !stopWords.contains(key)) {
                            tst.put(key, count);
                            count++;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Something wrong has happened while building the TST (inside stream):");
                    System.out.println("File name: " + path.getFileName());
                    System.out.println(e.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Something wrong has happened while building the TST (outside stream):");
            System.out.println(e.toString());
        }
        return tst;
    }
}
