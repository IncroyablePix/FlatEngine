package be.helmo.manager.controls.mouse;

import be.helmo.main.screen.Screen;
import be.helmo.manager.debug.Debug;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseWheelListener {
    public static final int NUM_BTN = 5;

    private final boolean[] mouseState = new boolean[NUM_BTN];
    private final boolean[] prevMouseState = new boolean[NUM_BTN];
    private final int[] syncCheck = new int[NUM_BTN];

    public static int   LMB = 0,
                        RMB = 1,
                        MMB = 2,
                        MWU = 3,
                        MWD = 4;

    public int xMouse;
    public int yMouse;

    public int notches;

    private final Screen screen;

    public Mouse(final Screen screen) {
        this.screen = screen;

        for(int i = 0; i < NUM_BTN; i ++) {
            mouseState[i] = false;
            prevMouseState[i] = false;
            syncCheck[i] = 0;
        }
    }

    public void mouseSet(int i, boolean b) {
        mouseState[i] = b;

        if(b)
            syncCheck[i] ++;
        else
            syncCheck[i] = 0;

        if (i == MWU || i == MWD) {
            notches++;
        }
    }

    public void update() {
        try {
            xMouse = MouseInfo.getPointerInfo().getLocation().x - screen.getGameFrameMarginX() - screen.getOnScreenX();
            xMouse *= (double) Screen.WIN_WIDTH / (screen.getGameFrameWidth());

            yMouse = MouseInfo.getPointerInfo().getLocation().y - screen.getGameFrameMarginY() - screen.getOnScreenY();
            yMouse *= (double) Screen.WIN_HEIGHT / (screen.getGameFrameHeight());
            yMouse = screen.isFullscreen() ? Screen.WIN_HEIGHT - yMouse : -yMouse;
        }
        catch (IllegalComponentStateException | NullPointerException e) {
            xMouse = 0;
            yMouse = 0;
        }

        for(int i = 0; i < NUM_BTN; i ++) {
            prevMouseState[i] = mouseState[i];
            syncCheck[i] = 0;
        }

        mouseState[MWU] = false;
        mouseState[MWD] = false;

        notches = 0;
    }

    public boolean isPressed(int i) { return mouseState[i] && (!prevMouseState[i] || syncCheck[i] == 1); }

    public boolean isDown(int i) {
        return mouseState[i];
    }

    public boolean isReleased(int i) {
        return !mouseState[i] && prevMouseState[i];
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /* TODO: Récupérer position souris
         * 			- Voir si c'est sur l'image
         * 			- Récupérer position relative
         * 			- Multiplier x et y par x et y factor
         * 			- Stocker position dans une classe statique
         */
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                mouseSet(Mouse.LMB, true);
                break;
            case MouseEvent.BUTTON2:
                mouseSet(Mouse.MMB, true);
                break;
            case MouseEvent.BUTTON3:
                mouseSet(Mouse.RMB, true);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                mouseSet(Mouse.LMB, false);
                break;
            case MouseEvent.BUTTON2:
                mouseSet(Mouse.MMB, false);
                break;
            case MouseEvent.BUTTON3:
                mouseSet(Mouse.RMB, false);
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        final int notches = e.getWheelRotation();

        if (notches > 0) {
            mouseSet(Mouse.MWD, true);
            //setNotches(notches);
        }
        else if (notches < 0) {
            mouseSet(Mouse.MWU, true);
            //setNotches(notches);
        }
    }
}
