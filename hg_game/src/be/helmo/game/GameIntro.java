package be.helmo.game;

import be.helmo.enums.GameMenus;
import be.helmo.enums.GameStates;
import be.helmo.graphics.hud.Observer;
import be.helmo.graphics.Renderer;
import be.helmo.graphics.texts.TypeText;
import be.helmo.main.GameThread;
import be.helmo.main.screen.Screen;
import be.helmo.manager.audio.AudioManager;
import be.helmo.manager.controls.ControlListener;
import be.helmo.manager.controls.Controls;
import be.helmo.manager.FontManager;
import be.helmo.manager.GameStateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * GameIntro
 * <p>
 * The introduction cinematic
 * It might be a little shitty coded but it's just to show two pictures and has no actual incidence overall, so...
 * whatever.
 *
 * @author IncroyablePix
 */
public class GameIntro extends GameState {

    private final BufferedImage[] logo;

    private final Observer observer;
    private final GameIntroControlsHandler controlsHandler;

    private final AudioManager am;

    private float alpha = 0.f;
    private int ticks;

    private final int FADE_IN = 80,
            LENGTH = 80,
            FADE_OUT = 80,
            END_PART = FADE_IN + LENGTH + FADE_OUT;

    private final byte SILHOUETTE = 0,
            GAME_BG = 1;


    private byte part = 0;

    /**
     * Loads resources and sets everything up
     *
     * @param gsm The GameStateManager
     * @author IncroyablePix
     */
    public GameIntro(final GameStateManager gsm) {
        super(gsm);
        debug("Loading GameIntro...");

        am = AudioManager.get();
        am.load("/be/helmo/resources/Sound/Music/silhouette.mp3", "intro", true);
        am.setVolume("intro", -10);

        ticks = GameThread.ticks();

        part = 0;

        logo = new BufferedImage[2];

        try {
            logo[0] = ImageIO.read(this.getClass().getResourceAsStream("/be/helmo/resources/Graphics/Others/SILHOUETTE.png"));
            logo[1] = ImageIO.read(this.getClass().getResourceAsStream("/be/helmo/resources/Graphics/Others/INTRO.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        observer = new Observer();
        observer.add(new TypeText(627, 180, END_PART, TypeText.SPEED_MEDIUM, "...a Silhouette Production...", Color.WHITE, FontManager.ORATOR_S));

        controlsHandler = new GameIntroControlsHandler();
        Controls.get().addListener(controlsHandler);

        debug("Loaded.");
    }

    /**
     * Initializes whatever needs to be
     *
     * @author IncroyablePix
     */
    public void init() {
        am.play("intro");
    }

    /**
     * Updates Observers, Images, etc., etc.
     */
    public void update() {
        int checkTick = GameThread.ticksFrom(ticks) - part * END_PART;
        //Debug.log("" + checkTick);

        if (ticks == END_PART + 1) {
            observer.add(new TypeText(707, 180, END_PART, TypeText.SPEED_MEDIUM, "...written by [Pix]...", Color.WHITE, FontManager.ORATOR_S));
			/*TypeText text = new TypeText(GameWindow.WIN_HEIGHT / 2, 180, END_PART, Text.SPEED_MEDIUM, "...written by [Pix]...", Color.WHITE, FontRepo.ORATOR_S);
			text.setAlignement(Text.ALIGNEMENT_CENTER);
			observer.add(text);*/
        }

        if (checkTick < FADE_IN) {
            alpha = (1.f * checkTick / FADE_IN);
        }
        else if (END_PART > checkTick && checkTick > FADE_IN + LENGTH) {
            alpha = 1.f - ((1.f * checkTick - FADE_IN - LENGTH) / FADE_OUT);
        }
        else if (checkTick >= END_PART) {
            if (part == SILHOUETTE) {
                part = GAME_BG;
                alpha = 0.f;
            }
            else if (part == GAME_BG) {
                exitIntro();
            }
        }

        observer.update();
    }

    /**
     * Draws images and observers, etc.
     *
     * @param renderer The Drawer to use
     */
    public void draw(final Renderer renderer) {
        //---FOND
        renderer.drawRectangle(0, 1080, Screen.WIN_WIDTH, Screen.WIN_HEIGHT, Color.BLACK);
        //---IMAGE
        if (part == 0)
            renderer.drawImage(logo[part], 675, 780, 630, 534, alpha);
        else if (part == 1)
            renderer.drawImage(logo[part], 713, 900, 453, 716, alpha);

        observer.draw(renderer);
    }

    /**
     * Finishes the intro.
     *
     * @author IncroyablePix
     */
    private void exitIntro() {
        am.unload("intro");

        gsm.setState(GameStates.MENU);
        gsm.setMenuState(GameMenus.MAIN_MENU);
    }

    @Override
    public void setState(GameMenus menu) {
    }

    @Override
    public void terminate() {
        Controls.get().removeListener(controlsHandler);
    }

    private class GameIntroControlsHandler implements ControlListener {
        private boolean paused = false;

        @Override
        public void onKeyInputChanged(int down, int pressed, int released) {
            if ((pressed & Controls.CHECK) == Controls.CHECK) {
                if (part == SILHOUETTE) {
                    alpha = 255;
                    ticks -= END_PART;
                    observer.remove(new TypeText(645, 180, END_PART, TypeText.SPEED_MEDIUM, "...a Silhouette Production...", Color.WHITE, FontManager.ORATOR_S));
                }
                else {
                    exitIntro();
                }
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
