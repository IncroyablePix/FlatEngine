package be.helmo.level.entities;

import be.helmo.graphics.render.Renderer;
import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.level.Camera;
import be.helmo.level.GameLevel;
import be.helmo.physics.ColParams;
import be.helmo.physics.Collider;
import be.helmo.physics.Collision;

public class Pickup extends Entity implements Pickupable {
    private boolean jump;

    public Pickup(GameLevel gl, ActiveSprite activeSprite, double x, double y) {
        super(gl, activeSprite, x, y);
        setColParams(ColParams.BOTTOM);

        jump = true;
    }

    @Override
    public void update(Collider collider) {
        super.update(collider);

        if(jump) {
            setMovingVector(0.0, 3.0);
            jump = false;
        }
        /*if (GameThread.tick(15)) {
            setMovingVector(0.0, 3.0);
        }*/
    }

    @Override
    public void onCollision(Collision collision) {
        if(collision.getDirection() == Collision.CollisionDirection.BOTTOM) {
            jump = true;
        }
    }

    @Override
    public void draw(Renderer renderer, Camera camera) {
        super.draw(renderer, camera);
    }

    public boolean isInRangeOf(final Entity entity, double radius) {
        return distanceBetweenTwoPoints(entity.getX(), entity.getY(), getX(), getY()) <= radius;
    }

    private static double distanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    @Override
    public void onPickUpPickup(Entity entity) {

    }
}
