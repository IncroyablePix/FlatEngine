package be.helmo.graphics.texts;

import be.helmo.graphics.Element;
import be.helmo.graphics.Renderer;
import be.helmo.manager.FontManager;

import java.awt.*;

public class Text extends Element {

    public static final byte ALIGNEMENT_LEFT = 0,
            ALIGNEMENT_CENTER = 1,
            ALIGNEMENT_RIGHT = 2;


    private int xAdjusted = 0;

    private String textMsg;//The text
    private String showMsg;

    private final byte font;

    private byte align;

    private Color color;//Color


    public Text(final int x, final int y, final int duration, final String textString, final Color color, final byte font) {
        super(x, y, duration);

        this.textMsg = textString == null ? "" : textString;
        this.showMsg = this.textMsg;

        this.xAdjusted = x;

        this.font = font;

        this.color = color;

        this.align = ALIGNEMENT_LEFT;
    }

    public void setAlignement(final byte align) {
        if (align != this.align) {
            switch (align) {
                case ALIGNEMENT_LEFT: {
                    this.align = ALIGNEMENT_LEFT;
                    break;
                }
                case ALIGNEMENT_CENTER: {
                    this.align = ALIGNEMENT_CENTER;
                    setShowXPosition(getShowXPos());
                    break;
                }
                case ALIGNEMENT_RIGHT: {
                    this.align = ALIGNEMENT_RIGHT;
                    setShowXPosition(getShowXPos());
                    break;
                }
            }
        }
    }

    @Override
    protected void setShowXPosition(final double x) {
        switch (this.align) {
            case ALIGNEMENT_LEFT:
            default: {
                super.setShowXPosition(x);
                break;
            }
            case ALIGNEMENT_CENTER: {
                super.setShowXPosition(x);
                xAdjusted = FontManager.getCenteredPosition(showMsg, font, (int) x);
                break;
            }
            case ALIGNEMENT_RIGHT: {
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

    public void setText(final String text) {
        this.textMsg = text != null ? text : "";
        this.showMsg = this.textMsg;
        setShowXPosition(getShowXPos());
    }

    @Override
    public void draw(final Renderer renderer) {
        renderer.setColor(color);
        renderer.drawString(xAdjusted, (int) getShowYPos(), showMsg, font, getAlpha());
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
