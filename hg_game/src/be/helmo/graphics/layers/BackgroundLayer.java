package be.helmo.graphics.layers;

import be.helmo.graphics.render.Renderer;
import be.helmo.level.Camera;

public class BackgroundLayer extends Layer {

    public BackgroundLayer(int width, int height) {
        super(width, height);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void draw(final Renderer renderer, final Camera camera) {
        renderer.drawImage(image, 0, image.getHeight(), image.getWidth(), image.getHeight(), 1.f);
    }

}
