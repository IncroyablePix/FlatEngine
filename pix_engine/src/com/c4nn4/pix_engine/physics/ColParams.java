package com.c4nn4.pix_engine.physics;

public class ColParams {
    public static final byte ALL = 0b00001111,
            TOP = 0b00001000,
            BOTTOM = 0b00000100,
            LEFT = 0b00000010,
            RIGHT = 0b00000001,
            NO_COL = 0b00000000;

    private boolean top, bottom, left, right;

    public ColParams(boolean top, boolean bottom, boolean left, boolean right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public ColParams(byte mask) {
        setParams(mask);
    }

    public boolean isTop() {
        return top;
    }

    public boolean isBottom() {
        return bottom;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isNoCol() {
        return !isTop() && !isBottom() && !isRight() && !isLeft();
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setParams(byte mask) {
        this.top = ((mask & TOP) == TOP);
        this.bottom = ((mask & BOTTOM) == BOTTOM);
        this.left = ((mask & LEFT) == LEFT);
        this.right = ((mask & RIGHT) == RIGHT);
    }
}
