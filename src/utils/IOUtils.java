package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper class for IO functions
 *
 * @author dlacher
 */
public class IOUtils {

    /**
     * A method to save HTML content as a text file
     * @param url the URL to be saved
     * @param htmlContent the HTML content to be parsed and saved
     */
    public static void saveTextAsTextFile(String url, String htmlContent) {

        // A list with "stop-characters", i.e., URLs with these characters won't be saved
        List<String> stopCharacters = new ArrayList<>(
                Arrays.asList("#", "?", "&", "!", "%", "$", "=", ";", "§", "*", "+")
        );

        // Only process a URL if it doesn't contain any stop-character
        if (stopCharacters.parallelStream().noneMatch(url::contains)) {
            try {
                // Create the name of the file
                String fileName = "./static/text/" + url.replaceAll("(https?)?(www)?\\W*", "") + ".txt";
                // Parse the HTML content
                Document document = Jsoup.parse(htmlContent);
                // Write two lines in the file, one for URL, another for the text
                FileWriter fw = new FileWriter(fileName);
                String newLine = System.getProperty("line.separator");
                fw.write(url + newLine);
                fw.write(document.text());
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
