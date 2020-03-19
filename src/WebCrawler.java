import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.lang.model.util.Elements;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Maxi Agrippa
 */
// Singleton Class
public final class WebCrawler
{
    // Instance
    private static final WebCrawler WEB_CRAWLER = new WebCrawler();

    // FIXME: need to use Database to store visited link.
    private static HashSet<HashSet<String>> VisitedLinks = new HashSet<HashSet<String>>();
    // Using HashSet to store checked links.
    private static HashSet<String> checkedLinks;
    //
    private static Database database = Database.getInstance();


    // Default Constructor. Don't let anyone initialize this
    private WebCrawler ()
    {
        checkedLinks = new HashSet<String>();
    }

    // Get Singleton Instance
    public static WebCrawler getInstance ()
    {
        return WEB_CRAWLER;
    }

    // traversal every links inside the URL
    public static void TraversalLinks (String URL)
    {
        // Temporary store data.
        ArrayList<String[]> Data = new ArrayList<String[]>();
        // Log checked links in this function running time
        HashSet<String> localCheckedLinks = new HashSet<String>();
        // log links need to check
        ArrayList<String> linksOnOnePages = new ArrayList<String>();
        // Have we checked the link before?
        if (!checkedLinks.contains(URL))
        {
            try
            {
                // add the link to local link log if it not already contained in it
                if (!localCheckedLinks.contains(URL))
                {
                    if (localCheckedLinks.add(URL))
                    {
                        System.out.println("Local Checked Link Added: " + URL);
                    }
                }
                // put the link into unchecked group.
                linksOnOnePages.add(URL);
                // claim the document object we will use to transact the content we get.
                Document document = null;
                // control times of the loop
                int loopTimes = 100;
                // while there is more link to check, and we can run more loops.
                while (linksOnOnePages.size() > 0 && loopTimes > 0)
                {
                    System.out.println("Get into the loop");
                    // get the last element.
                    String url = linksOnOnePages.get(linksOnOnePages.size() - 1);
                    // remove it from the need to visit list.
                    linksOnOnePages.remove(linksOnOnePages.size() - 1);
                    // if this link is in side the checkedLinks,
                    if(checkedLinks.contains(url))
                    {
                        // break the loop, don't do anything with this link.
                        break;
                    }
                    // if this link is not in the checkedLinks, put it in since we gonna visit it now.
                    checkedLinks.add(url);
                    System.out.println("Global Checked Link Added: " + URL);
                    // get the Document by using Jsoup.
                    document = Jsoup.connect(url).get();
                    // Add value pair to Data <url(String), text(String)>
                    Data.add(new String[]{url, document.text()});
                    // put all the link we can find from that page to linksOnOnePages.
                    linksOnOnePages.addAll(PatternMatcher.GetUrlsFromString(document.text()));
                    // finish one loop.
                    loopTimes--;
                }
            } catch (IOException e)
            {
                System.out.println("TraversalLinks(), URL Part: " + e.getMessage());
            }

            // Try to put the result into Database
            try
            {
                // get every value pair from Data
                for (String[] stringArray : Data)
                {
                    database.InsertToUrlTextTable(stringArray[0], stringArray[1]);
                }
                // clean Data for next use.
                Data.clear();
            } catch (Exception e)
            {
                System.out.println("TraversalLinks(), Database Part: " + e.getMessage());
            }
        }
    }

}
