package be.helmo.menu;


import be.helmo.enums.GameMenus;
import be.helmo.enums.GameStates;
import be.helmo.graphics.Renderer;
import be.helmo.main.screen.Screen;
import be.helmo.manager.GameStateManager;

public class MainMenu extends MenuState {

    private final String[] options = {
            "Commencer",
            "Options",
            "Quitter"/*,
		"Debug"*/
    };

    public MainMenu(final GameStateManager gsm) {
        super(gsm);

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
            gsm.setState(GameStates.PLAY);
        }
        else if (current == 1) {
            gsm.setMenuState(GameMenus.OPTIONS_MENU);
        }
        else if (current == 2) {
            gsm.setMenuState(GameMenus.QUIT_MENU);
        }
        else if (current == 3) {
            gsm.setState(GameStates.DEBUG);
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
