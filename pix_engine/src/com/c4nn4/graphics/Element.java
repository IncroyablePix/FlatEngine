package com.c4nn4.graphics;

import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.main.GameThread;
import com.c4nn4.manager.debug.Debug;
import com.c4nn4.physics.coords.Velocity;

public abstract class Element implements TempElement {
    private static final double INCREASE_FACTOR = 900.0;

    private double xPos;//Positions on the screen
    private double yPos;

    private final Velocity vel;

    private double xPosShow;
    private double yPosShow;

    private int xMoveTo;
    private int yMoveTo;

    private int ticks;
    private int length;//Duration of the message

    private float alpha = 1.f;

    private boolean gravityActive;

    private final double gravity;
    private final double bounce;

    public Element(final int x, final int y, final int duration) {
        this.length = duration;

        this.xPos = x;
        this.yPos = y;

        this.vel = new Velocity(0.0, 0.0);

        this.xMoveTo = Integer.MAX_VALUE;
        this.yMoveTo = Integer.MAX_VALUE;

        this.xPosShow = x;
        this.yPosShow = y;

        this.gravityActive = true;
        this.gravity = INCREASE_FACTOR;
        this.bounce = 2.5;

        this.ticks = GameThread.ticks();
    }

    @Override
    public abstract void draw(Renderer renderer);

    @Override
    public void update() {
        treatVelocity();
        updateDebug();
    }

    @Override
    public boolean spoiled() {
        return length != -1 && GameThread.ticksFrom(ticks) >= length;
    }

    protected int getTicks() {
        return GameThread.ticksFrom(ticks);
    }

    protected int getDuration() {
        return this.length;
    }

    @Override
    public void setLength(int ticks) {
        this.length = ticks;
    }

    @Override
    public void resetStartingTick() {
        this.ticks = GameThread.ticks();
    }

    private void treatVelocity() {
        double xVel = getXVel();
        double yVel = getYVel();

        if (gravityActive) {
            if (xVel != 0) {
                if (xVel > 0) {
                    setShowXPosition(xPosShow + (xVel * GameThread.actionFactor));
                    xVel -= INCREASE_FACTOR * GameThread.actionFactor;

                    if (xVel == 0.0)
                        xVel = -INCREASE_FACTOR * GameThread.actionFactor;
                }
                else if (xVel < 0) {
                    double newX = xPosShow + (xVel * GameThread.actionFactor);

                    if (newX <= xPos && xPos < xPosShow) {
                        //xVel = (int) (xVel / -2) * INCREASE_FACTOR;// * GameThread.actionFactor;
                        xVel = (xVel / -bounce) * INCREASE_FACTOR;// / GameThread.actionFactor;
                        setShowXPosition(xPos);
                    }
                    else {
                        setShowXPosition(xPosShow + (xVel * GameThread.actionFactor));
                        xVel -= INCREASE_FACTOR * GameThread.actionFactor;
                    }
                }
            }

            if (yVel != 0) {
                if (yVel > 0) {
                    setShowYPosition(yPosShow + (yVel * GameThread.actionFactor));

                    yVel -= INCREASE_FACTOR * GameThread.actionFactor;

                    if (yVel == 0)
                        yVel = -INCREASE_FACTOR * GameThread.actionFactor;
                }
                else if (yVel < 0) {
                    double newY = yPosShow + (yVel * GameThread.actionFactor);

                    if (newY <= yPos && yPos < yPosShow)//Hit the ground
                    {
                        yVel = (yVel / -bounce);
                        setShowYPosition(yPos);

                        if (yVel < 0.1)
                            yVel = 0.0;
                    }
                    else {
                        setShowYPosition(newY);//(int) (yPosShow + GameThread.actionFactor * yVel));
                        yVel -= (INCREASE_FACTOR * GameThread.actionFactor);
                    }
                }
            }
        }
        else {
            if (xMoveTo != Integer.MAX_VALUE) {
                double oldX = xPosShow;
                int newX = (int) (xPosShow + (GameThread.actionFactor * xVel));
                if ((xVel > 0 && newX > xMoveTo/* && xMoveTo > oldX*/) || (xVel < 0 && newX < xMoveTo/* && xMoveTo < oldX*/)) {
                    setShowXPosition(xMoveTo);
                    xMoveTo = Integer.MAX_VALUE;
                    xVel = 0;
                }
                else {
                    setShowXPosition(newX);
                }
            }

            if (xMoveTo != Integer.MAX_VALUE) {
                double oldY = yPosShow;
                int newY = (int) (yPosShow + GameThread.actionFactor * yVel);
                if ((yVel > 0 && newY > yMoveTo/* && yMoveTo > oldY*/) || (yVel < 0 && newY < yMoveTo/* && yMoveTo < oldY*/)) {
                    setShowYPosition(yMoveTo);
                    yMoveTo = Integer.MAX_VALUE;
                    yVel = 0;
                }
                else {
                    setShowYPosition(newY);
                }
            }

            if (xMoveTo == Integer.MAX_VALUE && yMoveTo == Integer.MAX_VALUE)
                gravityActive = true;
        }

        vel.setxVel(xVel);
        vel.setyVel(yVel);
    }

    protected double getShowXPos() {
        return this.xPosShow;
    }

    protected double getShowYPos() {
        return this.yPosShow;
    }

    protected double getXVel() {
        return this.vel.getxVel();
    }

    protected double getYVel() {
        return this.vel.getyVel();
    }

    protected void setShowXPosition(final double x) {
        this.xPosShow = x;
    }

    protected void setShowYPosition(final double y) {
        this.yPosShow = y;
    }

    public void setMovingVector(double xVel, double yVel) {
        setXVel(xVel);
        setYVel(yVel);
    }

    protected void setXVel(final double x) {
        this.vel.setxVel(x);
    }

    protected void setYVel(final double y) {
        this.vel.setyVel(y);
    }

    /**
     * Moves an element to another position
     *
     * @param x     Where to (x pos)
     * @param y     Where to (y pos)
     * @param speed Pixels per tick
     */
    public void move(final int x, final int y, double speed) {
        speed = Math.abs(speed);

        final double xDelta = x - this.xPosShow;
        final double yDelta = y - this.yPosShow;

        xMoveTo = x;
        yMoveTo = y;

        final double dist = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));

        final double time = dist / speed;
		/*458.911756 / 5 = 91.7823 ticks
				
				Distance / Vitesse = Temps
				Vitesse = Distance / Temps*/

        setXVel((int) (xDelta / time) * 30);
        setYVel((int) (yDelta / time) * 30);

        gravityActive = false;
    }

    public void setPos(final int x, final int y) {
        this.xPosShow = x;
        this.yPosShow = y;
    }

    public boolean isMoving() {
        return this.vel.isMoving();
    }

    protected void updateDebug() {

    }

    protected void writeDebug(final String text) {
        Debug.log(text);
    }

    public void setAlpha(final float alpha) {
        this.alpha = alpha < 0 ? Math.abs(alpha) : alpha;
    }

    protected float getAlpha() {
        return this.alpha;
    }
}
