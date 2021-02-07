package be.helmo.graphics.overimages;

import be.helmo.graphics.Speed;
import be.helmo.main.screen.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FlashImage extends FadingImg {

    public enum FlashColor {
        RED(0xCC0000),
        BLUE(0x0000FF),
        YELLOW(0xFFFF00);

        private final BufferedImage image;

        FlashColor(int color) {
            this.image = coloredImage(color);
        }

        public BufferedImage getImage() {
            return this.image;
        }

        static BufferedImage coloredImage(int color) {
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
            image.setRGB(0, 0, color);
                    /*color >>> 4,
                    ((color >>> 2) & 0xFF,
                    (color & 0xFF));*/
            return image;
        }
    }

    public FlashImage(Speed speed, FlashColor color) {
        super(0, Screen.WIN_HEIGHT, Screen.WIN_WIDTH, Screen.WIN_HEIGHT, getDurationFromSpeed(speed), speed, color.getImage());
    }

    public FlashImage(Speed speed, int color) {
        super(0, 0, Screen.WIN_WIDTH, Screen.WIN_HEIGHT, getDurationFromSpeed(speed), speed, FlashColor.coloredImage(color));
    }

    private static int getDurationFromSpeed(Speed speed) {
        switch(speed) {
            default:
            case SLOW:
                return 35;
            case MEDIUM:
                return 20;
            case FAST:
                return 15;
        }
    }

    @Override
    public void update() {
        super.update();

        if(this.getAlpha() > 0.5f)
            this.setAlpha(0.5f);
    }
}
