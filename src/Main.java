/**
 * @author Maxi Agrippa
 */
class Main
{
    public static void main (String[] args)
    {
        //WebCrawler.TraversalLinksInsideWebsite("https://jsoup.org/");
        WebCrawler.TraversalLinksInsideWebsite("https://mkyong.com/");
        Database.getInstance().ShowUrlTextTable();
        SearchInDatabase.getInstance().SearchKeyWordInUrlTextTable("java");
    }
}