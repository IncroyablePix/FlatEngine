package be.helmo.game;

import com.c4nn4.game.GameState;
import com.c4nn4.graphics.overimages.Img;
import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.main.screen.Screen;
import com.c4nn4.manager.image.Content;
import com.c4nn4.manager.controls.ControlListener;
import com.c4nn4.manager.controls.Controls;
import com.c4nn4.manager.GameStateManager;
import com.c4nn4.manager.debug.Debug;
import com.c4nn4.menu.MenuState;

public class GamePause extends GameState {
    private final Img pause;
    private ControlListener controlListener;

    public GamePause(final GameStateManager gsm) {
        super(gsm);

        pause = new Img(710, 100 + (Screen.WIN_HEIGHT / 2), 500, 200, -1, Content.load("/be/helmo/resources/Graphics/Others/PAUSE.png"), 1.0f);
        //Text text = new BouncingText(GameWindow.WIN_WIDTH / 2, GameWindow.WIN_HEIGHT / 2, 150, -1, Text.SPEED_MEDIUM, "PAUSE", Color.BLACK, FontManager.ORATOR_B);
        //text.setAlignement(Alignement.CENTER);

        controlListener = new GamePauseControlsHandler();
        Controls.get().addListener(controlListener);
        this.controlListener.pause(true);
    }

    @Override
    public void init() {
        pause.setPos(710, Screen.WIN_HEIGHT + 200);
        pause.setVelocity(0, -750.0);

        this.controlListener.pause(false);
        initialized = true;
    }

    @Override
    public void update() {
        //observer.update();
        pause.update();
    }

    @Override
    public void draw(final Renderer renderer) {
        pause.draw(renderer);
    }

    @Override
    public void setState(MenuState menu) {
    }

    @Override
    public void terminate() {

    }

    private class GamePauseControlsHandler implements ControlListener {
        private boolean paused = false;

        @Override
        public void onKeyInputChanged(int down, int pressed, int released) {
            if ((pressed & Controls.PAUSE) == Controls.PAUSE) {
                Debug.log("Pause in pause");
                //gsm.setPaused(false);

                pause(true);
            }
        }

        @Override
        public boolean isPaused() {
            return paused;
        }

        @Override
        public void pause(boolean paused) {
            this.paused = paused;
        }
    }
}
