package com.c4nn4.manager.controls.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

public class Keys implements KeyListener {
    private final boolean[] keysPressed = new boolean[120];
    private final boolean[] prevKeysPressed = new boolean[120];

    private final List<KeyboardTextListener> textListeners = new LinkedList<>();

    private void setKey(int i, boolean b) {
        if (keysPressed.length > i && i >= 0)
            keysPressed[i] = b;
    }

    public void update() {
        System.arraycopy(keysPressed, 0, prevKeysPressed, 0, keysPressed.length);
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

    public void addKeyboardTextListener(KeyboardTextListener listener) {
        if (listener != null && !textListeners.contains(listener))
            textListeners.add(listener);
    }

    public void removeKeyboardTextListener(KeyboardTextListener listener) {
        if(listener != null)
            textListeners.remove(listener);
    }

    private void keyTyped(int c) {
        if(!textListeners.isEmpty()) {
            if (c == '\n')
                returnPressed();
            else if(c == 0x1B)// ESC
                escapePressed();
            else if(c == '\b')//BACKSPACE
                backspacePressed();
            else
                getCharFromKey(c);
        }
    }

    private void returnPressed() {
        for (KeyboardTextListener listener : this.textListeners)
            listener.onEnterPressed();
    }

    private void escapePressed() {
        for (KeyboardTextListener listener : this.textListeners)
            listener.onExitPressed();
    }

    private void backspacePressed() {
        for(KeyboardTextListener listener : this.textListeners)
            listener.onBackspacePressed();
    }

    public boolean isActive() {
        return textListeners.isEmpty();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        setKey(key, true);

        keyTyped(key);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        setKey(e.getKeyCode(), false);
    }

    private void getCharFromKey(int k) {
        char c;
        boolean shifted = keysPressed[KeyEvent.VK_SHIFT];

        switch(k) {
            //Letters
            case KeyEvent.VK_A: c = shifted ? 'A' : 'a'; break;
            case KeyEvent.VK_B: c = shifted ? 'B' : 'b'; break;
            case KeyEvent.VK_C: c = shifted ? 'C' : 'c'; break;
            case KeyEvent.VK_D: c = shifted ? 'D' : 'd'; break;
            case KeyEvent.VK_E: c = shifted ? 'E' : 'e'; break;
            case KeyEvent.VK_F: c = shifted ? 'F' : 'f'; break;
            case KeyEvent.VK_G: c = shifted ? 'G' : 'g'; break;
            case KeyEvent.VK_H: c = shifted ? 'H' : 'h'; break;
            case KeyEvent.VK_I: c = shifted ? 'I' : 'i'; break;
            case KeyEvent.VK_J: c = shifted ? 'J' : 'j'; break;
            case KeyEvent.VK_K: c = shifted ? 'K' : 'k'; break;
            case KeyEvent.VK_L: c = shifted ? 'L' : 'l'; break;
            case KeyEvent.VK_M: c = shifted ? 'M' : 'm'; break;
            case KeyEvent.VK_N: c = shifted ? 'N' : 'n'; break;
            case KeyEvent.VK_O: c = shifted ? 'O' : 'o'; break;
            case KeyEvent.VK_P: c = shifted ? 'P' : 'p'; break;
            case KeyEvent.VK_Q: c = shifted ? 'Q' : 'q'; break;
            case KeyEvent.VK_R: c = shifted ? 'R' : 'r'; break;
            case KeyEvent.VK_S: c = shifted ? 'S' : 's'; break;
            case KeyEvent.VK_T: c = shifted ? 'T' : 't'; break;
            case KeyEvent.VK_U: c = shifted ? 'U' : 'u'; break;
            case KeyEvent.VK_V: c = shifted ? 'V' : 'v'; break;
            case KeyEvent.VK_W: c = shifted ? 'W' : 'w'; break;
            case KeyEvent.VK_X: c = shifted ? 'X' : 'x'; break;
            case KeyEvent.VK_Y: c = shifted ? 'Y' : 'y'; break;
            case KeyEvent.VK_Z: c = shifted ? 'Z' : 'z'; break;

            //Numbers
            case KeyEvent.VK_1: c = shifted ? '1' : '&'; break;
            case KeyEvent.VK_2: c = shifted ? '2' : 'é'; break;
            case KeyEvent.VK_3: c = shifted ? '3' : '"'; break;
            case KeyEvent.VK_4: c = shifted ? '4' : '\''; break;
            case KeyEvent.VK_5: c = shifted ? '5' : '('; break;
            case KeyEvent.VK_6: c = shifted ? '6' : '§'; break;
            case KeyEvent.VK_7: c = shifted ? '7' : 'è'; break;
            case KeyEvent.VK_8: c = shifted ? '8' : '!'; break;
            case KeyEvent.VK_9: c = shifted ? '9' : 'ç'; break;
            case KeyEvent.VK_0: c = shifted ? '0' : 'à'; break;

            //NUMPAD
            case KeyEvent.VK_NUMPAD1: c = '1'; break;
            case KeyEvent.VK_NUMPAD2: c = '2'; break;
            case KeyEvent.VK_NUMPAD3: c = '3'; break;
            case KeyEvent.VK_NUMPAD4: c = '4'; break;
            case KeyEvent.VK_NUMPAD5: c = '5'; break;
            case KeyEvent.VK_NUMPAD6: c = '6'; break;
            case KeyEvent.VK_NUMPAD7: c = '7'; break;
            case KeyEvent.VK_NUMPAD8: c = '8'; break;
            case KeyEvent.VK_NUMPAD9: c = '9'; break;
            case KeyEvent.VK_NUMPAD0: c = '0'; break;

            //Chars
            case KeyEvent.VK_COMMA: c = shifted ? ',' : '?'; break;
            case KeyEvent.VK_SEMICOLON: c = shifted ? '.' : ';'; break;
            case KeyEvent.VK_COLON: c = shifted ? '/' : ':'; break;
            case KeyEvent.VK_EQUALS: c = shifted ? '=' : '+'; break;

            case KeyEvent.VK_SPACE: c = ' ';
            default: c = '\0';
        }

        if(c != '\0') {
            for (KeyboardTextListener listener : this.textListeners)
                listener.onCharTyped(c);
        }
    }
}
