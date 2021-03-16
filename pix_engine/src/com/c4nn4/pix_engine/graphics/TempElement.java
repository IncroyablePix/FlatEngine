package com.c4nn4.pix_engine.graphics;

import com.c4nn4.pix_engine.graphics.render.Renderer;

/**
 * TempElement
 * <p>
 * Elément temporaire
 *
 * @author IncroyablePix
 */
public interface TempElement {

    void draw(final Renderer renderer);

    void update();

    boolean spoiled();

    void resetStartingTick();

    void setLength(int ticks);

    boolean equals(final Object other);

    int hashCode();
}
