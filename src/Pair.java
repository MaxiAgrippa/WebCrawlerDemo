/**
 * @author Maxi Agrippa
 * <p>
 * Value Pair(int,String)
 */
public class Pair implements Comparable<Pair>
{
    public String getString ()
    {
        return string;
    }

    public void setString (String string)
    {
        this.string = string;
    }

    public int getInteger ()
    {
        return integer;
    }

    public void setInteger (int integer)
    {
        this.integer = integer;
    }

    private String string = "";
    private int integer = 0;

    public Pair (int integer, String string)
    {
        this.setInteger(integer);
        this.setString(string);
    }

    /**
     * Compare to Pair with int part.
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo (Pair o)
    {
        return (this.getInteger() - o.getInteger() > 0 ? 1 : this.getInteger() - o.getInteger() == 0 ? 0 : -1);
    }
}
