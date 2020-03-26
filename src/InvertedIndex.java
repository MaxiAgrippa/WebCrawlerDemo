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

    // The Trie structure that contains all the words
    private static TST<Integer> tst = buildTSTFromTextFiles();

    // A map with the list of occurrences by word
    private static Map<Integer, List<Integer>> occurrencesMap = buildOccurrencesMapFromTextFiles();

    // A list with all the URLs
    private static List<String> urlList = new ArrayList<>();

    /**
     * Method to retrieve all occurrences for a single key
     * @param key the key to find in the TST
     * @return the list of occurrences
     */
    public static List<Integer> getOccurrencesForSingleKey(String key) {
        // TODO Test this method
        if (tst.contains(key)) {
            return occurrencesMap.get(tst.get(key));
        } else {
            // Key not found, simply return an empty list back to the caller
            return new ArrayList<>();
        }
    }

    /**
     * Getter for the map of all occurrences
     * @return the map of occurrences
     */
    public static Map<Integer, List<Integer>> getOccurrencesMap() {
        return occurrencesMap;
    }

    /**
     * Getter for the list of URLs
     * @return the list of URLs
     */
    public static List<String> getUrlList() {
        return urlList;
    }

    private static Map<Integer, List<Integer>> buildOccurrencesMapFromTextFiles() {
        Map<Integer, List<Integer>> occurrencesMapTemp = new HashMap<>();
        // Get keys from TST
        List<String> keysList = StreamSupport.stream(tst.keys().spliterator(), false)
                .collect(Collectors.toList());
        // Initialize map with all keys (indexes for each word) and an empty list
        for (String key : keysList) {
            List<Integer> list = new ArrayList<>();
            occurrencesMapTemp.put(tst.get(key), list);
        }
        // This stream gets each one of the TEXT files for processing from (./static/text/)
        try (Stream<Path> stream = Files.walk(Paths.get("./static/text/"))) {
            stream.filter(Files::isRegularFile).forEach(path -> {
                try {
                    // Get text from file
                    String content = Files.readAllLines(path).get(1);

                    // Iterate through the keys and find occurrences in that file for each one of the keys
                    for (String key : keysList) {
                        // Get list of offsets (occurrences) for a specific key
                        List<Integer> keyOffsets =
                                PatternMatchingUtils.getSinglePatternOffsetsInText(key,
                                        content,
                                        PatternMatchingEnum.BOYER_MOORE.value);
                        // Retrieve the list of occurrences from the map, for that key
                        List<Integer> keyOccurrences = occurrencesMapTemp.get(tst.get(key));
                        // Add the number of occurrences to this list
                        keyOccurrences.add(keyOffsets.size());
                        // Put it back to the map (update the map)
                        occurrencesMapTemp.put(tst.get(key), keyOccurrences);
                        // System.out.println("Key: " + key + "\t\t\t\tValue: " + tst.get(key));
                    }
                } catch (IOException e) {
                    System.out.println("Something wrong has happened: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println("Something wrong has happened: " + e.getMessage());
        }
        return occurrencesMapTemp;
    }

    /**
     * A method that creates the TST based on each text file collected by the system
     * @return the TST with all the words/keys
     */
    private static TST<Integer> buildTSTFromTextFiles() {
        TST<Integer> tst = new TST<>();
        // This stream gets each one of the TEXT files for processing from (./static/text/)
        try (Stream<Path> stream = Files.walk(Paths.get("./static/text/"))) {
            stream.filter(Files::isRegularFile).forEach(path -> {
                try {
                    String url = Files.readAllLines(path).get(0);
                    urlList.add(url);
                    String content = Files.readAllLines(path).get(1);

                    // Use StringTokenizer to extract the content of the text into a list
                    List<String> tokens = Collections.list(
                            new StringTokenizer(
                                    content,
                                    "\"“”‘’/[]{}£€≈•…$!&§%=?*:+#@;,._() \t\n\r\f"))
                            .stream().map(token -> token.toString().toLowerCase()).collect(Collectors.toList());

                    // Insert list elements into the Trie
                    int tstSize = tst.size();
                    for (String key : tokens) {
                        if (!tst.contains(key)) {
                            tst.put(key, tstSize + tokens.indexOf(key));
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Something wrong has happened: " + e.getMessage());
                }
            });
            // TEST Display the content of TST
            // Getting the keys from the TST
            List<String> result = StreamSupport.stream(tst.keys().spliterator(), false)
                    .collect(Collectors.toList());
            for (String s : result) {
                System.out.println("Key: " + s + "\t\t\t\tValue: " + tst.get(s));
            }
        } catch (IOException e) {
            System.out.println("Something wrong has happened: " + e.getMessage());
        }
        return tst;
    }
}
