package be.helmo.game;

import be.helmo.enums.GameMenus;
import be.helmo.graphics.overimages.Img;
import be.helmo.graphics.Renderer;
import be.helmo.main.screen.Screen;
import be.helmo.manager.image.Content;
import be.helmo.manager.controls.ControlListener;
import be.helmo.manager.controls.Controls;
import be.helmo.manager.GameStateManager;
import be.helmo.manager.debug.Debug;

public class GamePause extends GameState {
    private final Img pause;
    private ControlListener controlListener;

    public GamePause(final GameStateManager gsm) {
        super(gsm);

        pause = new Img(710, 100 + (Screen.WIN_HEIGHT / 2), 500, 200, -1, Content.load("/be/helmo/resources/Graphics/Others/PAUSE.png"), 1.0f);
        //Text text = new BouncingText(GameWindow.WIN_WIDTH / 2, GameWindow.WIN_HEIGHT / 2, 150, -1, Text.SPEED_MEDIUM, "PAUSE", Color.BLACK, FontManager.ORATOR_B);
        //text.setAlignement(Text.ALIGNEMENT_CENTER);

        controlListener = new GamePauseControlsHandler();
        Controls.get().addListener(controlListener);
        this.controlListener.pause(true);
    }

    @Override
    public void init() {
        pause.setPos(710, Screen.WIN_HEIGHT + 200);
        pause.setVelocity(0, -750.0);

        this.controlListener.pause(false);
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
    public void setState(GameMenus menu) {
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
                gsm.setPaused(false);

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
