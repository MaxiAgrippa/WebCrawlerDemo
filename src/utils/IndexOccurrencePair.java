package utils;

public class IndexOccurrencePair implements Comparable<IndexOccurrencePair> {
    public final int index;
    public final int occurrences;

    public IndexOccurrencePair(int index, int occurrences) {
        this.index = index;
        this.occurrences = occurrences;
    }

    @Override
    public int compareTo(IndexOccurrencePair other) {
        //multiplied to -1 as it needs descending sort order
        return -1 * Integer.valueOf(this.occurrences).compareTo(other.occurrences);
    }
}
