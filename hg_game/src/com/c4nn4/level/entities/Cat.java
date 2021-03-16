package com.c4nn4.level.entities;


import com.c4nn4.pix_engine.graphics.sprites.ActiveSprite;
import com.c4nn4.level.HigherGroundsLevel;
import com.c4nn4.pix_engine.main.GameThread;
import com.c4nn4.pix_engine.physics.Collider;
import com.c4nn4.pix_engine.physics.coords.Coords;
import com.c4nn4.pix_engine.physics.environment.Entity;
import com.c4nn4.pix_engine.physics.environment.EntityMovementListener;

/**
 * Cat
 * <p>
 * The end of level cat
 *
 * @author IncroyablePix
 */
public class Cat extends DirectionEntity implements EntityMovementListener {

    public Cat(HigherGroundsLevel gl, ActiveSprite left, ActiveSprite right, double x, double y) {
        super(gl, left, right, x, y);
    }

    @Override
    public void update(Collider collider) {
        super.update(collider);

        if (GameThread.tick(45)) {
            setMovingVector(0.0, 3.6);
        }
    }

    @Override
    public void setEntityAttachement(Entity entity) {
        if (entity != null)
            entity.addListener(this);
    }

    @Override
    public void onEntityMoved(Coords coords) {
        direction = coords.getX() > getX() ? Directions.RIGHT : Directions.LEFT;
    }
}
