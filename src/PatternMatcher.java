import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Maxi Agrippa
 */
public final class PatternMatcher
{
    // URL regex pattern
    private static Pattern URLPattern = Pattern.compile("(?:^|[\\W])" + "(((http|https|ftp|file):(\\/\\/)?))" + "(([\\w\\-\\_%]+(@))?)" + "(([\\w\\-\\_%]+\\.)+)" + "([\\w\\-\\_%]+)" + "((:[\\d]{2,5})?)" + "((\\/[\\w\\-\\_\\%]+)*)" + "((\\/(#|\\?)[\\w\\-\\.\\_\\~\\:\\/\\?\\#\\[\\]\\@\\!\\$\\&\\'\\(\\)\\*\\+\\,\\;\\=\\.]+)?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    // Singleton Mode
    private static PatternMatcher patternMatcher = new PatternMatcher();

    // Singleton Mode, Don't let anyone implement this
    private PatternMatcher ()
    {
    }

    // Singleton Mode, Get Instance
    public static PatternMatcher getInstance ()
    {
        return patternMatcher;
    }

    /**
     * Find urls in a String
     *
     * @param text (String)
     * @return ArrayList<String>
     */
    public static ArrayList<String> GetUrlsFromString (String text)
    {
        // claim the result set
        ArrayList<String> urls = new ArrayList<String>();
        // claim the matcher and match the text
        Matcher URLmatcher = URLPattern.matcher(text);
        // put every url it find into result set
        while (URLmatcher.find())
        {
            urls.add(URLmatcher.group());
        }
        return urls;
    }

}
