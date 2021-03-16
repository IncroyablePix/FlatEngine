package com.c4nn4.pix_engine.physics.coords;

public class Coords {
    private double x;
    private double y;

    public Coords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return Double.compare(coords.x, x) == 0 &&
                Double.compare(coords.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return (int) x * (int) y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
