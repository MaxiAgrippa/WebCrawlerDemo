package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternMatchingUtils {

    /**
     * Method to find all offsets for a list of patterns in a text using Brute Force, Boyer-Moore or KMP algorithm
     *
     * @param patterns the patterns to be found
     * @param text the text to be searched
     * @param type the algorithm to be used for the search
     * @return a map with a list of offsets for each one of the patterns
     */
    public static Map<String, List<Integer>> getPatternsOffsetsInText(String[] patterns, String text, String type) {
        Map<String, List<Integer>> offsets = new HashMap<>();
        for (String pattern : patterns) {
            List<Integer> patternOffsets = getSinglePatternOffsetsInText(pattern, text, type);
            offsets.put(pattern, patternOffsets);
        }
        return offsets;
    }

    /**
     * Method to find all offsets for a single pattern in a text using Brute Force, Boyer-Moore or KMP algorithm
     *
     * @param pattern the pattern to be found
     * @param text the text to be searched
     * @param type the algorithm to be used for the search
     * @return a list of offsets for the pattern
     */
    public static List<Integer> getSinglePatternOffsetsInText(String pattern, String text, String type) {
        String tempText = text;
        int tempLength = tempText.length();
        int textLength = text.length();
        List<Integer> patternOffsets = new ArrayList<>();
        BoyerMoore boyerMoore = new BoyerMoore(pattern);
        KMP kmp = KMP.getInstance();
        int i = 0;
        while (i < textLength) {
            int offset = getOffsetForType(pattern, tempText, type, kmp, boyerMoore);
            if (offset + i < textLength) {
                patternOffsets.add(offset + i);
            }
            if (offset < tempLength) {
                tempText = tempText.substring(offset + 1);
                tempLength = tempText.length();
            }
            i = i + offset + 1;
        }
        return patternOffsets;
    }

    private static int getOffsetForType(String pattern, String tempText, String type, KMP kmp, BoyerMoore boyerMoore) {
        if (type.equalsIgnoreCase(PatternMatchingEnum.BRUTE_FORCE.value)) {
            return BruteForceMatch.search1(pattern, tempText);
        } else if (type.equalsIgnoreCase(PatternMatchingEnum.BOYER_MOORE.value)) {
            return boyerMoore.search(tempText);
        } else {
            return KMP.KMPSearch(pattern, tempText);
        }
    }
}
