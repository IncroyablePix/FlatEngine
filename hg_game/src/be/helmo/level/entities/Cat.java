package be.helmo.level.entities;


import com.c4nn4.graphics.sprites.ActiveSprite;
import be.helmo.level.HigherGroundsLevel;
import com.c4nn4.main.GameThread;
import com.c4nn4.physics.Collider;
import com.c4nn4.physics.coords.Coords;
import com.c4nn4.physics.environment.Entity;
import com.c4nn4.physics.environment.EntityMovementListener;

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
