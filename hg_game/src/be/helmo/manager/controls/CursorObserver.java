package be.helmo.manager.controls;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CursorObserver {

    private final Set<Clickable> elements;

    public CursorObserver() {
        elements = new HashSet<>();
    }

    /**
     * Updates all elements contained while removing the ones that are spoiled.
     */
    public void update() {
        try {
            for (Clickable element : elements) {
                if (element.isMouseOn()) {
                    //System.out.println("Mouse on");
                    element.onHoverIn();

                    if (Controls.get().isPressed(Mouse.LMB))
                        element.onLeftClick();
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
        catch(ConcurrentModificationException e) {

        }
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
