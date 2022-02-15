package Mode7;

import mayflower.Actor;
import mayflower.World;

import mayflower.*;
import mayflower.util.FastTrig;

public class ScaleWorld extends World {

    private ImgObj[][] tiles;
    private Camera camera;
    private int counter;

    long[] pixels = new long[Runner.HEIGHT*Runner.WIDTH];

    private double cx;
    private double cy;
    private double cz;
    private double angle;

    private double ddx;
    private double ddy;

    private int scalar;

    private boolean changed;

    private Camera2 example;

    private static final MayflowerImage SKY = new MayflowerImage("images/sky.png");

    ScaleWorld() {
        camera = new Camera(this);
        cx = (8 * 64) / 2.0;
        cy = (8 * 64) / 2.0;
        addObject(camera, (int)cx, (int)cy);
        addObject(new ImgObj("path"),Runner.WIDTH,0);
        example = new Camera2();
        addObject(example,(int)cx + Runner.WIDTH,(int)cy);
        counter = 0;

        cz =  Runner.HEIGHT/2.0 * -1;

        setBackground("images/path.png");
        refill();
        angle = 0;

        //Default: 64
        scalar = 64;
        generate();
        changed = false;
    }

    private void refill()
    {
        for (int x = 0; x < Runner.WIDTH; x++) {
            for (int y = 0; y < Runner.HEIGHT; y++) {
                Color c = getColorAt(x, y);
                short b = (short) c.getBlue();
                short r = (short) c.getRed();
                short g = (short) c.getGreen();

                long vector = RGB.getVector(r, g, b);
                pixels[y * Runner.WIDTH + x] = vector;
            }
        }
    }

    public void generate()
    {

        double dx, dy;

        double z =  cz;

        double scaleX = scalar;
        double scaleY = scalar;

        double cos = FastTrig.cos(Math.toRadians(angle + 45));
        double sin = FastTrig.sin(Math.toRadians(angle + 45));

        for(int y = 0; y < Runner.HEIGHT; y++) {

            for (int x = 0; x < Runner.WIDTH; x++) {


                if (y <= Runner.HEIGHT / 2) {
                    getBackground().setColorAt(x, y, SKY.getColorAt(x,y));
                    continue;
                }


                //dy = ((Mode7.Runner.WIDTH - (x - cx)) * cos - (x - cx) * sin) / z;
                dx = ((Runner.WIDTH - (x)) * sin + (x) * cos) / z;
                dy = ((Runner.WIDTH - (x)) * cos - (x) * sin) / z;
                if (dy < 0)
                    dy = Runner.HEIGHT + dy;
                dy *= scaleY;
                dy += cy;
                dy %= Runner.HEIGHT;

                //dx = ((Mode7.Runner.WIDTH - (y - cy)) * sin + (y - cy) * cos) / z;
                if (dx < 0)
                    dx = Runner.WIDTH + dx;
                dx *= scaleX;
                dx += cx;
                dx %= Runner.WIDTH;

                long p = getPixel((int) dx, (int) dy);

                double r;
                double g;
                double b;

                //TRUE = FOG, FALSE = NO FOG
                if(false) {
                    double absZ = 2500 / Math.abs(z);
                    r = RGB.getR(p) - absZ;
                    r = r < 0 ? 0 : r;
                    g = RGB.getG(p) - absZ;
                    g = g < 0 ? 0 : g;
                    b = RGB.getB(p) - absZ;
                    b = b < 0 ? 0 : b;
                }
                else {
                    r = RGB.getR(p);
                    g = RGB.getG(p);
                    b = RGB.getB(p);
                }
                getBackground().setColorAt(x, y, new Color((int) r, (int) g, (int) b));
            }
            z++;
        }
    }

    public void translate(double dx, double dy)
    {
        cx += dx;
        cy += dy;

        ddx = dx;
        ddy = dy;

        if(cx < 0)
        {
            cx = Runner.WIDTH + cx;
        }
        if(cy < 0)
        {
            cy = Runner.HEIGHT + cy;
        }
        cx %= Runner.WIDTH;
        cy %= Runner.HEIGHT;
        changed = true;
    }

    public void move(Mode7.Direction d)
    {
        double rad = Math.toRadians(-angle);
        if(d == Direction.FORWARD)
        {
            double dx = 3.0 * FastTrig.cos(rad);
            double dy = 3.0 * FastTrig.sin(rad);
            translate(dx,dy);
        }
        else{
            double dx = -3 * FastTrig.cos(rad);
            double dy = -3 * FastTrig.sin(rad);
            translate(dx,dy);
        }
    }

    public void rotate(int da)
    {
        angle += da;
        if(angle > 360)
        {
            angle %= 360;
        }
        if(angle < 0)
        {
            angle = 360;
        }
        changed = true;
    }

    public void addObject(Actor a, int x, int y, int z)
    {
        int dx = x/z;
        int dy = y/z;
        addObject(a,dx,dy);
    }

    public void act(){
        //rotate();
        if(changed) {
            generate();
            changed = false;

            example.setRotation((int)-angle);
            example.setLocation(cx + Runner.WIDTH,cy);
        }

        if(Mayflower.isKeyPressed(Keyboard.KEY_SPACE))
        {
            System.out.printf("%.2f: (%d,%d,%d) + (%.3f,%.3f)\n",angle,(int)cx,(int)cy,(int)cz,ddx,ddy);
        }
    }

    public long getPixel(int x, int y)
    {
        return pixels[Math.abs((y * Runner.WIDTH + x)) % pixels.length];
    }
}
