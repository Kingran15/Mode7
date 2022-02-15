package Mode7;

public class RGB {

    private static final int sixteenBits = (1<<16)-1;

    public static long getVector(short r, short g, short b)
    {
        return ((long)r << 32) + ((long)g << 16) + b;
    }

    public static short getR(long vector)
    {
        return (short)(vector >> 32);
    }

    public static short getG(long vector)
    {
        return (short)((vector>>16) & sixteenBits);
    }

    public static short getB(long vector)
    {
        return (short)(vector & sixteenBits);
    }
}
