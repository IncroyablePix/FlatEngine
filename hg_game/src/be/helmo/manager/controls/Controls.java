package be.helmo.manager.controls;

import be.helmo.main.screen.GameWindow;
import be.helmo.manager.debug.Debug;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Controls {
    public static final short   UP = 0x1,
                                DOWN = 0x2,
                                RIGHT = 0x4,
                                LEFT = 0x8,

                                JUMP = 0x10,
                                CHECK = 0x20,
                                PAUSE = 0x40,
                                MENU = 0x80,

                                DEBUG = 0x100;

    private static final Controls instance = new Controls();
    public static Controls get() {
        return instance;
    }

    private final Control[] controls;
    private Keys keyboard;
    private Mouse mouse;

    private int pressed, down, released;

    private final List<ControlListener> listeners;

    private Controls() {
        listeners = new ArrayList<>();

        pressed = 0x0;
        down = 0x0;
        released = 0x0;

        controls = new Control[9];
        controls[0] = new Control(KeyEvent.VK_UP, 0);
        controls[1] = new Control(KeyEvent.VK_DOWN, 0);
        controls[2] = new Control(KeyEvent.VK_RIGHT, 0);
        controls[3] = new Control(KeyEvent.VK_LEFT, 0);
        controls[4] = new Control(KeyEvent.VK_SPACE, 0);
        controls[5] = new Control(KeyEvent.VK_ENTER, 0);
        controls[6] = new Control(KeyEvent.VK_P, 0);
        controls[7] = new Control(KeyEvent.VK_ESCAPE, 0);
        controls[8] = new Control(KeyEvent.VK_F2, 0);
    }

    public void update(final GameWindow window) {

        for (Control control : controls)
            control.update(keyboard);

        if (keyboard != null)
            keyboard.update();

        if(mouse != null)
            mouse.update();

        updateKeys();
        publish();
    }

    private void publish() {
        try {
            for (ControlListener listener : listeners) {
                if(!listener.isPaused())
                    listener.onKeyInputChanged(down, pressed, released);
            }
        }
        catch(ConcurrentModificationException e) {

        }
    }

    private void updateKeys() {
        pressed = 0x0;
        down = 0x0;
        released = 0x0;

        for (int i = 0; i < controls.length; i++) {
            pressed |= (controls[i].isPressed() ? 1 : 0) << i;
            down |= (controls[i].isDown() ? 1 : 0) << i;
            released |= (controls[i].isReleased() ? 1 : 0) << i;
        }
    }

    public void removeListener(ControlListener listener) {
        if (listener != null)
            listeners.remove(listener);
    }

    public void addListener(ControlListener listener) {
        if (listener != null && !listeners.contains(listener))
            listeners.add(listener);
    }

    public boolean isPressed(int button) { return this.mouse != null && this.mouse.isPressed(button); }

    public int getNotches() { return this.mouse != null ? mouse.notches : 0; }

    public void setKeyboard(Keys key) {
        this.keyboard = key;
    }

    public void setMouse(Mouse mouse) { this.mouse = mouse; }

    public int getCursorCoordsX() { return this.mouse.xMouse; }

    public int getCursorCoordsY() { return this.mouse.yMouse; }

    private class Control {
        private final int mButton;
        private final int kButton;

        private boolean pressed = false;
        private boolean down = false;
        private boolean released = false;

        public Control(int kEvent, int mEvent) {
            if (kEvent != 0) {
                kButton = kEvent;
                mButton = 0;
            }
            else if (mEvent != 0) {
                mButton = mEvent;
                kButton = 0;
            }
            else {
                throw new IllegalArgumentException("A key has to be set!");
            }
        }

        public void update(Keys keyboard) {
            pressed = keyboard.isPressed(kButton);
            down = keyboard.isDown(kButton);
            released = keyboard.isReleased(kButton);

            if(kButton == KeyEvent.VK_UP && keyboard.isPressed(KeyEvent.VK_UP))
                Debug.log("Control " + keyboard + " : " + keyboard.isPressed(KeyEvent.VK_UP));

            /*if(kButton == KeyEvent.VK_ENTER && down)
                System.out.println("Enter down");*/
        }

        public boolean isPressed() {
            return pressed;
        }

        public boolean isDown() {
            return down;
        }

        public boolean isReleased() {
            return released;
        }
    }
}

