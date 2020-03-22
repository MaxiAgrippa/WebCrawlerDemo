/**
 * @author Maxi Agrippa
 */
class Main
{
    public static void main (String[] args)
    {
        //WebCrawler.TraversalLinks("https://www.pepsi.com/");
        //WebCrawler.TraversalLinksInsideWebsite("https://www.pepsi.com/");
        //WebCrawler.TraversalLinksInsideWebsite("https://mkyong.com/java");
        WebCrawler.TraversalLinksInsideWebsite("https://mkyong.com/java/");
        //WebCrawler.TraversalLinksInsideWebsite("https://mkyong.com");
        Database.getInstance().ShowUrlTextTable();
        //SearchInDatabase.getInstance().SearchKeyWordInUrlTextTable("jsoup");
    }
}