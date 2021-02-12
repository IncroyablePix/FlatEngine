package com.c4nn4.graphics.perlin;

/**
 * FoggyPerlin
 * <p>
 * Perlin noise transformator for a foggy effect
 *
 * @author IncroyablePix
 */
public class FoggyPerlin implements PerlinRGB {

    @Override
    public int getRGB(double noise) {

        int b = (int) (noise * 0xFF);
        int g = b * 0x100;
        int r = b * 0x10000;

        int finalValue = b + g + r;

        return finalValue;
    }

}
