package be.helmo.menu;

import be.helmo.graphics.Renderer;
import be.helmo.graphics.TempElement;
import be.helmo.graphics.texts.FadingText;
import be.helmo.graphics.texts.Text;
import be.helmo.manager.controls.Clickable;
import be.helmo.manager.controls.Controls;
import be.helmo.manager.controls.CursorListener;
import be.helmo.manager.controls.Mouse;
import be.helmo.manager.FontManager;
import be.helmo.manager.debug.Debug;

import javax.swing.*;
import java.awt.*;

public class MenuElement extends AbstractButton implements TempElement, Clickable {
    private static final long serialVersionUID = -6258707310378363420L;

    private CursorListener cl;
    private final Text text;
    private final int x;
    private final int y;

    private boolean in;

    public MenuElement(final int x, final int y, final String text) {
        this.x = x;
        this.y = y;
        this.in = false;

        this.cl = null;

        this.text = new FadingText(x, y, -1, Text.SPEED_SLOW, text, Color.WHITE, FontManager.ORATOR_B);
        this.text.setAlignement(Text.ALIGNEMENT_CENTER);

        this.setSize(200, 50);
    }

    public void selectElement(final boolean select) {
        text.setColor(select ? Color.RED : Color.WHITE);
    }

    @Override
    public void setText(final String text) {
        this.text.setText(text);
    }

    @Override
    public void draw(Renderer renderer) {
        text.draw(renderer);
    }

    @Override
    public void update() {
        text.update();
    }

    @Override
    public boolean spoiled() {
        return text.spoiled();
    }

    @Override
    public void resetStartingTick() {

    }

    @Override
    public void setLength(int ticks) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLeftClick() {
        if (cl != null)
            cl.onLeftClick();
    }

    @Override
    public void onRightClick() {
        if (cl != null) cl.onRightClick();
    }

    @Override
    public void onMiddleClick() {
        if (cl != null) cl.onMiddleClick();
    }

    @Override
    public void onWheelUp(final int notches) {
        if (cl != null) cl.onWheelUp(notches);
    }

    @Override
    public void onWheelDown(final int notches) {
        if (cl != null) cl.onWheelDown(notches);
    }

    @Override
    public boolean isMouseOn() {
        return (x + 75 > Controls.get().getCursorCoordsX() && Controls.get().getCursorCoordsX() > x - 75) &&
                (y + FontManager.TEXT_SIZE > Controls.get().getCursorCoordsY() && Controls.get().getCursorCoordsY() > y - 10);
    }

    @Override
    public void addCursorListener(final CursorListener cl) {
        this.cl = cl;
    }

    @Override
    public void onHoverIn() {
        if (cl != null && !in) {
            cl.onHoverIn();
            in = true;
        }
    }

    @Override
    public void onHoverOut() {
        if (cl != null && in) {
            cl.onHoverOut();
            in = false;
        }
    }
}
