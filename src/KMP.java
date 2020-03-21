/**
 * @author Maxi Agrippa
 */
public final class KMP
{
    private static KMP kmp = new KMP();

    private KMP ()
    {
    }

    public static KMP getInstance ()
    {
        return kmp;
    }

    public static int KMPSearch (String pattern, String content)
    {
        // occurrences of pattern
        int occurrences = 0;
        int patternLength = pattern.length();
        int contentLength = content.length();

        // create longestPrefixSuffix[] that will hold the longest
        // prefix suffix values for pattern
        int longestPrefixSuffix[] = new int[patternLength];
        int patternIndex = 0; // index for pattern[]

        // Preprocess the pattern (calculate longestPrefixSuffix[]
        // array)
        computeLongestPrefixSuffixArray(pattern, patternLength, longestPrefixSuffix);

        int contentIndex = 0; // index for content[]
        while (contentIndex < contentLength)
        {
            if (pattern.charAt(patternIndex) == content.charAt(contentIndex))
            {
                patternIndex++;
                contentIndex++;
            }
            if (patternIndex == patternLength)
            {
                // find one
                occurrences++;
                //System.out.println("Found pattern " + "at index " + (contentIndex - patternIndex));
                patternIndex = longestPrefixSuffix[patternIndex - 1];
            }

            // mismatch after patternIndex matches
            else if (contentIndex < contentLength && pattern.charAt(patternIndex) != content.charAt(contentIndex))
            {
                // Do not match longestPrefixSuffix[0..longestPrefixSuffix[patternIndex-1]] characters,
                // they will match anyway
                if (patternIndex != 0) patternIndex = longestPrefixSuffix[patternIndex - 1];
                else contentIndex = contentIndex + 1;
            }
        }
        return occurrences;
    }

    private static void computeLongestPrefixSuffixArray (String pattern, int patternLength, int LongestPrefixSuffix[])
    {
        int length = 0;
        int i = 1;
        LongestPrefixSuffix[0] = 0;
        while (i < patternLength)
        {
            if (pattern.charAt(i) == pattern.charAt(length))
            {
                length++;
                LongestPrefixSuffix[i] = length;
                i++;
            }
            else
            {
                if (length != 0)
                {
                    length = LongestPrefixSuffix[length - 1];
                }
                else
                {
                    LongestPrefixSuffix[i] = length;
                    i++;
                }
            }
        }
    }
}
