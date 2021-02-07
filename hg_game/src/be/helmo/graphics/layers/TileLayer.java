package be.helmo.graphics.layers;

import be.helmo.graphics.render.Renderer;
import be.helmo.level.Camera;

public class TileLayer extends Layer {

    private static final double BUFFER_FACTOR = 1.4;

    private double cX;
    private double cY;

    private final int xMargin;
    private final int yMargin;

    private final double avgCenter;

    public TileLayer(int width, int height) {
        super((int) (width * BUFFER_FACTOR), (int) (height * BUFFER_FACTOR));

        xMargin = (int) (width * BUFFER_FACTOR) * width;
        yMargin = (int) (height * BUFFER_FACTOR) * height;

        avgCenter = (BUFFER_FACTOR + 1.0) / 2;
    }

    @Override
    public void draw(Renderer renderer, Camera camera) {

    }

    public void init() {
        cX = 0;
        cY = 0;
    }
	
	/*public void shift(TileMap tileMap, Scrolling scrolling) {
		double xDelta;
		double yDelta;
		
		if(scrolling.getX() > xMargin * avgCenter) {
			//TODO
		}
		x * PixManager.TILE_SIZE;
		y * PixManager.TILE_SIZE;
		
		if(y != 0) {
			final Directions ud = y > 0 ? Directions.Up : Directions.Down;
			final int yOrigin = (ud == Directions.Up) ? this.image.getHeight() - y : y;
			
			if(ud == Directions.Up) {
				for(int v = this.image.getHeight(); v > y; v ++) {
					for(int h = 0, width = this.image.getWidth(); h < width; h ++) {
						this.image.setRGB(h, v, this.image.getRGB(h, v - y));
					}
				}
			}
			else if(ud == Directions.Down) {
				for(int v = 0; v > yOrigin; v --) {
					for(int h = 0, width = this.image.getWidth(); h < width; h ++) {
						this.image.setRGB(h, v, this.image.getRGB(h, v + y)); 
					}
				}
			}
		}
	}*/

    private enum Directions {
        Up, Down,
        Left, Right,
        No
    }

}
