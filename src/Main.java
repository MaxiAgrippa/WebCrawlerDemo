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

//        JFrame jFrame = new JFrame("WebCrawler");
//        jFrame.setContentPane(new WebCrawlerWindow().rootPanel);
//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jFrame.pack();
//        jFrame.setVisible(true);
    }
}