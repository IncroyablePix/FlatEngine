package com.c4nn4.level.entities;

import com.c4nn4.game.level.Camera;
import com.c4nn4.level.Arrow;
import com.c4nn4.level.HigherGroundsLevel;
import com.c4nn4.level.Platform;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.graphics.sprites.ActiveSprite;
import com.c4nn4.pix_engine.manager.debug.Debug;
import com.c4nn4.pix_engine.manager.debug.DebugInfo.DebugInfos;
import com.c4nn4.pix_engine.physics.ColParams;
import com.c4nn4.pix_engine.physics.Collider;
import com.c4nn4.pix_engine.physics.Collision;
import com.c4nn4.pix_engine.physics.environment.Tile;

public class Player extends DirectionEntity {
    private Arrow arrow;

    private boolean dead;

    private boolean jumped;

    public Player(HigherGroundsLevel gl, ActiveSprite left, ActiveSprite right, double x, double y) {
        super(gl, left, right, x, y);

        //this.arrow = arrow;
        //this.arrow.setEntityAttachement(this);

        this.phys.setMass(30.0);
        setColParams(ColParams.BOTTOM);

        jumped = false;
        dead = false;
    }

    @Override
    public void update(Collider collider) {
        super.update(collider);

        //arrow.update();

        HigherGroundsLevel gl = (HigherGroundsLevel) this.gl;
        if (!isDead() && gl.getWaterLevel() > getY()) {
            gl.onPlayerFallsInWater(this);
        }
    }

    @Override
    public double getSizeX() {
        return 0.5;
    }

    @Override
    public void draw(final Renderer renderer, final Camera camera) {
        super.draw(renderer, camera);

        /*if(!isMoving() && movable)
            arrow.draw(renderer, camera);*/
    }

    public boolean hasJumped() {
        return jumped;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    @Override
    public void setPos(double x, double y) {
        super.setPos(x, y);

        Debug.getinfo().setInfo(DebugInfos.PlayerX, new Object[]{x});
        Debug.getinfo().setInfo(DebugInfos.PlayerY, new Object[]{y});

        Debug.getinfo().setInfo(DebugInfos.VelocityX, new Object[]{getVelX()});
        Debug.getinfo().setInfo(DebugInfos.VelocityY, new Object[]{getVelY()});
    }

    @Override
    public void onCollision(Collision collision) {

        HigherGroundsLevel gl = (HigherGroundsLevel) this.gl;

        if (collision != null) {
            if (collision.getDirection() == Collision.CollisionDirection.BOTTOM) {
                gl.onPlayerReachesPlatform(this, toPlatform(collision.getTile()));

                Debug.log("Player : [" + getX() + ", " + getY() + "] - Tile : " + collision.getTile());
                jumped = false;
            }
        }
    }

    private Platform toPlatform(Tile tile) {

        HigherGroundsLevel gl = (HigherGroundsLevel) this.gl;
        return gl.getGroundPos(tile.getX(), tile.getY() + 1.0);
    }

    public boolean isDead() {
        return dead;
    }
}
