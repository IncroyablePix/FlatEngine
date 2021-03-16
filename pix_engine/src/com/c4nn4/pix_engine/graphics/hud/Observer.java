package com.c4nn4.pix_engine.graphics.hud;

import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.graphics.TempElement;

import java.util.*;

/**
 * Observer for temporary images to display or to wipe out.
 * <p>
 * Use of a Set for no duplicates to be allowed and no null values to
 * be put in.
 * As you insert elements, pay attention to how your hashCode and equals
 * methods are written according to what you consider "the same".
 * -> i.e.: A text should not be defined only by its String.
 *
 * @author IncroyablePix
 */
public class Observer {

    private final List<TempElement> elements;

    /**
     * Creates a instance of Observer and its Set.
     */
    public Observer() {
        elements = new LinkedList<>();
    }

    /**
     * Updates all elements contained while removing the ones that are spoiled.
     */
    public void update() {

        try {
            for (TempElement element : elements) {
                if (element.spoiled()) {
                    elements.remove(element);
                }
                else {
                    element.update();
                }
            }
        }
        catch (ConcurrentModificationException ignored) {
        }
    }

    /**
     * Draws all elements in the Set.
     *
     * @param renderer The Drawer you use for displaying.
     */
    public void draw(final Renderer renderer) {
        final Iterator<TempElement> iterator = elements.iterator();

        try {
            while (iterator.hasNext()) {
                final TempElement element = iterator.next();

                element.draw(renderer);
            }
        }
        catch (ConcurrentModificationException ignored) {
        }
    }

    /**
     * Add an element to the Observer.
     *
     * @param element The element to add.
     * @return Whether the element could be inserted or not.
     */
    public boolean add(TempElement element) {
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
    public void remove(TempElement element) {
        elements.remove(element);
    }

    /**
     * Checks whether the set contains or not the element
     *
     * @param element The element to check for presence
     * @return True if it's in it
     */
    public boolean contains(TempElement element) {
        return element != null && elements.contains(element);
    }

    /**
     * Returns true or false whether the collection is empty or not
     *
     * @return true or false...
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Clears the set
     */
    public void clear() {
        elements.clear();
    }
}
