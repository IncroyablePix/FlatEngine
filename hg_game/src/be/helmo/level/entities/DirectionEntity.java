package be.helmo.level.entities;

import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.graphics.sprites.ActiveSprite;
import com.c4nn4.game.level.Camera;
import be.helmo.level.HigherGroundsLevel;
import com.c4nn4.physics.Collider;
import com.c4nn4.physics.environment.Entity;

/**
 * Direction Entity
 * <p>
 * An entity that has two sets of images which you can choose to draw with
 * according to a set direction.
 *
 * @author IncroyablePix
 */
public abstract class DirectionEntity extends Entity {

    private final ActiveSprite left;
    private final ActiveSprite right;

    protected Directions direction;

    public DirectionEntity(HigherGroundsLevel gl, ActiveSprite left, ActiveSprite right, double x, double y) {
        super(gl, left, x, y);

        this.left = left;
        this.right = right;

        this.left.setCoords(this.coords);
        this.right.setCoords(this.coords);

        this.direction = Directions.RIGHT;
    }

    public void setDirection(Directions direction) {
        if (direction != null)
            this.direction = direction;
    }

    public Directions getDirection() {
        return this.direction;
    }

    @Override
    public void update(Collider collider) {
        left.update();
        right.update();

        move(collider);
    }

    @Override
    public void draw(final Renderer renderer, final Camera camera) {
        if (direction == Directions.LEFT)
            left.draw(renderer, camera);
        else
            right.draw(renderer, camera);
    }

    @Override
    public void move(Collider collider) {
        super.move(collider);

        if (this.getVelX() != 0)
            direction = this.getVelX() > 0 ? Directions.LEFT : Directions.RIGHT;
    }

    public void setPos(final double x, final double y) {
        super.setPos(x, y);
    }
}
