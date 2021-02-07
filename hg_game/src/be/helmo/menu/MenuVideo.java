package be.helmo.menu;

import be.helmo.graphics.render.Renderer;
import be.helmo.main.screen.Screen;
import be.helmo.manager.GameStateManager;
import be.helmo.menu.options.Resolution;

import java.util.Arrays;

public class MenuVideo extends MenuState {

    private final String[] options = {
            "1080p",
            "Plein écran",
            "Limiteur d'IPS",
            "Précédent"
    };

    private Resolution[] resolutions;
    private int currentResolution;

    public MenuVideo(final GameStateManager gsm, MenuState previousMenu) {
        super(gsm, previousMenu);

        setupResolutions();
        setupMenu();
    }

    public void setupResolutions() {
        resolutions = new Resolution[]{
                new Resolution(640, 480),
                new Resolution(800, 600),
                new Resolution(1024, 768),
                new Resolution(1280, 720),
                new Resolution(1280, 1024),
                new Resolution(1366, 768),
                new Resolution(1440, 900),
                new Resolution(1600, 900),
                new Resolution(1920, 1080)
        };
        Resolution current = new Resolution(gsm.getXRes(), gsm.getYRes());
        options[0] = current.getDescription();

        currentResolution = Arrays.binarySearch(resolutions, current);
    }

    @Override
    public void setupMenu() {
        menuElements = new MenuElement[options.length];

        for (int i = 0; i < options.length; i++) {
            menuElements[i] = new MenuElement(Screen.WIN_WIDTH / 2, BASE - i * GAP, options[i]);
            menuElements[i].addCursorListener(new MenuListener(i));
            addObserverElement(menuElements[i]);
            addMenuElement(menuElements[i]);
        }

        menuElements[0].selectElement(true);

        options[1] = gsm.getFullscreen() ? "Fenêtré" : "Plein écran";
        menuElements[1].setText(options[1]);
        options[2] = gsm.getFPSLimit() ? "Limiteur d'IPS: On" : "Limiteur d'IPS: Off";
        menuElements[2].setText(options[2]);
    }

    @Override
    public void draw(final Renderer renderer) {
        super.draw(renderer);

        for (int i = 0; i < options.length; i++) {
            menuElements[i].draw(renderer);
        }
    }

    @Override
    public void handleInput() {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectOption() {
        if (current == 0) {//1080p
            if (resolutions.length <= currentResolution || currentResolution < 0) {
                switchOption(true);
            }
            gsm.setResolution(resolutions[currentResolution].getWidth(), resolutions[currentResolution].getHeight());
            //gsm.setResolution(1920, 1080);
            //switchOption(true);
        }
        else if (current == 1) {//Fullscreen
            final boolean fs = !gsm.getFullscreen();
            gsm.fullScreenRelay(fs);

            options[1] = fs ? "Fenêtré" : "Plein écran";
            menuElements[1].setText(options[1]);

            addNotification("Affichage changé: " + options[1]);
        }
        else if (current == 2) {//FPS LIMIT
            final boolean fps = !gsm.getFPSLimit();
            gsm.setFPSLimit(fps);

            options[2] = fps ? "Limiteur d'IPS: On" : "Limiteur d'IPS: Off";
            menuElements[2].setText(options[2]);

            addNotification("Affichage changé: " + options[2]);
        }
        else if (current == 3) {//Précédent
            goBack();
        }
    }

    @Override
    public int getMenuOptions() {
        return options.length;
    }

    @Override
    public void switchOption(final boolean forward) {
        if (current == 0) {
            if (currentResolution >= 0) {
                if (forward) {
                    if (++currentResolution >= resolutions.length) {
                        currentResolution = resolutions.length - 1;
                    }
                }
                else {
                    if (--currentResolution <= 0) {
                        currentResolution = 0;
                    }
                }
            }
            else {
                currentResolution = 0;
            }

            System.out.println(resolutions[currentResolution].getDescription());
            options[0] = resolutions[currentResolution].getDescription();
            menuElements[0].setText(options[0]);
        }
    }

}
