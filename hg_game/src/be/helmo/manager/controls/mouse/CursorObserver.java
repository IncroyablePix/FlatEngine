package be.helmo.manager.controls.mouse;

import be.helmo.manager.controls.Controls;
import be.helmo.manager.controls.mouse.ClickZone;
import be.helmo.manager.controls.mouse.Clickable;
import be.helmo.manager.controls.mouse.Mouse;
import be.helmo.manager.debug.Debug;

import java.util.*;

public class CursorObserver {

    private final List<Clickable> elements;
    private byte active = 0;

    public CursorObserver() {
        elements = new LinkedList<>();
    }

    /**
     * Updates all elements contained while removing the ones that are spoiled.
     */
    public void update() {
        if(active < 2) {
            active ++;
        }
        else {
            int x = Controls.get().getCursorCoordsX();
            int y = Controls.get().getCursorCoordsY();

            try {
                for (Clickable element : elements) {
                    if (within(x, y, element.getClickZone())) {
                        element.onHoverIn();

                        if (Controls.get().isPressed(Mouse.LMB))
                            element.onLeftClick();

                        if(Controls.get().isPressed(Mouse.RMB))
                            element.onRightClick();

                        if(Controls.get().isPressed(Mouse.MMB))
                            element.onMiddleClick();

                        if (Controls.get().isPressed(Mouse.MWU))
                            element.onWheelUp(Controls.get().getNotches());

                        if (Controls.get().isPressed(Mouse.MWD))
                            element.onWheelDown(Controls.get().getNotches());
                    }
                    else {
                        element.onHoverOut();
                    }
                }
            }
            catch (ConcurrentModificationException e) { }
        }
    }

    private boolean within(int x, int y, ClickZone cz) {
        return cz != null && cz.within(x, y);
    }

    /**
     * Add an element to the Observer.
     *
     * @param element The element to add.
     * @return Whether the element could be inserted or not.
     */
    public boolean add(Clickable element) {
        boolean ret = false;

        if (element != null) {
            ret = elements.add(element);
        }

        return ret;
    }

    /**
     * Remove an element from the Observer's field of view.
     *
     * @param element The element to remove.
     */
    public void remove(Clickable element) {
        elements.remove(element);
    }
}
