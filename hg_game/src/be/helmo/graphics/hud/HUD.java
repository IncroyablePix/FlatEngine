package be.helmo.graphics.hud;

import be.helmo.graphics.hud.elements.HUDElement;
import be.helmo.graphics.render.Renderer;
import be.helmo.manager.controls.Controls;
import be.helmo.manager.controls.mouse.CursorObserver;

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
