package com.c4nn4.pix_engine.graphics.hud;

import com.c4nn4.pix_engine.graphics.hud.elements.HUDElement;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.manager.controls.Controls;
import com.c4nn4.pix_engine.manager.controls.mouse.CursorObserver;

import java.util.LinkedList;
import java.util.List;

public class HUD {
    private CursorObserver cursorObserver;
    private List<HUDElement> elements;

    public HUD() {
        elements = new LinkedList<>();
        cursorObserver = new CursorObserver();

        Controls.get().addObserver(cursorObserver);
    }

    public void add(HUDElement element) {
        if(element != null && !elements.contains(element)) {
            elements.add(element);
            cursorObserver.add(element);
        }
    }

    public void remove(HUDElement element) {
        if(element != null && elements.contains(element)) {
            elements.remove(element);
            cursorObserver.remove(element);
        }
    }

    public void update() {
        for(HUDElement element : elements)
            element.update();

        cursorObserver.update();
    }

    public void draw(Renderer renderer) {
        for(HUDElement element : elements)
            element.draw(renderer);
    }

    public void terminate() {
        for(HUDElement element : elements)
            element.focus(false);

        Controls.get().removeObserver(cursorObserver);
    }

}
