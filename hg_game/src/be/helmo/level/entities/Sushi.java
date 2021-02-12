package be.helmo.level.entities;

import com.c4nn4.graphics.sprites.ActiveSprite;
import be.helmo.level.HigherGroundsLevel;
import com.c4nn4.main.GameThread;
import com.c4nn4.physics.Collider;

public class Sushi extends Pickup implements Pickupable {

    public Sushi(HigherGroundsLevel gl, ActiveSprite activeSprite, double x, double y) {
        super(gl, activeSprite, x, y);
    }

    @Override
    public void update(Collider collider) {
        super.update(collider);

        if (GameThread.tick(45)) {
            //setMovingVector(0.0, 3.6);
        }
    }

    @Override
    public double getSizeX() {
        return 0.9;
    }

    @Override
    public double getSizeY() {
        return 0.9;
    }
}
