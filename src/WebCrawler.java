import java.util.HashSet;

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
    private HashSet<String> checkedLinks;

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
    public static void traversalLinks (String URL)
    {
        // TODO: traversal every links in an url
        // TODO: put every visited link in checked links(avoid duplicate)
        // TODO: update VisitedLinks using checkedLinks
    }

}
