package be.helmo.level.entities;

import be.helmo.graphics.Renderer;
import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.level.*;
import be.helmo.manager.debug.Debug;
import be.helmo.manager.debug.DebugInfo.DebugInfos;
import be.helmo.physics.ColParams;
import be.helmo.physics.Collider;
import be.helmo.physics.Collision;

public class Player extends DirectionEntity {
    private Arrow arrow;

    private boolean dead;

    private boolean jumped;

    public Player(GameLevel gl, ActiveSprite left, ActiveSprite right, Arrow arrow, double x, double y) {
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

        if (!isDead() && gl.getWaterLevel() > getY()) {
            gl.onPlayerFallsInWater(this);
            dead = true;
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
    public void onCollision(Collision... collisions) {
        Collision col;
        if (collisions.length == 1 && (col = collisions[0]) != null) {
            if (col.getDirection() == Collision.CollisionDirection.BOTTOM) {
                gl.onPlayerReachesPlatform(this, toPlatform(col.getTile()));
                jumped = false;
            }
        }
    }

    private Platform toPlatform(Tile tile) {
        return gl.getGroundPos(tile.getX(), tile.getY() + 1.0);
    }

    public boolean isDead() {
        return dead;
    }
}
