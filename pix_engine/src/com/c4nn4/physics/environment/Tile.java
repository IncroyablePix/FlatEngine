package com.c4nn4.physics.environment;

import com.c4nn4.game.level.Camera;
import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.graphics.sprites.ActiveSprite;
import com.c4nn4.physics.ColParams;
import com.c4nn4.physics.Collision;
import com.c4nn4.physics.coords.Coords;
import com.c4nn4.physics.coords.Size;
import com.c4nn4.physics.Physical;

import java.awt.image.BufferedImage;

/**
 * An actual tile that can be drawn etc.
 * <p>
 * Contains a sprite and a fixed position
 * <p>
 * TODO: Add a "move" method (maybe?)
 */
public class Tile implements Physical {
    private final ActiveSprite image;

    private final Coords coords;
    private final Size size;
    private final ColParams col;

    private final AbstractTileLabel atl;

    public Tile(final BufferedImage image, byte col, final AbstractTileLabel atl, int x, int y, double xSize, double ySize) {
        this.image = new ActiveSprite(x, y, 1, image);
        this.col = new ColParams(col);
        this.atl = atl;

        this.coords = new Coords(x, y);
        this.size = new Size(xSize, ySize);
        this.image.setCoords(coords);
    }

    public void draw(final Renderer renderer, final Camera camera) {
        image.draw(renderer, camera);
    }

    public AbstractTileLabel getAbstractType() {
        return atl;
    }

    public void setColParams(byte mask) {
        this.col.setParams(mask);
    }

    public ColParams getColParams() {
        return this.col;
    }

    public boolean isNoCol() {
        return this.col == null || this.col.isNoCol();
    }

    @Override
    public void onCollision(Collision collision) {

    }

    @Override
    public double getX() {
        return coords.getX();
    }

    @Override
    public double getY() {
        return coords.getY();
    }

    @Override
    public void setPos(double x, double y) {
        coords.setPos(x, y);
    }

    @Override
    public void setMass(double g) { }

    @Override
    public double getVelX() {
        return 0;
    }

    @Override
    public double getVelY() {
        return 0;
    }

    @Override
    public void setMovingVector(double xVel, double yVel) {
        // void because static
    }

    @Override
    public double getSizeX() {
        return size.getxSize();
    }

    @Override
    public double getSizeY() {
        return size.getySize();
    }

    @Override
    public double getBounciness() { return 0; }

    @Override
    public String toString() {
        return "Tile{" +
                "coords=" + coords +
                ", size=" + size +
                '}';
    }
}
