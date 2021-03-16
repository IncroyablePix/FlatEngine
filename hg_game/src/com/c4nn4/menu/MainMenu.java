package com.c4nn4.menu;


import com.c4nn4.game.GameDebug;
import com.c4nn4.game.GamePlay;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.pix_engine.manager.GameStateManager;
import com.c4nn4.pix_engine.menu.MenuElement;
import com.c4nn4.pix_engine.menu.MenuState;

public class MainMenu extends MenuState {

    private final String[] options = {
            "Jeu en solo",
            "Multijoueur",
            "Options",
            "Quitter"/*,
		"Debug"*/
    };

    public MainMenu(final GameStateManager gsm, final MenuState previousMenuState) {
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
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(final Renderer renderer) {
        super.draw(renderer);
		
		/*for(int i = 0; i < options.length; i ++) {
			menuElements[i].draw(drawer);
		}*/
    }

    @Override
    public void handleInput() {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectOption() {
        if (current == 0) {
            gsm.setState(new GamePlay(gsm));
        }
        else if(current == 1) {
            gsm.setMenuState(new MenuAskIP(gsm, this));
        }
        else if (current == 2) {
            gsm.setMenuState(new MenuOptions(gsm, this));
        }
        else if (current == 3) {
            gsm.setMenuState(new MenuQuit(gsm, this));
        }
        else if (current == 4) {
            gsm.setState(new GameDebug(gsm));
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
