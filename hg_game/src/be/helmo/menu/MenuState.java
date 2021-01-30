package be.helmo.menu;

import be.helmo.graphics.hud.Observer;
import be.helmo.graphics.Renderer;
import be.helmo.graphics.TempElement;
import be.helmo.graphics.texts.BouncingText;
import be.helmo.graphics.texts.Text;
import be.helmo.main.screen.Screen;
import be.helmo.manager.controls.Clickable;
import be.helmo.manager.controls.CursorListener;
import be.helmo.manager.controls.CursorObserver;
import be.helmo.manager.FontManager;
import be.helmo.manager.GameStateManager;

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

    public MenuState(final GameStateManager gsm) {
        this.gsm = gsm;
        this.observer = new Observer();
        this.co = new CursorObserver();
        this.ticks = 0;

        init();
    }

    public void init() {
    }

    public void update() {
        ticks++;

        observer.update();

        co.update();
    }

    public void draw(final Renderer renderer) {
        observer.draw(renderer);
    }

    public abstract void setupMenu();

    public abstract void handleInput();

    public abstract void selectOption();

    public abstract void switchOption(final boolean forward);

    protected final void addNotification(final String text) {
        if (notif != null) {
            observer.remove(notif);
        }

        notif = new BouncingText(Screen.WIN_WIDTH - 20, 20, 150, 150, Text.SPEED_MEDIUM, text, Color.RED, FontManager.ORATOR_T);
        notif.setAlignement(Text.ALIGNEMENT_RIGHT);
        observer.add(notif);
    }

    protected final void addMenuElement(final MenuElement element) {
        co.add(element);
    }

    protected final void addObserverElement(final TempElement element) {
        observer.add(element);
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
