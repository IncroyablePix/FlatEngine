package com.c4nn4.graphics.hud.elements;

import com.c4nn4.graphics.TempElement;
import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.manager.controls.mouse.Clickable;

public interface HUDElement extends Clickable, TempElement {
    void draw(Renderer renderer);
    void update();
    void focus(boolean focus);
}
