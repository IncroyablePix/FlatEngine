package be.helmo.main;

import be.helmo.main.screen.GameWindow;
import be.helmo.main.screen.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main {
    public static final String GAME_TITLE = "Higher Grounds";
    public static final String VERSION = "v0.2b1";

    public static final String BUGS = "- Le BGS ne fonctionne plus\n" +
            "- Les tiles de haut de plateforme\nne sont parfois pas les bonnes\n" +
            "- Le mode fenêtré ne place parfois pas le canva au centre";

    public static void main(String[] args) {
        JFrame window = new JFrame(GAME_TITLE);
        Screen screen = new Screen(window);
        GameWindow gw = new GameWindow(window, screen);

        screen.addResolutionChangedListener(gw);

        window.add(gw);

        window.setResizable(true);
        window.pack();
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/be/helmo/resources/Graphics/Others/ICON.png")));

        //---CURSOR
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(Main.class.getResource("/be/helmo/resources/Graphics/Cursor/CURSOR.png"));
        Cursor c = toolkit.createCustomCursor(image, new Point(window.getX(), window.getY()), "img");

        window.setCursor(c);

        window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                screen.onWindowResized(window.getWidth() - 16, window.getHeight() - 16);
            }
        });

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
