package functionalities;

import utils.IndexOccurrencePair;
import utils.InvertedIndex;

import java.util.*;

public class WebSearchEngine {

    private static final int NUMBER_OF_PAGES_TO_RANK = 10;

    /**
     * Method that returns a Map with the top "x" URLs in terms of occurrence of a specific keyword.
     * @param key the keyword to be evaluated
     * @return a map with the top URLs and number of occurrences for each one
     */
    public static Map<String, Integer> rankTopPagesByKeyword(String key) {
        Map<String, Integer> result = new LinkedHashMap<>();
        //  Create an instance of InvertedIndex
        InvertedIndex invertedIndex = InvertedIndex.getInstance();
        // Get the list of all URLs
        List<String> urls = invertedIndex.getUrlList();
        // Get the list of occurrences of the keyword for each URL
        List<Integer> resultList = invertedIndex.getOccurrencesForSingleKey(key);
        // Need to check if list is not empty (keyword found in the TST)
        if (!resultList.isEmpty()) {
            // This Pair object is used to keep the original index after the list of occurrences is sorted
            List<IndexOccurrencePair> indexOccurrencePairs = new ArrayList<>();
            for (int i = 0; i < resultList.size(); i++) {
                IndexOccurrencePair pair = new IndexOccurrencePair(i, resultList.get(i));
                indexOccurrencePairs.add(pair);
            }
            // Sort the pairs list
            Collections.sort(indexOccurrencePairs);
            // Add the first 10 pages (with more occurrences) to the Map
            for (int i = 0; i < NUMBER_OF_PAGES_TO_RANK; i++) {
                IndexOccurrencePair pair = indexOccurrencePairs.get(i);
                result.put(urls.get(pair.index), pair.occurrences);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Map<String, Integer> resultMap = rankTopPagesByKeyword("macbook");
        Iterator<Map.Entry<String, Integer>> iterator = resultMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String url = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("URL: " + url + " | Occurrences: " + value);
        }

        // Counter for the total of occurrences (but to do that, all the entries in the original resultList must be queried.
//        int count = 0;
//        for (Integer integer : resultList) {
//            count = count + integer;
//        }
//        System.out.println("Occurrences count: " + count);
//        // Using it for a ranking
//        for (String url : urls) {
//            System.out.println("URL: " + url + ", occurrences: " + resultList.get(urls.indexOf(url)));
//        }
    }
}
