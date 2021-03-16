package com.c4nn4.pix_engine.graphics.texts;

import com.c4nn4.pix_engine.graphics.Element;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.manager.fonts.FontManager;
import com.c4nn4.pix_engine.manager.fonts.Fonts;

import java.awt.*;

import static com.c4nn4.pix_engine.manager.fonts.FontManager.getTextHeight;
import static com.c4nn4.pix_engine.manager.fonts.FontManager.getTextWidth;

public class Text extends Element {

    public static final byte ALIGNEMENT_LEFT = 0,
                            ALIGNEMENT_CENTER = 1,
                            ALIGNEMENT_RIGHT = 2;


    private int xAdjusted = 0;

    private String textMsg;//The text
    private String showMsg;

    private final Fonts font;

    private Alignement align;

    private Color color;//Color

    private float stroke;
    private Color strokeColor;

    public Text(final int x, final int y, final int duration, final String textString, final Color color, final Fonts font) {
        super(x, y, duration);

        this.textMsg = textString == null ? "" : textString;
        this.showMsg = this.textMsg;

        this.xAdjusted = x;

        this.font = font;

        this.color = color;
        this.strokeColor = Color.BLACK;
        this.stroke = 0.f;

        this.align = Alignement.LEFT;
    }

    public void setAlignement(final Alignement align) {
        if (align != this.align) {
            switch (align) {
                case LEFT: {
                    this.align = Alignement.LEFT;
                    break;
                }
                case CENTER: {
                    this.align = Alignement.CENTER;
                    setShowXPosition(getShowXPos());
                    break;
                }
                case RIGHT: {
                    this.align = Alignement.RIGHT;
                    setShowXPosition(getShowXPos());
                    break;
                }
            }
        }
    }

    public Alignement getAlignement() { return this.align; }

    @Override
    protected void setShowXPosition(final double x) {
        switch (this.align) {
            case LEFT:
            default: {
                super.setShowXPosition(x);
                break;
            }
            case CENTER: {
                super.setShowXPosition(x);
                xAdjusted = FontManager.getCenteredPosition(showMsg, font, (int) x);
                break;
            }
            case RIGHT: {
                super.setShowXPosition(x);
                xAdjusted = FontManager.getRightenedPosition(showMsg, font, (int) x);
                break;
            }
        }
    }

    public String getText() {
        return textMsg;
    }

    public String getShowText() {
        return showMsg;
    }

    public void setColor(final Color color) {
        if (color != null) {
            this.color = color;
        }
    }

    public Color getColor() {
        return this.color;
    }

    public int getWidth() { return getTextWidth(this.getText(), this.font); }

    public int getHeight() { return getTextHeight(this.getText(), this.font); }

    public void setText(final String text) {
        this.textMsg = text != null ? text : "";
        this.showMsg = this.textMsg;
        setShowXPosition(getShowXPos());
    }

    @Override
    public void draw(final Renderer renderer) {
        renderer.setColor(color);
        renderer.drawString(xAdjusted, (int) getShowYPos(), showMsg, font, getAlpha(), stroke, strokeColor);
    }

    public void setStroke(float stroke) {
        this.stroke = Math.abs(stroke);
    }

    public void setStrokeColor(Color color) {
        this.strokeColor = color == null ? Color.BLACK : color;
    }

    public void setShowText(final String text) {
        this.showMsg = text;
        setShowXPosition(getShowXPos());
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public boolean equals(final Object other) {

        boolean retValue = false;

        if (!retValue && other != null) {
            if (this == other) {
                retValue = true;
            }
            else if (other instanceof Text &&
                    ((this.textMsg != null && this.textMsg.equals(((Text) other).textMsg)) ||
                            (this.textMsg == null && ((Text) other).textMsg == null))) {
                retValue = true;
            }
        }

        return retValue;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((textMsg == null) ? 0 : textMsg.hashCode());
        return result;
    }
}
