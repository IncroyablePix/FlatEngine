package com.c4nn4.pix_engine.menu;

import com.c4nn4.pix_engine.graphics.Speed;
import com.c4nn4.pix_engine.graphics.TempElement;
import com.c4nn4.pix_engine.graphics.hud.Observer;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.graphics.texts.Alignement;
import com.c4nn4.pix_engine.graphics.texts.BouncingText;
import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.pix_engine.manager.controls.Controls;
import com.c4nn4.pix_engine.manager.controls.mouse.Clickable;
import com.c4nn4.pix_engine.manager.controls.mouse.CursorListener;
import com.c4nn4.pix_engine.manager.controls.mouse.CursorObserver;
import com.c4nn4.pix_engine.manager.GameStateManager;
import com.c4nn4.pix_engine.manager.fonts.Fonts;

import java.awt.*;

public abstract class MenuState {
    public static int GAP = 53;
    public static int BASE = 700;//330

    protected GameStateManager gsm;

    protected Observer observer;

    private final CursorObserver co;

    private BouncingText notif;

    protected int current = 0;
    protected int previous = 0;

    protected int ticks;

    protected MenuElement[] menuElements;
    protected Clickable[] menuClickables;
    protected String[] options;

    private final MenuState previousMenu;

    public MenuState(final GameStateManager gsm, MenuState previousMenu) {
        this.gsm = gsm;
        this.previousMenu = previousMenu;
        this.observer = new Observer();
        Controls.get().addObserver(this.co = new CursorObserver());
        this.ticks = 0;

        init();
    }

    public void init() {
    }

    public void update() {
        ticks++;

        observer.update();
    }

    public void draw(final Renderer renderer) {
        observer.draw(renderer);
    }

    public abstract void setupMenu();

    public abstract void handleInput();

    public abstract void selectOption();

    public void terminate() { Controls.get().removeObserver(this.co); }

    public abstract void switchOption(final boolean forward);

    protected final void addNotification(final String text) {
        if (notif != null) {
            observer.remove(notif);
        }

        notif = new BouncingText(Screen.WIN_WIDTH - 20, 20, 150, 150, Speed.MEDIUM, text, Color.RED, Fonts.ORATOR_T);
        notif.setAlignement(Alignement.RIGHT);
        observer.add(notif);
    }

    protected final void addMenuElement(final MenuElement element) {
        co.add(element);
    }

    protected final void addObserverElement(final TempElement element) {
        observer.add(element);
    }

    protected final void goBack() {
        if(this.previousMenu != null) {
            gsm.setMenuState(this.previousMenu);
        }
    }

    public int getMenuOptions() {
        return options.length;
    }

    public int getMenuChoice() {
        return current;
    }

    public void setMenuChoice(boolean up) {

        if (up) {
            if (current > 0) {
                setMenuElement(current - 1);
            }
            else {
                setMenuElement(getMenuOptions() - 1);
            }
        }
        else {
            if (current < getMenuOptions() - 1) {
                setMenuElement(current + 1);
            }
            else {
                setMenuElement(0);
            }
        }
    }

    public void setMenuElement(final int element) {
        if (element != current) {
            previous = current;

            current = element;

            menuElements[previous].selectElement(false);
            menuElements[current].selectElement(true);
        }
    }

    protected void addClickable(Clickable clickable) {
        if(clickable != null)
            co.add(clickable);
    }

    protected class MenuListener implements CursorListener {
        private final int index;

        public MenuListener(final int index) {
            this.index = index;
        }

        @Override
        public void onHoverIn() {
            setMenuElement(this.index);
        }

        @Override
        public void onHoverOut() {
        }

        @Override
        public void onLeftClick() {
            if (current != index) {
                setMenuElement(index);
            }
            selectOption();
        }

        @Override
        public void onRightClick() {
        }

        @Override
        public void onMiddleClick() {
        }

        @Override
        public void onWheelUp(final int notches) {
            setMenuElement(index);
            for (int i = 0; i < notches; i++) {
                switchOption(true);
            }
        }

        @Override
        public void onWheelDown(final int notches) {
            setMenuElement(index);
            for (int i = 0; i < notches; i++) {
                switchOption(false);
            }
        }
    }
}
