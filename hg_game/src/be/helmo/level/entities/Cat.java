package be.helmo.level.entities;


import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.level.GameLevel;
import be.helmo.main.GameThread;
import be.helmo.physics.Collider;
import be.helmo.physics.coords.Coords;

/**
 * Cat
 * <p>
 * The end of level cat
 *
 * @author IncroyablePix
 */
public class Cat extends DirectionEntity implements EntityMovementListener {

    public Cat(GameLevel gl, ActiveSprite left, ActiveSprite right, double x, double y) {
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
