package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final String URL_REGEX = "((((https?|ftps?|gopher|telnet|nntp):\\/\\/)|(mailto:|news:))(%[0-9A-Fa-f]{2}|[-()_.!~*';/?#:@&=+$,A-Za-z0-9])+)([).!';/?:,][[:blank:|:blank:]])?";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    private static final String EMAIL_ADDRESS_REGEX =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(EMAIL_ADDRESS_REGEX);
    private static final String PHONE_NUMBER_REGEX =
            "1?[^A-Za-z0-9\\n/\\\\_.,:=-]*([2-9][0-8][0-9])[^A-Za-z0-9\\n/\\\\_.,:=]*([2-9][0-9]{2})[^A-Za-z0-9\\n/\\\\_.,:=]*([0-9]{4})(\\se?x?t?(\\d*))?";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    /**
     * A method that scans a text file and finds all occurrences of valid URLs in it
     * @param text the text to be scanned
     * @return a list with all the URLs
     */
    public static List<String> findAllUrlsInText(String text) {
        List<String> results = new ArrayList<>();
        Matcher urlMatcher = URL_PATTERN.matcher(text);
        while (urlMatcher.find()) {
            String s = urlMatcher.group();
            results.add(s);
        }
        return results;
    }

    /**
     * A method that scans a text file and finds all occurrences of valid emails in it
     * @param text the text to be scanned
     * @return a list with all the emails
     */
    public static List<String> findAllEmailAddressesInText(String text) {
        List<String> results = new ArrayList<>();
        Matcher emailMatcher = EMAIL_ADDRESS_PATTERN.matcher(text);
        while (emailMatcher.find()) {
            String s = emailMatcher.group();
            results.add(s);
        }
        return results;
    }

    /**
     * A method that scans a text file and finds all occurrences of valid phone numbers in it
     * @param text the text to be scanned
     * @return a list with all the phone numbers
     */
    public static List<String> findAllPhoneNumbersInText(String text) {
        List<String> results = new ArrayList<>();
        Matcher phoneMatcher = PHONE_NUMBER_PATTERN.matcher(text);
        while (phoneMatcher.find()) {
            String s = phoneMatcher.group();
            results.add(s);
        }
        return results;
    }

    /**
     * A method to validate the input as an email address
     * @param emailAddress the string to be validated
     * @return true (valid) or false (invalid)
     */
    public static boolean validateEmailAddress(String emailAddress) {
        Matcher matcher = EMAIL_ADDRESS_PATTERN.matcher(emailAddress);
        return matcher.find();
    }

    /**
     * A method to validate the input as a phone number from the US or Canada
     * @param phoneNumber the string to be validated
     * @return true (valid) or false (invalid)
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        return matcher.find();
    }

    /**
     * A method to validate the input as an URL
     * @param url the string to be validated
     * @return true (valid) or false (invalid)
     */
    public static boolean validateUrl(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.find();
    }
}
