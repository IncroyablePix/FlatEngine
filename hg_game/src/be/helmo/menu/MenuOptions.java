package be.helmo.menu;

import be.helmo.enums.GameMenus;
import be.helmo.graphics.Renderer;
import be.helmo.main.screen.Screen;
import be.helmo.manager.GameStateManager;

public class MenuOptions extends MenuState {

    private final String[] options = {
            "Vidéo",
            "Précédent"
    };

    private GameMenus previousMenu = GameMenus.NO_MENU;

    public MenuOptions(final GameStateManager gsm, final GameMenus origin) {
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
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        super.update();
        //menuElements[previous].setColor(Color.WHITE);
        //menuElements[current].setColor(Color.RED);
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
        if (current == 0) {//Options vidéo
            gsm.setMenuState(GameMenus.O_VIDEO_MENU);
        }
        else if (current == 1) {//Précédent
            gsm.setMenuState(previousMenu);
        }
    }

    @Override
    public int getMenuOptions() {
        return options.length;
    }

    @Override
    public void switchOption(final boolean forward) {
        // TODO Auto-generated method stub

    }

}