package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class ConversionUtils {

    /**
     * This method receives an HTML File, parses it and returns only the text
     *
     * @param file the HTML file
     * @return formatted text
     */
    public static String getPlainTextFromHtmlFile(File file) {
        try {
            Document document = Jsoup.parse(file, "UTF-8", "");
            return document.text();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
