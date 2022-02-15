package Mode7;

import mayflower.Actor;
import mayflower.Keyboard;
import mayflower.Mayflower;

public class Camera extends Actor {

    private ScaleWorld world;
    public Camera(ScaleWorld w)
    {
        setImage("images/tinyTile.png");
        world = w;
    }

    public void act()
    {
        if(Mayflower.isKeyDown(Keyboard.KEY_W))
        {
            world.move(Direction.FORWARD);
        }
        else if(Mayflower.isKeyDown(Keyboard.KEY_S))
        {
            world.move(Direction.BACKWARD);
        }
        if(Mayflower.isKeyDown(Keyboard.KEY_D))
        {
            world.rotate(2);
        }
        else if(Mayflower.isKeyDown(Keyboard.KEY_A))
        {
            world.rotate(-2);
        }
    }
}
