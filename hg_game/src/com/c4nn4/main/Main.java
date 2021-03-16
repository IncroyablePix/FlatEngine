package com.c4nn4.main;

import com.c4nn4.game.GameIntro;
import com.c4nn4.pix_engine.main.screen.GameWindow;
import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.pix_engine.manager.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static final String GAME_TITLE = "Higher Grounds";
    public static final String VERSION = "v0.2b1";

    public static final String BUGS = "- Le BGS ne fonctionne plus\n" +
            "- Les tiles de haut de plateforme\nne sont parfois pas les bonnes\n" +
            "- Le mode fenêtré ne place parfois pas le canva au centre";

    public static void main(String[] args) {
        JFrame window = new JFrame(GAME_TITLE);
        Screen screen = new Screen(window);
        GameWindow gw = null;
        try {
            gw = new GameWindow(window, screen, GAME_TITLE, GameIntro.class.getConstructor(GameStateManager.class));
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        screen.addResolutionChangedListener(gw);

        window.add(gw);

        window.setResizable(true);
        window.pack();
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/com/c4nn4/resources/Graphics/Others/ICON.png")));

        //---CURSOR
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(Main.class.getResource("/com/c4nn4/resources/Graphics/Cursor/CURSOR.png"));
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
