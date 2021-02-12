package com.c4nn4.graphics.perlin;

/**
 * SizzlingPerlin
 * <p>
 * Perlin noise transformator for a buggy screen effect
 *
 * @author IncroyablePix
 */
public class SizzlingPerlin implements PerlinRGB {

    @Override
    public int getRGB(double noise) {
        int b = 0xFF;
        int g = 0xFF00;
        int r = (int) (noise * 0xFF0000);

        int finalValue = b + g + r;

        return finalValue;
    }

}
