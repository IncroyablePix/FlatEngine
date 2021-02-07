package be.helmo.level;

import be.helmo.level.map.AbstractTileLabel;
import be.helmo.graphics.render.Renderer;
import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.physics.ColParams;
import be.helmo.physics.Collision;
import be.helmo.physics.coords.Coords;
import be.helmo.physics.coords.Size;
import be.helmo.physics.Physical;

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
        /*        type.getType() == TileType.TYPE_BLOCKED ? ColParams.BOTTOM : //(byte) (ColParams.TOP | ColParams.BOTTOM | ColParams.LEFT | ColParams.RIGHT) :
                        ColParams.NO_COL
        );*/
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
