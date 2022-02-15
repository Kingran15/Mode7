package Mode7;

import mayflower.Mayflower;
import mayflower.World;

public class Runner extends Mayflower {

    static final int HEIGHT = 8 * 64;
    static final int WIDTH = 8 * 64;

    private Runner()
    {
        super("Mode7",WIDTH  *2,HEIGHT);
    }

    public void init()
    {
        World world = new ScaleWorld();

        Mayflower.setWorld(world);
    }

    public static void main(String[] args) {
        new Runner();
    }
}
