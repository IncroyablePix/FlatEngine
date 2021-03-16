package com.c4nn4.pix_engine.menu;

import com.c4nn4.pix_engine.graphics.Speed;
import com.c4nn4.pix_engine.graphics.hud.elements.Button;
import com.c4nn4.pix_engine.graphics.texts.Alignement;
import com.c4nn4.pix_engine.graphics.texts.FadingText;
import com.c4nn4.pix_engine.manager.fonts.Fonts;

import java.awt.*;


public class MenuElement extends Button {

    public MenuElement(final int x, final int y, final String text) {
        super(x, y, new FadingText(x, y, -1, Speed.SLOW, text, Color.WHITE, Fonts.ORATOR_B));
        this.text.setAlignement(Alignement.CENTER);
        this.text.setStroke(1.f);
        this.text.setStrokeColor(Color.BLACK);
        this.clickZone.setAlignement(Alignement.CENTER);
    }

    @Override
    public void selectElement(final boolean select) {
        text.setColor(select ? Color.RED : Color.WHITE);
    }
}
