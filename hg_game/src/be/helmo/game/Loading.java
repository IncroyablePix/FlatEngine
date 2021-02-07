package be.helmo.game;

import be.helmo.graphics.render.Renderer;
import be.helmo.graphics.texts.Alignement;
import be.helmo.graphics.texts.Text;
import be.helmo.main.screen.Screen;
import be.helmo.manager.fonts.Fonts;

import java.awt.*;

public class Loading {
    private Text loading;
    private float alpha;
    private float alphaChange;

    public Loading() {
        loading = new Text(Screen.WIN_WIDTH / 2, Screen.WIN_HEIGHT / 2, -1, "Loading", Color.WHITE, Fonts.CHAMP_LIMO_B);
        loading.setAlignement(Alignement.CENTER);
        alphaChange = 0.025f;
        alpha = 0.0f;
    }

    public void update() {
        loading.update();
    }

    public void draw(Renderer renderer) {
        renderer.drawRectangle(0, Screen.WIN_HEIGHT, Screen.WIN_WIDTH, Screen.WIN_HEIGHT, Color.BLACK);
        updateAlpha();
        loading.draw(renderer);
    }

    private void updateAlpha() {
        alpha += alphaChange;
        if(alpha >= 1.f) {
            alpha = 1.f;
            alphaChange = -0.025f;
        }
        else if(alpha <= 0.0f) {
            alpha = 0.0f;
            alphaChange = 0.025f;
        }

        loading.setAlpha(alpha);
    }
}
