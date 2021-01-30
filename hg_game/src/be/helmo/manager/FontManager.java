package be.helmo.manager;

import be.helmo.main.screen.Screen;
import be.helmo.main.screen.ResolutionChangedListener;
import be.helmo.manager.image.Content;

import java.awt.*;
import java.io.InputStream;
import java.util.Arrays;

/**
 * FontRepo
 * <p>
 * Contains all fonts used in the game.
 * The fonts are preloaded with a certain size for no dynamic resizing.
 * <p>
 * Fonts are also scalable in order for them to fit the screen size.
 * The static "ORIGINALS" array is useful if you want to use a
 * different alignment (centered, rightened) on a standardized basis
 * (as are all positions).
 * <p>
 * <p>
 * N.-B.: To add a font, add it to the "ORIGINALS" array.
 *
 * @author IncroyablePix
 */
public class FontManager implements ResolutionChangedListener {

    public static final int TINY_SIZE = 20,
            TEXT_SIZE = 38,
            MENU_SIZE = 66;

    public static final byte COURIER_T = 0,
            COURIER_S = 1,
            COURIER_B = 2,
            ORATOR_S = 3,
            ORATOR_B = 4,
            RIGHTEOUS_S = 5,
            RIGHTEOUS_B = 6,
            CHAMP_LIMO_S = 7,
            CHAMP_LIMO_B = 8,
            ORATOR_T = 9;

    private static final Font[] ORIGINALS = {
            new Font("Courier New", Font.PLAIN, TINY_SIZE),
            new Font("Courier New", Font.PLAIN, TEXT_SIZE),
            new Font("Courier New", Font.PLAIN, MENU_SIZE),
            createFont("OratorStd", TEXT_SIZE),
            createFont("OratorStd", MENU_SIZE),
            createFont("Righteous-Regular", TEXT_SIZE),
            createFont("Righteous-Regular", MENU_SIZE),
            createFont("ChampagneLimousines", TEXT_SIZE),
            createFont("ChampagneLimousines", 98),
            createFont("OratorStd", TINY_SIZE),
    };

    private final Font[] fonts;

    /**
     * Creates a FontRepo instance that creates a new array based on the static one.
     */
    public FontManager() {
        fonts = Arrays.copyOf(ORIGINALS, ORIGINALS.length);
    }

    /**
     * Get a font by its ID
     *
     * @param fontid The ID of the Font
     * @return The Font
     */
    public Font getFont(final byte fontid) {
        return fonts.length > fontid && fontid >= 0 ? fonts[fontid] : fonts[0];
    }

    /**
     * Scales font based according to a scaling ratio.
     *
     * @param scale The ratio - 1.f is the basic size.
     */
    public void scaleFonts(final float scale) {
        for (byte i = 0; i < fonts.length; i++) {
            fonts[i] = ORIGINALS[i].deriveFont(Font.TRUETYPE_FONT, (int) (scale * ORIGINALS[i].getSize()));
        }
    }

    private static Font createFont(String fontName, int size) {
        Font newFont = null;
        Font ttfBase = null;
        try {
            InputStream myStream = null;
            myStream = Content.class.getResourceAsStream("/be/helmo/resources/Fonts/" + fontName + ".ttf");
            ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            newFont = ttfBase.deriveFont(Font.PLAIN, size);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.printf("Erreur de chargement de la police \"%s\".\n.", fontName);
        }
        return newFont;
    }

    /**
     * Fetches for the Font of the "ORIGINALS" array basd on its ID .
     *
     * @param fontid The ID.
     * @return The Font.
     */
    public static Font getOriginalFont(final byte fontid) {
        return ORIGINALS.length > fontid && fontid >= 0 ? ORIGINALS[fontid] : ORIGINALS[0];
    }

    /**
     * Gets the centered position for a given String and Font around a position.
     *
     * @param text The String to center.
     * @param font The Font in which the String'll be written.
     * @param x    The x position around which the text has to be written.
     * @return The new x position to write the text at.
     */
    public static int getCenteredPosition(final String text, final byte font, final int x) {
        Canvas c = new Canvas();
        FontMetrics fm = c.getFontMetrics(getOriginalFont(font));

        return (x - (fm.stringWidth(text) / 2));
    }


    /**
     * Gets the right alignment position for a given String and Font next to a position.
     *
     * @param text The String to align.
     * @param font The Font in which the String'll be written.
     * @param x    The x position next to which the text has to be written.
     * @return The new x position to write the text at.
     */
    public static int getRightenedPosition(final String text, final byte font, final int x) {
        Canvas c = new Canvas();
        FontMetrics fm = c.getFontMetrics(getOriginalFont(font));

        return (x - fm.stringWidth(text));
    }

    @Override
    public void onResolutionChanged(boolean fullscreen, int x, int y) {
        float xFactor = (float) x / Screen.WIN_WIDTH;
        float yFactor = (float) y / Screen.WIN_HEIGHT;

        scaleFonts(Math.min(xFactor, yFactor));
    }
}
