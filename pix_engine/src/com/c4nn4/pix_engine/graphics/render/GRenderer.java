package com.c4nn4.pix_engine.graphics.render;

import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.pix_engine.main.screen.GameWindow;
import com.c4nn4.pix_engine.manager.fonts.FontManager;
import com.c4nn4.pix_engine.manager.fonts.Fonts;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;

/**
 * Drawer for Graphics2D
 * Draws strings, images, or shapes onto a Graphics2D object.
 *
 * @author IncroyablePix
 */
public class GRenderer implements Renderer {

    final GameWindow gw;
    Graphics2D g2;

    Color color;
    Fonts font;
    float alpha;

    FontManager fonts;

    public GRenderer(final GameWindow gw, final Graphics2D g2, final FontManager fonts) throws IllegalArgumentException {
        if (g2 == null)
            throw new IllegalArgumentException("Graphics2D argument cannot be null for Drawer !");

        this.gw = gw;
        this.g2 = g2;

        this.fonts = fonts;

        color = Color.WHITE;
        font = Fonts.COURIER_T;
        alpha = 1.f;
    }

    public void setGraphics(final Graphics2D g2) {
        if (g2 != null) {
            this.g2 = g2;
        }
    }

    /**
     * Draws a string on the screen
     *
     * @param x    The X position on a 1920*1080 display
     * @param y    The Y position on a 1920*1080 display
     * @param text The text to draw
     * @param font Font to use - previous will be used if null
     */
    public void drawString(final int x, final int y, final String text, final Fonts font) {
        if(text != null && text.length() > 0) {
            setFont(font);
            setAlpha(1.f);
            g2.drawString(text, getPosX(x), getPosY(y));
        }
    }

    /**
     * Draws a string on the screen
     *
     * @param x     The X position on a 1920*1080 display
     * @param y     The Y position on a 1920*1080 display
     * @param text  The text to draw
     * @param font  Font to use - previous will be used if null
     * @param alpha Transparency
     */
    public void drawString(int x, int y, final String text, final Fonts font, final float alpha) {
        if(text != null && text.length() > 0) {
            setFont(font);
            setAlpha(alpha);

            for (String line : text.split("\n")) {
                g2.drawString(line, getPosX(x), getPosY(y));
                y -= g2.getFontMetrics().getHeight();
            }

            //g2.drawString(text, getPosX(x), getPosY(y));
        }
    }

    @Override
    public void drawString(int x, int y, final String text, final Fonts font, final float alpha, final float stroke, final Color strokeColor) {
        if(text != null && text.length() > 0) {
            if (stroke <= 0) {
                drawString(x, y, text, font, alpha);
            }
            else {
                String[] lines = text.split("\n");
                int roundedStroke = (int) Math.ceil(stroke);
                int width = FontManager.getTextWidth(text, font);
                int height = FontManager.getTextHeight(text, font) + roundedStroke;

                BufferedImage image = new BufferedImage(width, (height * lines.length) + roundedStroke, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = image.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Font cFont = fonts.getFont(font);
                g2.setStroke(new BasicStroke(stroke));

                for (int i = 0; i < lines.length; i++) {
                    g2.translate(0, height);
                    writeLine(lines[i], cFont, g2, this.color, strokeColor);
                }

                drawImage(image, x, y + height, image.getWidth(), image.getHeight(), 1.f);
            }
        }
    }

    private void writeLine(String text, Font font, Graphics2D g2, Color fillColor, Color strokeColor) {
        GlyphVector gv = font.createGlyphVector(g2.getFontRenderContext(), text);

        for(int i = 0, j = text.length(); i < j; i ++) {
            Shape shape = gv.getGlyphOutline(i);

            g2.setPaint(fillColor);
            g2.fill(shape);

            g2.setColor(strokeColor);
            g2.draw(shape);
        }
    }

    public void drawRectangle(final int x, final int y, final int width, final int height, final Color color) {
        setColor(color);
        g2.fillRect(getPosX(x), getPosY(y), getWidth(width), getHeight(height));
    }

    public void drawImage(final Image img, final int x, final int y, final int width, final int height, final float alpha) {
        setAlpha(alpha);
        g2.drawImage(img, getPosX(x), getPosY(y), getWidth(width), getHeight(height), null);
    }

    private int getPosX(int x) {
        return (int) (x * gw.getXFactor());
    }

    private int getPosY(int y) {
        return (int) ((Screen.WIN_HEIGHT - y) * gw.getYFactor());
    }

    private int getWidth(final int width) {
        return (int) Math.ceil((width) * gw.getXFactor());
    }

    private int getHeight(final int height) {
        return (int) Math.ceil((height) * gw.getYFactor());
    }

    public void setFont(final Fonts font) {
        if (this.font != font) {
            g2.setFont(fonts.getFont(this.font = font));
        }
    }

    public void setAlpha(final float alpha) {
        if (this.alpha != alpha && 1.f >= alpha && alpha >= 0.f) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha = alpha));
        }
    }

    public void setColor(final Color color) {
        //if((this.color == null || (this.color != null && !this.color.equals(color))) && color != null)
        g2.setColor(this.color = color);
    }

    public void refreshDrawer() {
        g2.setFont(fonts.getFont(this.font));
    }

    @Override
    public int[] getPixels() {
        return new int[0];
    }

    @Override
    public void render() {

    }

    @Override
    public void clear() {

    }
}
