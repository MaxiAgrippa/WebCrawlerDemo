import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * @author Maxi Agrippa
 */
public final class SearchInDatabase
{
    // Singleton mode
    private static SearchInDatabase searchInDatabase = new SearchInDatabase();
    // Store the UrlTextTable
    private ArrayList<String[]> UrlTextTable;
    // Database
    private Database database = Database.getInstance();

    // Singleton Mode, Don't let anyone implement this
    private SearchInDatabase ()
    {
        UrlTextTable = new ArrayList<String[]>();
    }

    // Singleton Mode, Get Instance
    public static SearchInDatabase getInstance ()
    {
        return searchInDatabase;
    }

    // Search keyWord in UrlTextTable, the most related result(appearing most often) will have smallest index.
    public ArrayList<String> SearchKeyWordInUrlTextTable (String keyWord)
    {
        //FORTEST:
        System.out.println("SearchKeyWordInUrlTextTable");
        // result set
        ArrayList<String> result = new ArrayList<String>();
        // Sort list
        ArrayList<Pair> sortList = new ArrayList<Pair>();
        // Get UrlTextTable Data
        UrlTextTable = database.SelectAllFromUrlTextTable();
        // Claim a Document
        Document document = null;
        // Store Content
        String content = "";
        // Store Occurrences
        int occurrences = 0;
        // Foreach row of data in UrlTextTable
        for (String[] data : UrlTextTable)
        {
            // implement a document object with the html string
            document = Jsoup.parse(data[2]);
            // get normalized content from it
            content = document.text();
            // calculate occurrence in each content.
            occurrences = SearchOccurrencesOfWord(keyWord, content);
            // store occurrence and content pair for sort.
            sortList.add(new Pair(occurrences, data[1]));
        }
        // sort the result.
        Collections.sort(sortList);
        // output result.
        for (int i = 0; i < sortList.size(); i++)
        {
            result.add(sortList.get(i).getString());
            //FORTEST:
            System.out.println(sortList.get(i).getString());
        }
        return result;
    }

    private int SearchOccurrencesOfWord (String word, String content)
    {
        // Process content
        String processedContent = content.replaceAll("([\\,\\.\\+\\-\\?\\:\\'\\\"\\(\\)\\[\\]\\{\\}\\;\\/\\<\\>\\=]+)", "");
        // Count occurrences
        int occurrences = 0;
        // Tokenizer a string
        StringTokenizer stringTokenizer = new StringTokenizer(processedContent);
        // while there is more tokens
        while (stringTokenizer.hasMoreTokens())
        {
            // Get next token.
            String temp = stringTokenizer.nextToken();
            // If the token equal to the word, occurrences add
            if (temp.equals(word))
            {
                occurrences += 1;
            }
        }
        return occurrences;
    }

}
