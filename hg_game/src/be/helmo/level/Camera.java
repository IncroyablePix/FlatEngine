package be.helmo.level;

public class Camera {

    private double xCamera;
    private double yCamera;

    public Camera(double x, double y) {
        this.xCamera = x;
        this.yCamera = y;
    }

    public void setPos(double x, double y) {
        this.xCamera = x;
        this.yCamera = y;
    }

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
