package com.c4nn4.level.entities.particles;

import com.c4nn4.pix_engine.graphics.sprites.ActiveSprite;
import com.c4nn4.level.HigherGroundsLevel;
import com.c4nn4.pix_engine.physics.Collider;
import com.c4nn4.pix_engine.physics.environment.Entity;
import com.c4nn4.pix_engine.physics.coords.Size;

class Particle extends Entity {

    private final Size size;
    private double xSize;
    private double ySize;

    private boolean physical;

    public Particle(HigherGroundsLevel gl, ActiveSprite activeSprite, double x, double y, double xSize, double ySize, boolean physical) {
        super(gl, activeSprite, x, y);

        this.physical = physical;

        size = new Size(xSize, ySize);
    }

    @Override
    public double getSizeX() {
        return size.getxSize();
    }

    @Override
    public double getSizeY() {
        return size.getySize();
    }

    public void setAlpha(final float alpha) {
        activeSprite.setAlpha(alpha);
    }

    @Override
    public void move(Collider collider) {
        super.move(physical ? collider : null);

        /*double xVel = getVelX();
        double yVel = getVelY();

        if(xVel != 0) {
            xVel = Math.max(Math.abs(xVel) - 0.0001, 0.0) *
                    (xVel < 0 ? -1 : 1);

            setMovingVector(xVel, yVel);
        }*/
    }
}
