package com.c4nn4.menu;

import com.c4nn4.game.GamePlay;
import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.pix_engine.manager.GameStateManager;
import com.c4nn4.pix_engine.menu.MenuElement;
import com.c4nn4.pix_engine.menu.MenuState;

public class MenuGameOver extends MenuState {

    private final String[] options = {
            "Recommencer",
            "Retour au menu principal",
            "Quitter"
    };

    public MenuGameOver(GameStateManager gsm, MenuState previousMenuState) {
        super(gsm, previousMenuState);

        setupMenu();
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
    public void selectOption() {
        if (current == 0) {
            gsm.setState(new GamePlay(gsm));
        }
        else if (current == 1) {
            gsm.setMenuState(new MainMenu(gsm, this));
        }
        else if (current == 2) {
            gsm.setMenuState(new MenuQuit(gsm, this));
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

    @Override
    public void handleInput() {
        // TODO Auto-generated method stub

    }

}
