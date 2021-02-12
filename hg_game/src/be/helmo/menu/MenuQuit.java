package be.helmo.menu;

import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.main.screen.Screen;
import com.c4nn4.manager.GameStateManager;
import com.c4nn4.menu.MenuElement;
import com.c4nn4.menu.MenuState;

public class MenuQuit extends MenuState {

    private final String[] options = {
            "Non",
            "Oui"
    };

    public MenuQuit(final GameStateManager gsm, final MenuState previousMenu) {
        super(gsm, previousMenu);

        setupMenu();
        init();
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
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(final Renderer renderer) {
        super.draw(renderer);
    }

    @Override
    public void handleInput() {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectOption() {
        if (current == 0) {//Retour
            goBack();
        }
        else if (current == 1) {//Quitter
            System.exit(0);
        }
    }

    @Override
    public int getMenuOptions() {
        return options.length;
    }

    @Override
    public void switchOption(boolean forward) {
        // TODO Auto-generated method stub

    }

}
