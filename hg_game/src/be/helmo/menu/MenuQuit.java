package be.helmo.menu;

import be.helmo.enums.GameMenus;
import be.helmo.graphics.Renderer;
import be.helmo.main.screen.Screen;
import be.helmo.manager.GameStateManager;

public class MenuQuit extends MenuState {

    private final String[] options = {
            "Non",
            "Oui"
    };

    private GameMenus previousMenu = GameMenus.NO_MENU;

    public MenuQuit(final GameStateManager gsm, final GameMenus origin) {
        super(gsm);

        previousMenu = origin;

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
            gsm.setMenuState(previousMenu);
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
