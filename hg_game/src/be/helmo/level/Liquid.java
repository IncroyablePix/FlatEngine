package be.helmo.level;

import be.helmo.graphics.render.Renderer;
import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.main.GameThread;
import be.helmo.main.screen.Screen;
import be.helmo.manager.debug.Debug;
import be.helmo.manager.debug.DebugInfo;
import be.helmo.manager.image.PixManager;
import be.helmo.physics.coords.Coords;

import java.awt.*;

public class Liquid {
    private static final boolean FLOOD_ENABLED = false;
    private final ActiveSprite[][] liquid;
    private final Coords[][] liquidCoords;

    private final Color color;

    private final int width;
    private final int ticks;

    private double increaseSpeed;

    private boolean moving;

    public Liquid(final int width, Color color) {
        ticks = GameThread.ticks();

        this.increaseSpeed = 0.0;

        this.width = width;
        liquid = new ActiveSprite[width + 4][2];
        liquidCoords = new Coords[width + 4][2];

        this.color = color;

        for (int i = 0; i < width + 4; i++) {
            liquid[i][0] = new ActiveSprite(i - 3, 0, 1, PixManager.get().getSprite("LIQUID", (i & 1)));
            liquid[i][1] = new ActiveSprite(i - 3, -1, 1, PixManager.get().getSprite("LIQUID", (i & 1) + 2));
            liquidCoords[i][0] = new Coords(i - 3, 0);
            liquidCoords[i][1] = new Coords(i - 3, -1);
            liquid[i][0].setCoords(liquidCoords[i][0]);
            liquid[i][1].setCoords(liquidCoords[i][1]);
        }
    }

    public void draw(final Renderer renderer, final Camera camera) {
        if (liquid != null && camera != null) {
            if(camera.getY() <= liquid[0][0].getY()) {
                for (ActiveSprite[] activeSprites : liquid) {
                    activeSprites[0].draw(renderer, camera, ActiveSprite.STICKY_X);
                    activeSprites[1].draw(renderer, camera, ActiveSprite.STICKY_X);
                }

                int yTop = liquid[0][0].getBottomPixel(camera);

                renderer.drawRectangle(0, yTop, Screen.WIN_WIDTH, Math.abs(yTop - Screen.WIN_HEIGHT), color);
            }
        }
    }

    public void update() {
        int animWater = GameThread.ticksFrom(ticks) % 400;

        double xIncrement = 0.0, yIncrement = 0.0;
        for (int i = 0; i < liquid.length; i++) {
            if (animWater < 150) {
                xIncrement = 0.2475 * GameThread.actionFactor;
                yIncrement = increaseSpeed * GameThread.actionFactor;
            }
            else if (animWater < 165) {
                xIncrement = 0.375 * GameThread.actionFactor;
                yIncrement = increaseSpeed * GameThread.actionFactor;
            }
            else if (animWater < 180) {
                xIncrement = 0.525 * GameThread.actionFactor;
                yIncrement = increaseSpeed * GameThread.actionFactor;
            }
            else if (animWater < 200) {
                xIncrement = 0;
                yIncrement = increaseSpeed * GameThread.actionFactor;
            }
            else {
                xIncrement = -0.253125 * GameThread.actionFactor;
                yIncrement = increaseSpeed * GameThread.actionFactor;
            }

            if (!moving || !FLOOD_ENABLED)
                yIncrement = 0;
            else
                increaseSpeed += 0.005 * GameThread.actionFactor;

            if (increaseSpeed < 0)
                increaseSpeed = 0;
            //increaseWaterVel(0.005 * GameThread.actionFactor);

            if (liquid[i][0].getY() + yIncrement < 0.0) {
                yIncrement = -liquid[i][0].getY();
                increaseSpeed = 0.0;
            }

            liquid[i][0].addPos(xIncrement, yIncrement);
            liquid[i][1].addPos(xIncrement, yIncrement);
        }
    }

    public void increaseWaterVel(double vel) {
        if ((this.increaseSpeed += vel) >= 3.0)
            this.increaseSpeed = 3.0;

        Debug.getinfo().setInfo(DebugInfo.DebugInfos.WaterIncrease, new Object[]{this.increaseSpeed});
    }

    public void toggleWaterMovement(boolean moving) {
        this.moving = moving;
    }

    public double getWaterLevel() {
        return liquid[0][0].getY();
    }
}
