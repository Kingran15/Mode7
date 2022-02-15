package Mode7;

import mayflower.Actor;

public abstract class InactiveActor extends Actor {

    public void act() {
        getWorld().stopActor(this);
    }
}
