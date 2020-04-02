package functionalities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.PatternMatcher;

/**
 * @author Maxi Agrippa
 */
// Singleton Class
public class HtmlToText
{
    // Instance
    private static final HtmlToText HTML_TO_TEXT = new HtmlToText();

    // Singleton Default Constructor. Don't let anyone initialize this
    private HtmlToText ()
    {

    }

    // Get Singleton Instance
    public static HtmlToText getInstance ()
    {
        return HTML_TO_TEXT;
    }

    // Get an html page and return text
    public String HtmlToText (String URL)
    {
        String result = "";
        // regulated input url
        String regulatedURL = URL;
        // check if the URL is legitimate
        if (!URL.matches(PatternMatcher.URLPattern.pattern()))
        {
            // see if it can pass with a "/"
            regulatedURL = URL + "/";
            if (!regulatedURL.matches(PatternMatcher.URLPattern.pattern()))
            {
                // if the URL is not legit, throw Illegal Argument Exception
                throw new IllegalArgumentException("The input to traversalLinks() is not an url");
            }
        }
        // claim the document object we will use to transact the content we get.
        Document document = null;
        try
        {
            // get the page
            document = Jsoup.connect(regulatedURL).get();
            // if there is a page
            if (document != null)
            {
                // extract the text content.
                result = document.text();
            }
        } catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            System.out.println(exception.getStackTrace());
        }
        return result;
    }

    public static void main (String[] args)
    {
        HtmlToText htmlToText=HtmlToText.getInstance();
        System.out.println(htmlToText.HtmlToText("https://www.bbc.com"));
    }
}
