package be.helmo.manager.controls;

import be.helmo.manager.debug.Debug;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener {
    private final boolean[] keysPressed = new boolean[120];
    private final boolean[] prevKeysPressed = new boolean[120];

    private void setKey(int i, boolean b) {
        if (keysPressed.length > i && i >= 0)
            keysPressed[i] = b;
    }

    public void update() {
        for (int i = 0; i < keysPressed.length; i++) {
            prevKeysPressed[i] = keysPressed[i];
        }
    }

    public boolean isPressed(int i) { return keysPressed[i] && !prevKeysPressed[i]; }

    public boolean isDown(int i) {
        return keysPressed[i];
    }

    public boolean isReleased(int i) {
        return !keysPressed[i] && prevKeysPressed[i];
    }

    public boolean anyKeyDown() {
        for (int i = 0; i < keysPressed.length; i++) {
            if (keysPressed[i])
                return true;
        }
        return false;
    }

    public boolean anyKeyPress() {
        for (int i = 0; i < keysPressed.length; i++) {
            if (keysPressed[i] && !prevKeysPressed[i])
                return true;
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        setKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) { setKey(e.getKeyCode(), false); }
}
