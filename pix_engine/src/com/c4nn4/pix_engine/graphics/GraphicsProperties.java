package com.c4nn4.pix_engine.graphics;

public class GraphicsProperties {
    private static final GraphicsProperties instance = new GraphicsProperties(0, 0);

    public static GraphicsProperties get() {
        return instance;
    }

    private float xFactor;
    private float yFactor;

    private int xRes;
    private int yRes;

    private GraphicsProperties(float xFactor, float yFactor) {
        this.xFactor = xFactor;
        this.yFactor = yFactor;
    }

    public void setxFactor(float xFactor) {
        this.xFactor = xFactor;
    }

    public void setyFactor(float yFactor) {
        this.yFactor = yFactor;
    }

    public void setxResolution(int xRes) {
        this.xRes = xRes;
    }

    public void setyResolution(int yRes) {
        this.yRes = yRes;
    }

    public int getxRes() {
        return xRes;
    }

    public int getyRes() {
        return yRes;
    }

    public float getXFactor() {
        return xFactor;
    }

    public float getYFactor() {
        return yFactor;
    }

    public float getMinimumFactor() {
        return Math.min(xFactor, yFactor);
    }
}
