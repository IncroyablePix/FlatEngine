package com.c4nn4.level;

import com.c4nn4.level.entities.ArrowDirections;
import com.c4nn4.level.entities.Directions;
import com.c4nn4.game.level.Camera;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.graphics.sprites.ActiveSprite;
import com.c4nn4.pix_engine.physics.environment.Entity;
import com.c4nn4.pix_engine.physics.environment.EntityMovementListener;
import com.c4nn4.level.entities.Player;
import com.c4nn4.pix_engine.main.GameThread;
import com.c4nn4.pix_engine.physics.coords.Coords;

public class Arrow implements EntityMovementListener {

    public static final byte    MIN_STRENGTH = 18,
                                MAX_STRENGTH = 40;

    private static final double A_VEL = 180;//45;

    private double aVel;

    private final int level;
    private double power;
    private double angle;
    private final ActiveSprite[] ball;

    private double xOrigin, yOrigin;

    public Arrow(int level) {
        ball = new ActiveSprite[]{
                /*new ActiveSprite(0.0, 0.0, 1, 1, PixManager.get().ball()),
                new ActiveSprite(0.0, 0.0, 1, 1, PixManager.get().ball()),
                new ActiveSprite(0.0, 0.0, 1, 1, PixManager.get().ball()),
                new ActiveSprite(0.0, 0.0, 1, 1, PixManager.get().ball()),
                new ActiveSprite(0.0, 0.0, 1, 1, PixManager.get().ball()),
                new ActiveSprite(0.0, 0.0, 1, 1, PixManager.get().ball()),
                new ActiveSprite(0.0, 0.0, 1, 1, PixManager.get().ball()),
                new ActiveSprite(0.0, 0.0, 1, 1, PixManager.get().ball())*/
        };

        this.level = level;
        power = MIN_STRENGTH;
        angle = 0.0;

        xOrigin = 0.0;
        yOrigin = 0.0;

        aVel = 0.0;
    }

    public void update() {
        angle += aVel * GameThread.actionFactor;

        /*if(aVel > 0.0 && !Keys.isDown(Keys.RIGHT))
            aVel = 0.0;
        else if(aVel < 0.0 && !Keys.isDown(Keys.LEFT))
            aVel = 0.0;

        if(angle > 80.0)
        {
            angle = 80.0;
            aVel = 0.0;
        }
        else if(angle < -80.0)
        {
            angle = -80.0;
            aVel = 0.0;
        }*/

        //---

        double angle = Math.toRadians(this.angle);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        for (int i = 0; i < ball.length; i++)
            ball[i].setPos(xOrigin + (sin * (i + 1) * (power / 45)), yOrigin + (cos * (i + 1) * (power / 45)));

    }

    public void draw(final Renderer renderer, Camera camera) {
        for (int i = 0; i < ball.length; i++) {
            ball[i].draw(renderer, camera);
        }
    }

    public void moveArrow(ArrowDirections direction) {
        switch (direction) {
            case UP: {
                if (power < getPlayerMaxJumpStrength(level))
                    power += GameThread.actionFactor * 30;
                break;
            }
            case DOWN: {
                if (power > MIN_STRENGTH) power -= GameThread.actionFactor * 30;
                break;
            }
            case LEFT: {
                aVel -= A_VEL * GameThread.actionFactor;

                //Debug.log("aVel: " + aVel);
                break;
            }
            case RIGHT: {
                aVel += A_VEL * GameThread.actionFactor;
                //Debug.log("aVel: " + aVel);
                break;
            }
        }
    }

    public static byte getPlayerMaxJumpStrength(int level) {
        byte strength;
        switch (level / 4) {
            case 0:
                strength = 20;
                break;
            case 1:
                strength = 22;
                break;
            case 2:
                strength = 24;
                break;
            case 3:
                strength = 27;
                break;
            case 4:
                strength = 30;
                break;
            case 5:
                strength = 35;
                break;
            case 6:
                strength = 38;
                break;
            default:
                strength = MAX_STRENGTH;
                break;
        }
        return strength;
    }

    public void jump(Player player) {
        if (player != null) {
            double x, y;
            x = ((power) * Math.sin(Math.toRadians(angle)));
            y = ((power / 2) * Math.cos(Math.toRadians(angle)));

            player.setMovingVector(x / 2.7, y * 1.5);
        }
    }

    public Directions getDirection() {
        return angle > 0.0 ? Directions.LEFT : Directions.RIGHT;
    }

    @Override
    public void setEntityAttachement(Entity entity) {
        entity.addListener(this);
    }

    @Override
    public void onEntityMoved(Coords coords) {
        xOrigin = coords.getX() + 0.2;
        yOrigin = coords.getY() + 0.3;
    }
}
