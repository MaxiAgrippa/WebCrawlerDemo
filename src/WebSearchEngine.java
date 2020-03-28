import java.util.List;

public class WebSearchEngine {

    public static void main(String[] args) {
        // Testing method invertedIndex.getOccurrencesForSingleKey(...)
        InvertedIndex invertedIndex = InvertedIndex.getInstance();
        List<String> urls = invertedIndex.getUrlList();
        System.out.println("URL list size: " + urls.size());
        List<Integer> resultList = invertedIndex.getOccurrencesForSingleKey("macbook");
        System.out.println("Occurrences list size: " + resultList.size());
        int count = 0;
        for (Integer integer : resultList) {
            count = count + integer;
        }
        System.out.println(count);
        // Using it for a ranking
        for (String url : urls) {
            System.out.println("URL: " + url + ", occurrences: " + resultList.get(urls.indexOf(url)));
        }
        // TODO Use a PAIR? https://stackoverflow.com/questions/23587314/how-to-sort-an-array-and-keep-track-of-the-index-in-java
    }
}
