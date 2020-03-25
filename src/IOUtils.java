import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        try {
            // Remove everything after # in URL, if this special character is present
            if (url.contains("#") || url.contains("/#")) {
                url = url.substring(0, url.indexOf("#"));
            }
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
