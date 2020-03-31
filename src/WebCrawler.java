import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Maxi Agrippa
 */
// Singleton Class
public final class WebCrawler
{
    // Instance
    private static final WebCrawler WEB_CRAWLER = new WebCrawler();

    // Using HashSet to store checked links.
    private static HashSet<String> checkedLinks;
    // Sqlite Database interface
    private static Database database = Database.getInstance();


    // Singleton Default Constructor. Don't let anyone initialize this
    private WebCrawler ()
    {
        checkedLinks = new HashSet<>();
    }

    // Get Singleton Instance
    public static WebCrawler getInstance ()
    {
        return WEB_CRAWLER;
    }


    /**
     * Traversal every links from the URL
     *
     * @param URL the url of the website
     */
    public static void TraversalLinks (String URL)
    {
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
        if (!checkedLinks.contains(URL))
        {
            try
            {
                // put the link into unchecked group.
                linksOnOnePages.add(regulatedURL);
                // claim the document object we will use to transact the content we get.
                Document document = null;
                // control times of the loop
                int loopTimes = 100;
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
                        // store them in linksOnOnePages (ArrayList<String>)
                        for (Element e : linksOnPage)
                        {
                            linksOnOnePages.add(e.attr("abs:href"));
                        }
                    }
                    // finish one loop.
                    loopTimes--;
                }
            } catch (IllegalArgumentException illegalArgumentException)
            {
                System.out.println(url);
                System.out.println("TraversalLinks(), URL Part: " + illegalArgumentException.getMessage());
                illegalArgumentException.printStackTrace();
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

    /**
     * Traversal every links inside the website
     *
     * @param URL the URL to traverse
     */
    public static void TraversalLinksInsideWebsite (String URL)
    {
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
                int loopTimes = 100;
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

            // Try to put the result into Database
            try
            {
                // get every value pair from Data
                for (String[] stringArray : Data)
                {
                    // FIXME Is this the best alternative for embedded db?
                    IOUtils.saveTextAsTextFile(stringArray[0], stringArray[1]);
                    //database.InsertToUrlTextTable(stringArray[0], stringArray[1]); // 0 = URL, 1 = text
                }
                // clean Data for next use.
                Data.clear();
            } catch (Exception e)
            {
                System.out.println("TraversalLinks(), Database Part: " + e.getMessage());
            }
        }
    }

    /**
     * Traversal every links that contain the keyword
     *
     * @param URL     the URL to be traversed
     * @param KeyWord the keyword expression to be found in the URL
     */
    public static void TraversalLinksWithKeyWord (String URL, String KeyWord)
    {
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
                // claim the document object we will use to transact the content we get.
                Document document = null;
                // control times of the loop
                int loopTimes = 2000;
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
                            // store links that contain thw Keyword in linksOnOnePages (ArrayList<String>)
                            if (tempLink.contains(KeyWord))
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

            // Try to put the result into Database
            try
            {
                // get every value pair from Data
                for (String[] stringArray : Data)
                {
                    // Save as TEXT file
                    IOUtils.saveTextAsTextFile(stringArray[0], stringArray[1]);
                    // Save as HTML file
                    //IOUtils.saveTextAsHtmlFile(stringArray[0], stringArray[1]);
                    // Save in the embedded database
                    //database.InsertToUrlTextTable(stringArray[0], stringArray[1]); // 0 = URL, 1 = text
                }
                // clean Data for next use.
                Data.clear();
            } catch (Exception e)
            {
                System.out.println("TraversalLinks(), Database Part: " + e.getMessage());
            }
        }
    }

    /**
     * Traversal every links inside the website
     *
     * @param URL the URL to be traversed
     * @return results ArrayList<String>
     */
    public static ArrayList<String> TraversalLinksInsideWebsiteWithoutStore (String URL)
    {
        // Store result
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

            // put url into result set.
            for (String[] stringArray : Data)
            {
                results.add(stringArray[0]);
            }
            // clean Data for next use.
            Data.clear();
        }
        return results;
    }
}
