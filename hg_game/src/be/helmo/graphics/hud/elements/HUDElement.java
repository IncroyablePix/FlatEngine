package be.helmo.graphics.hud.elements;

import be.helmo.graphics.TempElement;
import be.helmo.graphics.render.Renderer;
import be.helmo.manager.controls.mouse.Clickable;

public interface HUDElement extends Clickable, TempElement {
    void draw(Renderer renderer);
    void update();
    void focus(boolean focus);
}
