package be.helmo.graphics.layers;

import be.helmo.graphics.Renderer;
import be.helmo.level.Camera;

import java.awt.image.BufferedImage;

public abstract class Layer {

    protected BufferedImage image;

    public Layer(final int width, final int height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("Image cannot have negative values");

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void pasteImage(final BufferedImage image, final int xOrigin, final int yOrigin) {
        if (image == null)
            throw new IllegalArgumentException("image cannot be null");

        int width = image.getWidth() + xOrigin;
        if (width > this.image.getWidth())
            width = this.image.getWidth();

        int height = image.getHeight() + yOrigin;
        if (height > this.image.getHeight())
            height = this.image.getHeight();

        for (int x = xOrigin; x < width; x++) {
            for (int y = yOrigin; y < height; y++) {
                this.image.setRGB(x, y, image.getRGB(x, y));
            }
        }
    }

    public abstract void draw(final Renderer renderer, final Camera camera);

}
