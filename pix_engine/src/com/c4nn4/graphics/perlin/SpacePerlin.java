package com.c4nn4.graphics.perlin;

public class SpacePerlin implements PerlinRGB {

    public SpacePerlin() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getRGB(double noise) {
		/*int b = (int) (noise * 0xFF);
		int g = (int) (b * 0x100);					
		int r = (int) (b * 0x18000);
	
		int finalValue = b + g + r;*/
		
		/*int b = (int) (-noise * 0x82);
		int g = (int) (b * 0x100);
		int r = (int) (b * 0x40000);
		int finalValue = b + g + r;*/

        //return finalValue;
        return -noise > 0.85 ? 0xFFFFFF : 0x000000;
    }

}
