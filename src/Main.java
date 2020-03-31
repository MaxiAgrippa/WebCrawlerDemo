import javax.swing.*;
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

        // BBC: 100 loops, got around 15 useful pages
        // WebCrawler.TraversalLinksWithKeyWord("https://www.bbc.com/news/technology", "www.bbc.com/news/technology");

        // C-NET: 100 loops, 60 pages
        // WebCrawler.TraversalLinksWithKeyWord("https://www.cnet.com/news/", "www.cnet.com/news");

        // TechNewsWorld: 100 loops, 99 pages
        // WebCrawler.TraversalLinksWithKeyWord("https://www.technewsworld.com/", "www.technewsworld.com/story");

        // Mashable: doesn't get anything (robots.txt?)
        // WebCrawler.TraversalLinksWithKeyWord("https://mashable.com", "mashable.com/article/");

        // CBC News Tech - https://www.cbc.ca/news/technology/epa-enforcement-covid-19-1.5512023
        // WebCrawler.TraversalLinksWithKeyWord("https://www.cbc.ca/news/technology", "www.cbc.ca/news/technology");

        // IT World Canada
        //WebCrawler.TraversalLinksWithKeyWord("https://www.itworldcanada.com/", "www.itworldcanada.com/article");

        // Canadian Business https://www.canadianbusiness.com/technology-news/apple-iphone-8-and-x-10-things-you-need-to-know/
        //WebCrawler.TraversalLinksWithKeyWord("https://www.canadianbusiness.com/technology-news/", "www.canadianbusiness.com/technology-news");

        // TechCrunch: 100 loops, 65 pages
        // WebCrawler.TraversalLinksWithKeyWord("https://techcrunch.com", "techcrunch.com/2020");

        // The Verge: 100 loops, 45 pages
        // WebCrawler.TraversalLinksWithKeyWord("https://www.theverge.com", "www.theverge.com/2020");
        // WebCrawler.TraversalLinksWithKeyWord("https://www.theverge.com/archives/tech/2019/1", "www.theverge.com/2019");

        // Engadget: 100 loops, 40 pages
        // WebCrawler.TraversalLinksWithKeyWord("https://www.engadget.com", "www.engadget.com/2020");

        //WebCrawler.TraversalLinksInsideWebsite("https://www.coca-cola.ca/");
        //WebCrawler.TraversalLinksWithKeyWord("https://en.coca-colaarabia.com", "coca");
        //WebCrawler.TraversalLinksInsideWebsite("https://mkyong.com");
        //Database.getInstance().ShowUrlTextTable();
        //SearchInDatabase.getInstance().SearchKeyWordInUrlTextTable("location");
    }
}