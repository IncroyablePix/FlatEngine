package com.c4nn4.graphics.hud.elements;

import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.graphics.texts.Text;
import com.c4nn4.manager.controls.mouse.ClickZone;
import com.c4nn4.manager.controls.mouse.CursorListener;

public abstract class Button implements HUDElement {
    private final int x, y;
    private boolean in;
    protected final ClickZone clickZone;
    private CursorListener cl;

    protected final Text text;

    public Button(final int x, final int y, final int width, final int height, final Text text) {
        this.x = x;
        this.y = y;
        this.cl = null;
        this.in = false;

        this.clickZone = new ClickZone(x, y, width, height);
        this.text = text;
    }

    public Button(final int x, final int y, final Text text) {
        this.x = x;
        this.y = y;
        this.cl = null;
        this.in = false;

        this.clickZone = new ClickZone(x, y, text.getWidth(), text.getHeight());
        this.clickZone.setAlignement(text.getAlignement());
        this.text = text;
    }

    public abstract void selectElement(final boolean select);

    public void setText(final String text) {
        if(this.text != null)
            this.text.setText(text);
    }

    @Override
    public ClickZone getClickZone() { return this.clickZone; }

    @Override
    public void draw(Renderer renderer) {
        text.draw(renderer);
    }

    @Override
    public void update() {
        if(text != null)
            text.update();
    }

    @Override
    public boolean spoiled() {
        if(text == null)
            return false;
        else
            return text.spoiled();
    }

    @Override
    public void resetStartingTick() {
        if(text != null)
            text.resetStartingTick();
    }

    @Override
    public void setLength(int ticks) {
        if(text != null)
            text.setLength(ticks);
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

    /*@Override
    public boolean isMouseOn() {
        return (x + 75 > Controls.get().getCursorCoordsX() && Controls.get().getCursorCoordsX() > x - 75) &&
                (y + FontManager.TEXT_SIZE > Controls.get().getCursorCoordsY() && Controls.get().getCursorCoordsY() > y - 10);
    }*/

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

    @Override
    public void focus(boolean focus) {

    }
}
