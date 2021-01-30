package be.helmo.level;

import be.helmo.graphics.Renderer;
import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.main.GameThread;
import be.helmo.main.screen.Screen;
import be.helmo.manager.debug.Debug;
import be.helmo.manager.debug.DebugInfo;
import be.helmo.manager.image.PixManager;
import be.helmo.physics.coords.Coords;

public class Water {
    private static final boolean FLOOD_ENABLED = false;
    private final ActiveSprite[][] water;
    private final Coords[][] waterCoords;

    private final int width;
    private final int ticks;

    private double increaseSpeed;

    private boolean moving;

    public Water(final int width) {
        ticks = GameThread.ticks();

        this.increaseSpeed = 0.0;

        this.width = width;
        water = new ActiveSprite[width + 4][2];
        waterCoords = new Coords[width + 4][2];

        for (int i = 0; i < width + 4; i++) //I add two extra water tiles for the movement to not let empty spaces
        {
            water[i][0] = new ActiveSprite(i - 3, 0, 1, PixManager.get().getSprite("WATER", (i & 1)));
            water[i][1] = new ActiveSprite(i - 3, -1, 1, PixManager.get().getSprite("WATER", (i & 1) + 2));
            waterCoords[i][0] = new Coords(i - 3, 0);
            waterCoords[i][1] = new Coords(i - 3, -1);
            water[i][0].setCoords(waterCoords[i][0]);
            water[i][1].setCoords(waterCoords[i][1]);
        }
    }

    public void draw(final Renderer renderer, final Camera camera) {
        if (water != null) {
            for (int i = 0; i < water.length; i++) {
                water[i][0].draw(renderer, camera, ActiveSprite.STICKY_X);
                water[i][1].draw(renderer, camera, ActiveSprite.STICKY_X);
            }

            int yTop = water[0][0].getBottomPixel(camera);

            renderer.drawRectangle(0, yTop, Screen.WIN_WIDTH, Math.abs(yTop - Screen.WIN_HEIGHT), PixManager.WATER_COLOR);
            //Debug.log("Rectangle : " + 0 + "x" + yTop + " " + GameWindow.WIN_WIDTH + "x" + (yTop - GameWindow.WIN_HEIGHT));
        }
    }

    public void update() {
        int animWater = GameThread.ticksFrom(ticks) % 400;

        double xIncrement = 0.0, yIncrement = 0.0;
        for (int i = 0; i < water.length; i++) {
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

            if (water[i][0].getY() + yIncrement < 0.0) {
                yIncrement = -water[i][0].getY();
                increaseSpeed = 0.0;
            }

            water[i][0].addPos(xIncrement, yIncrement);
            water[i][1].addPos(xIncrement, yIncrement);
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
        return water[0][0].getY();
    }
}
