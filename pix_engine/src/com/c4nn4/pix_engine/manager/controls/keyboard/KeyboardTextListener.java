package com.c4nn4.pix_engine.manager.controls.keyboard;

public interface KeyboardTextListener {
    void onCharTyped(char c);
    void onStringTyped(String s);
    void onEnterPressed();
    void onExitPressed();
    void onBackspacePressed();
}
