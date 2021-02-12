package be.helmo.menu;

import be.helmo.game.GameConnection;
import com.c4nn4.graphics.hud.elements.textfield.TextField;
import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.graphics.texts.Alignement;
import com.c4nn4.main.screen.Screen;
import com.c4nn4.manager.GameStateManager;
import com.c4nn4.manager.network.connection.HigherGroundsConnectionInitializer;
import com.c4nn4.menu.MenuElement;
import com.c4nn4.menu.MenuState;

import java.util.regex.Pattern;

public class MenuAskIP extends MenuState {
    private static final String IPV4_PATTERN_ALLOW_LEADING_ZERO =
            "^([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])\\." +
            "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])\\." +
            "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])\\." +
            "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])$";
    private static final String IPV4_PATTERN_WITH_PORT =
            "^([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])\\." +
            "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])\\." +
            "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])\\." +
            "([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])" +
            ":\\d{1,5}$";

    private TextField ipField;

    private final String[] options = {
            "Se connecter",
            "Précédent"
    };

    public MenuAskIP(GameStateManager gsm, MenuState previousMenu) {
        super(gsm, previousMenu);

        setupMenu();
        init();
    }

    @Override
    public void setupMenu() {
        ipField = new TextField(Screen.WIN_WIDTH / 2, Screen.WIN_HEIGHT / 2, Alignement.CENTER, this::trySendIp);
        addClickable(ipField);

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
    public void draw(Renderer renderer) {
        super.draw(renderer);
        ipField.draw(renderer);
    }

    @Override
    public void update() {
        super.update();
        ipField.update();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void selectOption() {
        if (current == 0) {//Connexion
            trySendIp(ipField.getText());
        }
        else if (current == 1) {//Précédent
            goBack();
        }
    }

    private void trySendIp(String ip) {
        try {
            sendIp(ip);
        }
        catch(IllegalArgumentException e) {
            addNotification(e.getMessage());
        }
    }

    @Override
    public int getMenuOptions() {
        return options.length;
    }

    @Override
    public void switchOption(boolean forward) {

    }

    private void sendIp(String ip) throws IllegalArgumentException {
        if(ip == null)
            throw new IllegalArgumentException("Veuillez saisir une adresse IP");

        if(Pattern.matches(IPV4_PATTERN_WITH_PORT, ip)) {
            String[] splits = ip.split(":");
            gsm.setState(new GameConnection(gsm, new HigherGroundsConnectionInitializer(), splits[0], Integer.parseInt(splits[1])));
        }
        else if(Pattern.matches(IPV4_PATTERN_ALLOW_LEADING_ZERO, ip)) {
            gsm.setState(new GameConnection(gsm, new HigherGroundsConnectionInitializer(), ip));
        }
        else {
            throw new IllegalArgumentException("Adresse IP invalide");
        }
    }
}
