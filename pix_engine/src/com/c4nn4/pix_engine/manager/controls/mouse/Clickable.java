package com.c4nn4.pix_engine.manager.controls.mouse;

public interface Clickable {
    ClickZone getClickZone();

    void onHoverIn();

    void onHoverOut();

    void onLeftClick();

    void onRightClick();

    void onMiddleClick();

    void onWheelUp(final int notches);

    void onWheelDown(final int notches);

    void addCursorListener(final CursorListener cl);
}
