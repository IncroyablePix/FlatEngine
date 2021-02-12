package com.c4nn4.menu;

import com.c4nn4.graphics.Speed;
import com.c4nn4.graphics.hud.elements.Button;
import com.c4nn4.graphics.texts.Alignement;
import com.c4nn4.graphics.texts.FadingText;
import com.c4nn4.manager.fonts.Fonts;

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
