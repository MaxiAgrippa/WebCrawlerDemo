package functionalities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.PatternMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;

/**
 * @author Maxi Agrippa
 */
public class FindingEmailAddressInAPage
{
    // Singleton
    private static FindingEmailAddressInAPage findingEmailAddressInAPage = new FindingEmailAddressInAPage();

    // Singleton
    public static FindingEmailAddressInAPage getInstance ()
    {
        return findingEmailAddressInAPage;
    }

    // User input
    public static String URL;

    // Using HashSet to store checked links.
    private static HashSet<String> checkedLinks;

    // Singleton Constructor. Don't let anyone implement it.
    private FindingEmailAddressInAPage ()
    {
        URL = "";
        checkedLinks = new HashSet<>();
    }

    public ArrayList<String> FindingEmailAddressInAPage (String URL)
    {
        // store result
        ArrayList<String> results = new ArrayList<String>();
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
        // Temporary store data.
        ArrayList<String[]> Data = new ArrayList<>();
        // log links need to check
        ArrayList<String> linksOnOnePages = new ArrayList<>();
        // Claim the link out side try, let it being able to be catch.
        String url = "";
        // Have we checked the link before?
        if (!checkedLinks.contains(regulatedURL))
        {
            try
            {
                // put the link into unchecked group.
                linksOnOnePages.add(regulatedURL);
                // Extract thw website key word from the URL. Used in search.l
                String Website = regulatedURL.replaceFirst("(((http|https|ftp|file):(\\/\\/)))" + "((www.|WWW.)?)", "");
                Website = Website.split("/")[0];
                // claim the document object we will use to transact the content we get.
                Document document = null;
                // control times of the loop
                int loopTimes = 10;
                // while there is more link to check, and we can run more loops.
                while (linksOnOnePages.size() > 0 && loopTimes > 0)
                {
                    // get the last element.
                    url = linksOnOnePages.get(linksOnOnePages.size() - 1);
                    // remove it from the need to visit list.
                    linksOnOnePages.remove(linksOnOnePages.size() - 1);
                    // if this link is in side the checkedLinks,
                    if (url.equals("") || checkedLinks.contains(url))
                    {
                        // continue the loop, don't do anything with this link.
                        continue;
                    }
                    // if this link is not in the checkedLinks, put it in since we gonna visit it now.
                    checkedLinks.add(url);
                    // FORTEST:
                    System.out.println("Global Checked Link Added: " + url);
                    // try to get the Document by using Jsoup.
                    try
                    {
                        document = Jsoup.connect(url).get();
                    } catch (IOException iOException)
                    {
                        // caused by non-html resource(.zip, .pdf...).
                        // ignore.
                        System.out.println("TraversalLinks(), URL Part: " + iOException.getMessage());
                        System.out.println(url);
                    }
                    if (document != null)
                    {
                        // Add value pair to Data <url(String), text(String)>
                        Data.add(new String[]{url, document.toString()});
                        // put all the links we can find from that page to linksOnOnePages.
                        Elements linksOnPage = document.select("a[href]");
                        // temporary store link.
                        String tempLink;
                        // foreach Element in side linksOnPage.
                        for (Element e : linksOnPage)
                        {
                            // get all the href node.
                            tempLink = e.attr("abs:href");
                            // store links that inside the Website in linksOnOnePages (ArrayList<String>)
                            if (tempLink.contains(Website))
                            {
                                linksOnOnePages.add(tempLink);
                            }
                        }
                    }
                    // finish one loop.
                    loopTimes--;
                }
            } catch (IllegalArgumentException illegalArgumentException)
            {
                System.out.println(url);
                System.out.println("TraversalLinks(), URL Part: " + illegalArgumentException.toString());
                illegalArgumentException.printStackTrace();
            }
        }

        for (String[] stringArray : Data)
        {
            Matcher matcher = PatternMatcher.EmailPattern.matcher(stringArray[1]);
            while (matcher.find())
            {
                results.add(matcher.group());
            }
        }
        return results;
    }

    public static void main (String[] args)
    {
        ArrayList<String> results = FindingEmailAddressInAPage.getInstance().FindingEmailAddressInAPage("https://www.bbc.com/news");
        for (String s : results)
        {
            System.out.println(s);
        }
    }

}
