package com.c4nn4.pix_engine.graphics.perlin;

/**
 * CloudyPerlin
 * <p>
 * Perlin noise transformator for a cloudy effect (with blue sky)
 *
 * @author IncroyablePix
 */
public class CloudyPerlin implements PerlinRGB {

    @Override
    public int getRGB(double noise) {

        int b = (int) (noise * 0x5);
        int g = 0xFFFFFF;//0x74ccf4;
        int r = b * 0x100000;

        int finalValue = b + g + r;

        return finalValue;
    }

}
