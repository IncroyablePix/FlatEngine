package com.c4nn4.graphics.layers;

import com.c4nn4.game.level.Camera;
import com.c4nn4.graphics.render.Renderer;

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
