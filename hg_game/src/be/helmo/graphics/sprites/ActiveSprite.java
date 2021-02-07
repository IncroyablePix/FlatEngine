package be.helmo.graphics.sprites;

import be.helmo.graphics.render.Renderer;
import be.helmo.level.Camera;
import be.helmo.main.GameThread;
import be.helmo.physics.coords.Coords;

import java.awt.image.BufferedImage;

import static be.helmo.manager.image.PixManager.TILE_SIZE;

/**
 * Sprite
 * <p>
 * Sprites are roughly just images but went packed into a class
 * for the position to be be processed with scrolling by the time of drawing.
 *
 * @author IncroyablePix
 */
public class ActiveSprite {

    public static final int NO_STICK = 0x00,
            STICKY_X = 0x0F,
            STICKY_Y = 0xF0;

    //---

    private final BufferedImage[] images;

    private final int states;
    private int state;
    private final int periodicity;

    private Coords coords;

    private final int[] imageShift;

    //private int width;
    //private int height;

    private float alpha;

    public ActiveSprite(final double x, final double y, final int periodicity, final BufferedImage... images) {
        if (images == null || images.length == 0) {
            throw new IllegalArgumentException("No null image for a sprite !");
        }
        if (periodicity < 1) {
            throw new IllegalArgumentException("Periodicity cannot be less than 1");
        }

        this.coords = new Coords(x, y);

        this.alpha = 1.f;
        this.periodicity = periodicity;

        this.states = images.length;

        this.images = images;
        this.imageShift = new int[images.length];

        setupShifts();
        //this.width = image.getWidth();
        //this.height = image.getHeight();
    }

    public void setAlpha(float alpha) {
        if(alpha > 1.f)
            this.alpha = 1.f;
        else if(alpha < 0.f)
            this.alpha = 0.f;
        else
            this.alpha = alpha;
    }

    private void setupShifts() {
        for(int i = 0; i < imageShift.length; i ++) {
            imageShift[i] = (images[i].getHeight() < TILE_SIZE) ?
                    (int) (TILE_SIZE * (images[i].getHeight() / (double) TILE_SIZE)) :
                    TILE_SIZE;
        }
    }

    public void update() {
        if (GameThread.tick(periodicity)) {
            if (++state == states)
                state = 0;
        }
    }

    public void setCoords(Coords coords) {
        if (coords != null)
            this.coords = coords;
    }

    public void draw(final Renderer renderer, final Camera camera, final int attach) {
        BufferedImage image = getImage();
        int shift = getShift();

        if (image != null) {
            int x;
            int y;

            if ((attach & STICKY_X) == STICKY_X)
                x = (int) (this.coords.getX() * TILE_SIZE);
            else
                x = (int) ((this.coords.getX() - camera.getX()) * TILE_SIZE);

            if ((attach & STICKY_Y) == STICKY_Y)
                y = (int) (((this.coords.getY()) * TILE_SIZE) + shift);
            else
                y = (int) (((this.coords.getY() - camera.getY()) * TILE_SIZE) + shift);

            renderer.drawImage(image, x, y, image.getWidth(), image.getHeight(), this.alpha);
        }
    }

    public void draw(final Renderer renderer, final Camera camera) {
        BufferedImage image = getImage();
        int shift = getShift();

        if (image != null) {
            renderer.drawImage(image,
                    (int) ((this.coords.getX() - (camera == null ? 0 : camera.getX())) * TILE_SIZE),
                    (int) (((this.coords.getY() - (camera == null ? 0 : camera.getY())) * TILE_SIZE) + shift),
                    image.getWidth(), image.getHeight(), this.alpha);
        }
    }

    public int getBottomPixel(final Camera camera) {
        BufferedImage image = getImage();

        if (image != null)
            return (int) Math.ceil((((this.coords.getY() - (camera == null ? 0 : camera.getY())) * TILE_SIZE) - 2 * TILE_SIZE) + image.getHeight());

        return 0;
    }

    public void setPos(double x, double y) {
        this.coords.setPos(x, y);
    }

    public void addPos(double x, double y) {
        this.coords.setPos(this.getX() + x, this.getY() + y);
    }

    public double getX() {
        return this.coords.getX();
    }

    public double getY() {
        return this.coords.getY();
    }

    public BufferedImage getImage() {
        return images[state];
    }

    public int getShift() { return imageShift[state]; }

    public int getWidth() {
        return images[0].getWidth();
    }

    public int getHeight() {
        return images[0].getHeight();
    }

    public int hashCode() {
        return images[0].hashCode() * this.coords.hashCode();
    }
}
