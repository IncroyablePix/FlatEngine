package be.helmo.level.entities;

import be.helmo.graphics.render.Renderer;
import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.level.Camera;
import be.helmo.level.GameLevel;
import be.helmo.physics.*;
import be.helmo.physics.coords.Coords;
import be.helmo.physics.coords.Velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity
 * <p>
 * An entity that would somehow be interactive
 *
 * @author mooo
 */
public abstract class Entity implements Physical {
    protected ActiveSprite activeSprite;

    protected GameLevel gl;

    protected final Coords coords;
    protected final Velocity vel;
    protected final Physics phys;
    protected final ColParams col;

    private boolean alive;
    private boolean frozen;

    protected List<EntityMovementListener> listeners;

    public Entity(GameLevel gl, ActiveSprite activeSprite, double x, double y) {
        this.gl = gl;
        this.activeSprite = activeSprite;

        this.vel = new Velocity(0.0, 0.0);
        this.coords = new Coords(x, y);
        this.col = new ColParams((byte) (ColParams.TOP | ColParams.BOTTOM | ColParams.LEFT | ColParams.RIGHT));
        this.phys = new Physics(vel, coords, gl, this);

        this.activeSprite.setCoords(coords);

        this.listeners = new ArrayList<>();

        this.alive = true;
        this.frozen = false;
    }

    public void update(Collider collider) {
        move(collider);
        activeSprite.update();
    }

    public final boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public void draw(final Renderer renderer, final Camera camera) {
        if(camera != null && renderer != null) {
            if(camera.getY() + camera.getHeight() + getSizeY() >= getY() &&
                    getY() >= camera.getY() - getSizeY())
                activeSprite.draw(renderer, camera);
        }
    }

    public void addListener(EntityMovementListener listener) {
        if (listener != null)
            listeners.add(listener);
    }

    @Override
    public void setPos(final double x, final double y) { this.coords.setPos(x, y); }

    public boolean isMoving() {
        return this.vel.isMoving();
    }

    public boolean isFalling() {
        return this.vel.isFalling();
    }

    @Override
    public double getX() {
        return this.coords.getX();
    }

    @Override
    public double getY() {
        return this.coords.getY();
    }

    @Override
    public double getVelX() {
        return this.vel.getxVel();
    }

    @Override
    public double getVelY() {
        return this.vel.getyVel();
    }

    @Override
    public double getSizeX() {
        return 1.0;
    }

    @Override
    public double getSizeY() {
        return 1.0;
    }

    @Override
    public void setMass(double g) { this.phys.setMass(g); }

    @Override
    public double getBounciness() { return this.phys.getBounciness(); }

    public void setMovingVector(double xVel, double yVel) {
        this.vel.setxVel(xVel);
        this.vel.setyVel(yVel);
    }

    public void move(Collider collider) {
        if (!isFrozen()) {
            this.phys.move(collider, col);

            for (EntityMovementListener listener : listeners)
                if (listener != null)
                    listener.onEntityMoved(coords);
        }
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setColParams(byte mask) {
        this.col.setParams(mask);
    }

    protected final GameLevel getGameLevel() {
        return this.gl;
    }

    @Override
    public void onCollision(Collision collision) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Entity entity = (Entity) o;

        return  alive == entity.alive &&
                coords.equals(entity.coords) &&
                vel.equals(entity.vel) &&
                phys.equals(entity.phys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coords, vel, phys, alive);
    }
}