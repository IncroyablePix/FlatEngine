package be.helmo.physics.coords;

public class Size {
    private double xSize;
    private double ySize;

    public Size(double xSize, double ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public double getxSize() {
        return xSize;
    }

    public void setxSize(double xSize) {
        this.xSize = xSize;
    }

    public double getySize() {
        return ySize;
    }

    public void setySize(double ySize) {
        this.ySize = ySize;
    }
}
