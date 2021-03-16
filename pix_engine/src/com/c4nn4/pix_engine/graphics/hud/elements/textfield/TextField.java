package com.c4nn4.pix_engine.graphics.hud.elements.textfield;

import com.c4nn4.pix_engine.graphics.hud.elements.HUDElement;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.graphics.texts.Alignement;
import com.c4nn4.pix_engine.graphics.texts.Text;
import com.c4nn4.pix_engine.manager.controls.Controls;
import com.c4nn4.pix_engine.manager.controls.keyboard.KeyboardTextListener;
import com.c4nn4.pix_engine.manager.controls.mouse.ClickZone;
import com.c4nn4.pix_engine.manager.controls.mouse.CursorListener;
import com.c4nn4.pix_engine.manager.fonts.FontManager;
import com.c4nn4.pix_engine.manager.fonts.Fonts;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TextField implements KeyboardTextListener, HUDElement {
    private static final int    WIDTH = 305,
                                HEIGHT = 20;

    private int cursor;
    private boolean focus;
    private Text text;
    private StringBuilder stringBuilder;
    private ClickZone clickZone;
    private Alignement alignement;
    private boolean alwaysVisible;
    private boolean hover;

    private TextFieldListener listener;

    public TextField(int x, int y, Alignement alignement, boolean alwaysVisible, TextFieldListener listener) {
        this.cursor = 0;
        this.focus = false;
        this.hover = false;
        this.stringBuilder = new StringBuilder();
        this.alignement = alignement;
        this.alwaysVisible = alwaysVisible;
        this.text = new Text(x + 3, y + 3, -1, stringBuilder.toString(), Color.WHITE, Fonts.COURIER_T);
        this.text.setAlignement(this.alignement);
        this.clickZone = new ClickZone(x, y, WIDTH, HEIGHT);
        this.clickZone.setAlignement(this.alignement);

        this.listener = listener;
    }

    public TextField(int x, int y, Alignement alignement, TextFieldListener listener) {
        this(x, y, alignement, true, listener);
    }

    public TextField(int x, int y, TextFieldListener listener) {
        this(x, y, Alignement.LEFT, listener);
    }

    public void setAlignement(Alignement alignement) {
        this.alignement = alignement == null ? Alignement.LEFT : alignement;
        this.clickZone.setAlignement(this.alignement);
        text.setAlignement(this.alignement);
    }

    public void setTextFieldListener(TextFieldListener listener) {
        if(listener != null)
            this.listener = listener;
    }

    public void setAlwaysVisible(boolean alwaysVisible) {
        this.alwaysVisible = alwaysVisible;
    }

    public String getText() {
        return stringBuilder.toString();
    }

    @Override
    public void draw(Renderer renderer) {
        if(hover || focus || alwaysVisible) {
            drawRectangle(renderer);
            text.draw(renderer);
        }
    }

    protected void drawRectangle(Renderer renderer) {
        switch(alignement) {
            default:
            case LEFT:
                renderer.drawRectangle(clickZone.x, clickZone.y + (FontManager.TEXT_SIZE / 2) + 5, clickZone.width, clickZone.height + 5, Color.BLACK);
                break;
            case CENTER:
                renderer.drawRectangle(clickZone.x - (clickZone.width / 2), clickZone.y + (FontManager.TEXT_SIZE / 2) + 5, clickZone.width, clickZone.height + 5, Color.BLACK);
                break;
            case RIGHT:
                renderer.drawRectangle(clickZone.x - (clickZone.width), clickZone.y + (FontManager.TEXT_SIZE / 2) + 5, clickZone.width, clickZone.height + 5, Color.BLACK);
                break;
        }
    }

    @Override
    public void update() {
        text.update();
    }

    @Override
    public boolean spoiled() {
        return false;
    }

    @Override
    public void resetStartingTick() {

    }

    @Override
    public void setLength(int ticks) {

    }

    @Override
    public ClickZone getClickZone() {
        return clickZone;
    }

    @Override
    public void onHoverIn() {
        hover = true;
    }

    @Override
    public void onHoverOut() {
        hover = false;
    }

    @Override
    public void onLeftClick() {
        focus(true);
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onMiddleClick() {
        try {
            String data = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
            onStringTyped(data);
        }
        catch (UnsupportedFlavorException | IOException ignored) {
        }
    }

    @Override
    public void onWheelUp(int notches) {

    }

    @Override
    public void onWheelDown(int notches) {

    }

    @Override
    public void addCursorListener(CursorListener cl) {

    }

    @Override
    public void onCharTyped(char c) {
        stringBuilder.insert(cursor ++, c);
        updateText();
    }

    @Override
    public void onStringTyped(String s) {
        if(s != null) {
            stringBuilder.insert(cursor, s);
            cursor += s.length();
            updateText();
        }
    }

    @Override
    public void onEnterPressed() {
        focus(false);
        sendMessage(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        cursor = 0;
        updateText();
    }

    @Override
    public void onExitPressed() {
        focus(false);
    }

    @Override
    public void onBackspacePressed() {
        if(cursor != 0) {
            stringBuilder.deleteCharAt(--cursor);
            updateText();
        }
    }

    @Override
    public void focus(boolean focus) {
        if(this.focus != focus) {
            if(focus)
                Controls.get().addKeyboardTextListener(this);
            else
                Controls.get().removeKeyboardTextListener(this);

            this.focus = focus;
        }
    }

    private void sendMessage(String msg) {
        if(listener != null)
            this.listener.onMessageSent(msg);
    }

    private void updateText() {
        int len = stringBuilder.length();
        String toShow;
        if(len > 25)
            toShow = stringBuilder.toString().substring(len - 25);
        else
            toShow = stringBuilder.toString();

        this.text.setText(toShow);
        this.text.update();
    }
}
