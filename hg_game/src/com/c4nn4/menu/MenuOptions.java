package com.c4nn4.menu;

import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.pix_engine.manager.GameStateManager;
import com.c4nn4.pix_engine.menu.MenuElement;
import com.c4nn4.pix_engine.menu.MenuState;

public class MenuOptions extends MenuState {

    private final String[] options = {
            "Vidéo",
            "Précédent"
    };

    public MenuOptions(final GameStateManager gsm, final MenuState previousMenu) {
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
            gsm.setMenuState(new MenuVideo(gsm, this));
        }
        else if (current == 1) {//Précédent
            goBack();
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
