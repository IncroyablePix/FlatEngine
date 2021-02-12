package com.c4nn4.game.level;

public class Camera {

    private double xCamera;
    private double yCamera;

    private final double height;
    private final double width;

    public Camera(double x, double y) {
        this.xCamera = x;
        this.yCamera = y;

        height = 18.0;
        width = 32.0;
    }

    public void setPos(double x, double y) {
        this.xCamera = x;
        this.yCamera = y;
    }

    public double getHeight() { return height; }

    public double getWidth() { return width; }

    public double getX() {
        return this.xCamera;
    }

    public double getY() {
        return this.yCamera;
    }

    public void increment(double x, double y) {
        this.xCamera += x;
        this.yCamera += y;

        if (this.yCamera < 0)
            this.yCamera = 0;
    }
}
