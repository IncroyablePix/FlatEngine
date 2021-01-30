package be.helmo.manager.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Content
 * <p>
 * Contains static classes to load images
 *
 * @author IncroyablePix
 */
public class Content {
    /**
     * Load images from a spritesheet
     *
     * @param path Path to the image file
     * @param w    width of a single SpriteSheet Image
     * @param h    height of a single SpriteSheet Image
     * @return The whole sprites that were extracted or null if the Image could not be found.
     */
    public static BufferedImage[][] load(final String path, int w, int h) {
        BufferedImage[][] img;
        try {
            BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(path));
            int width = spritesheet.getWidth() / w;
            int height = spritesheet.getHeight() / h;

            img = new BufferedImage[width][height];

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    img[i][j] = spritesheet.getSubimage(i * w, j * h, w, h);
                }
            }

            return img;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.printf("Erreur de chargement de l'image \"%s\".\n", path);
        }

        return null;
    }

    /**
     * Loads an image as a BufferedImage from a path.
     *
     * @param path Path to the image file
     * @return The image or null
     */
    public static BufferedImage load(final String path) {
        try {
            return ImageIO.read(Content.class.getResourceAsStream(path));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.printf("Erreur de chargement de l'image \"%s\".\n", path);
        }

        return null;
    }
}
