package com.c4nn4.pix_engine.graphics.hud.elements;

import com.c4nn4.pix_engine.graphics.TempElement;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.manager.controls.mouse.Clickable;

public interface HUDElement extends Clickable, TempElement {
    void draw(Renderer renderer);
    void update();
    void focus(boolean focus);
}
