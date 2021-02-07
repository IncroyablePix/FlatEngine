package be.helmo.physics.coords;

import java.util.Objects;

public class Velocity {
    private double xVel;
    private double yVel;

    public Velocity(double xVel, double yVel) {
        this.xVel = xVel;
        this.yVel = yVel;
    }

    public double getxVel() {
        return xVel;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }

    public double getyVel() {
        return yVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    public void setMovingVector(double x, double y) {
        this.xVel = x;
        this.yVel = y;
    }

    public boolean isMoving() {
        return this.xVel != 0 || this.yVel != 0;
    }

    public boolean isFalling() {
        return this.yVel < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Velocity velocity = (Velocity) o;
        return Double.compare(velocity.xVel, xVel) == 0 &&
                Double.compare(velocity.yVel, yVel) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xVel, yVel);
    }
}
