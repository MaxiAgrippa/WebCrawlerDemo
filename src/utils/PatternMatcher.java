package utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Maxi Agrippa
 */
public final class PatternMatcher
{
    // URL regex pattern
    public static Pattern URLPattern = Pattern.compile("((((https?|ftps?|gopher|telnet|nntp):\\/\\/)|(mailto:|news:))(%[0-9A-Fa-f]{2}|[-()_.!~*';/?#:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:|:blank:]])?");
    // Email regex pattern
    public static Pattern EmailPattern = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z-.]+");

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
        ArrayList<String> urls = new ArrayList<>();
        // claim the matcher and match the text
        Matcher urlMatcher = URLPattern.matcher(text);
        // put every url it find into result set
        while (urlMatcher.find())
        {
            urls.add(urlMatcher.group());
        }
        return urls;
    }
}
