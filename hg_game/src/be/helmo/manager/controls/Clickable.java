package be.helmo.manager.controls;

public interface Clickable {
    boolean isMouseOn();

    void onHoverIn();

    void onHoverOut();

    void onLeftClick();

    void onRightClick();

    void onMiddleClick();

    void onWheelUp(final int notches);

    void onWheelDown(final int notches);

    void addCursorListener(final CursorListener cl);
}
