import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * @author Maxi Agrippa
 */
public class Main {

    public static void main(String[] args) throws IOException {
        //WebCrawler.TraversalLinks("https://www.pepsi.com/");
        //WebCrawler.TraversalLinksInsideWebsite("https://www.pepsi.com/");
        //WebCrawler.TraversalLinksInsideWebsite("https://mkyong.com/java");

        // Testing method invertedIndex.getOccurrencesForSingleKey(...)
        InvertedIndex invertedIndex = InvertedIndex.getInstance();
        List<String> urls = invertedIndex.getUrlList();
        System.out.println("URL list size: " + urls.size());
        List<Integer> resultList = invertedIndex.getOccurrencesForSingleKey("smartphone");
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

        // BBC: 100 loops, got around 15 useful pages
        // WebCrawler.TraversalLinksWithKeyWord("https://www.bbc.com/news/technology", "www.bbc.com/news/technology");

        // C-NET: 100 loops, 60 pages
        // WebCrawler.TraversalLinksWithKeyWord("https://www.cnet.com/news/", "www.cnet.com/news");

        // TechNewsWorld: 100 loops, 99 pages
        // WebCrawler.TraversalLinksWithKeyWord("https://www.technewsworld.com/", "www.technewsworld.com/story");

        // Mashable: doesn't get anything (robots.txt?)
        // WebCrawler.TraversalLinksWithKeyWord("https://mashable.com", "mashable.com/article/");

        // TechCrunch: 100 loops, 65 pages
        // WebCrawler.TraversalLinksWithKeyWord("https://techcrunch.com", "techcrunch.com/2020");

        // The Verge: 100 loops, 45 pages
        // WebCrawler.TraversalLinksWithKeyWord("https://www.theverge.com", "www.theverge.com/2020");

        // Engadget: 100 loops, 40 pages (lots of duplicates, hard to fix)
        // WebCrawler.TraversalLinksWithKeyWord("https://www.engadget.com", "www.engadget.com/2020");

        //WebCrawler.TraversalLinksInsideWebsite("https://www.coca-cola.ca/");
        //WebCrawler.TraversalLinksWithKeyWord("https://en.coca-colaarabia.com", "coca");
        //WebCrawler.TraversalLinksInsideWebsite("https://mkyong.com");
        //Database.getInstance().ShowUrlTextTable();
        //SearchInDatabase.getInstance().SearchKeyWordInUrlTextTable("location");
    }
}