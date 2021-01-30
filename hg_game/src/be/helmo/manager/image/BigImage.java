package be.helmo.manager.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BigImage {
    private final String name;
    private final BufferedImage image;

    static BigImage loadBigImage(String path, String name) {

        BigImage bi;

        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            bi = new BigImage(name, image);
        }
        catch (IOException | IllegalArgumentException e) {
            bi = null;
        }

        return bi;
    }

    private BigImage(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }
}
