import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.KMP;
import utils.Pair;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Maxi Agrippa
 */
public final class SearchInDatabase {
    // Singleton mode
    private static SearchInDatabase searchInDatabase = new SearchInDatabase();
    // Store the UrlTextTable
    private ArrayList<String[]> UrlTextTable;
    // Database
    private Database database = Database.getInstance();

    // Singleton Mode, Don't let anyone implement this
    private SearchInDatabase() {
        UrlTextTable = new ArrayList<String[]>();
    }

    // Singleton Mode, Get Instance
    public static SearchInDatabase getInstance() {
        return searchInDatabase;
    }

    // Search keyWord in UrlTextTable, the most related result(appearing most often) will have smallest index.
    public ArrayList<String> SearchKeyWordInUrlTextTable(String keyWord) {
        //FORTEST:
        System.out.println("SearchKeyWordInUrlTextTable");
        // result set
        ArrayList<String> result = new ArrayList<>();
        // Sort list
        ArrayList<Pair> sortList = new ArrayList<>();
        // Get UrlTextTable Data
        UrlTextTable = database.SelectAllFromUrlTextTable();
        // Claim a Document
        Document document;
        // Store Content
        String content;
        // Store Occurrences
        int occurrences;
        // Foreach row of data in UrlTextTable
        for (String[] data : UrlTextTable) {
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
        for (Pair pair : sortList) {
            result.add(pair.getString());
            //FORTEST:
            System.out.println(pair.getString());
        }
        return result;
    }

    private int SearchOccurrencesOfWord(String word, String content) {
        // Count occurrences, using both lower case to achieve "vague" match.
        return KMP.KMPSearch(word.toLowerCase(), content.toLowerCase());
    }
}
