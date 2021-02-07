package be.helmo.menu;

import be.helmo.graphics.Speed;
import be.helmo.graphics.hud.elements.Button;
import be.helmo.graphics.texts.Alignement;
import be.helmo.graphics.texts.FadingText;
import be.helmo.graphics.texts.Text;
import be.helmo.manager.fonts.FontManager;
import be.helmo.manager.fonts.Fonts;

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
