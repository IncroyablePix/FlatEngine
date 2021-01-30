package be.helmo.level.ornamentation;

import be.helmo.graphics.Renderer;
import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.level.Camera;
import be.helmo.manager.image.PixManager;

public abstract class Ornament implements Comparable<Ornament> {

    private final ActiveSprite activeSprite;

    private double x;
    private double y;

    private final int height;
    private final int width;

    public Ornament(final ActiveSprite activeSprite, double x, double y) {
        if (activeSprite == null)
            throw new IllegalArgumentException("Sprite cannot be null");

        this.activeSprite = activeSprite;

        this.width = activeSprite.getWidth();
        this.height = activeSprite.getHeight();

        setPos(x, y);
    }

    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;

        activeSprite.setPos(this.x, this.y + (this.height / PixManager.TILE_SIZE));

        //Debug.log("" + sprite.getX() + ", " + sprite.getY());
    }

    public void update() {
        activeSprite.update();
    }

    public void draw(final Renderer renderer, final Camera camera) {
        activeSprite.draw(renderer, camera);
    }

    public int hashCode() {
        return activeSprite.hashCode();
    }

    @Override
    public int compareTo(Ornament o) {
        return (o != null ? (int) (o.y - y) : 0);
    }
}
