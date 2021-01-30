package be.helmo.physics;

import be.helmo.level.GameLevel;
import be.helmo.level.Platform;
import be.helmo.main.GameThread;
import be.helmo.physics.coords.Coords;
import be.helmo.physics.coords.Velocity;

import java.util.Objects;

public class Physics {
    private final Physical physicalObject;

    private final GameLevel gl;

    private final Velocity vel;
    private final Coords coords;

    private double mass;

    public Physics(final Velocity vel, final Coords coords, final GameLevel gl, final Physical physicalObject) {
        if (vel == null || coords == null)
            throw new IllegalArgumentException("Cannot infer physics from null velocity or coords");

        if (gl == null)
            throw new IllegalArgumentException("Cannot create physics without game level context");

        this.gl = gl;
        this.physicalObject = physicalObject;

        this.mass = 15.0;

        this.vel = vel;
        this.coords = coords;
    }

    public void move(Collider collider) {
        PosAndVel xPv = treatX(collider);
        PosAndVel yPv = treatY(collider);

        this.vel.setMovingVector(xPv.getVel(), yPv.getVel());
        this.coords.setPos(xPv.getPos(), yPv.getPos());
    }

    private PosAndVel treatX(Collider collider) {
        double x = coords.getX();
        double xVel = vel.getxVel();

        if (xVel != 0)
            x += xVel * GameThread.actionFactor;

        //---
        if (x > gl.getMaxBorder())
            x = gl.getMaxBorder();
        else if (x < gl.getMinBorder())
            x = gl.getMinBorder();

        coords.setX(x);
        vel.setxVel(xVel);

        return collider != null ?
                collider.checkX(physicalObject) :
                new PosAndVel(x, xVel);
    }

    private PosAndVel treatY(Collider collider) {
        double y = coords.getY();
        double yVel = vel.getyVel();

        if (yVel != 0) {
            y += yVel * GameThread.actionFactor;
            if (yVel > 0) {
                yVel -= mass * GameThread.actionFactor;

                if (yVel <= 0)
                    yVel = -mass * GameThread.actionFactor;
            }
            else {
                yVel -= mass * GameThread.actionFactor;
            }
        }
        else {
            if (!collider.isOnTopOfSomething(physicalObject))
                yVel = -mass * GameThread.actionFactor;
        }

        coords.setY(y);
        vel.setyVel(yVel);

        return collider != null ?
                collider.checkY(physicalObject) :
                new PosAndVel(y, yVel);
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    private double getPlatformY(final Platform platform) {
        return platform == null ? Double.POSITIVE_INFINITY : (Platform.isPlatformWater(platform) ? 0.0 : platform.getY() + 1);
    }

    /*public Platform yCollide(double yMin, double yMax) {
        Platform platform = gl.getGroundPos(this.coords.getX(), yMax);
        double groundPos = getPlatformY(platform);//gl.getGroundPos(this.x, yMax);

        if (yMin <= groundPos && groundPos <= yMax) {
            return platform;
        }

        return null;//Double.POSITIVE_INFINITY;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Physics physics = (Physics) o;
        return Double.compare(physics.mass, mass) == 0 &&
                Objects.equals(vel, physics.vel) &&
                Objects.equals(coords, physics.coords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vel, coords, mass);
    }
}
