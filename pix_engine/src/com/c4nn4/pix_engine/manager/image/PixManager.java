package com.c4nn4.pix_engine.manager.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class PixManager {
    private static final PixManager pm = new PixManager();

    public static PixManager get() {
        return pm;
    }

    public static final Color WATER_COLOR = Color.decode("#00b0a5");
    public static final Color LAVA_COLOR = Color.decode("#ff8800");

    public static final int TILE_SIZE = 60;

    public static final String TILES = "/com/c4nn4/resources/Graphics/Tiles/pf_spritesheet.png";
    public static final String HELL_TILES = "/com/c4nn4/resources/Graphics/Tiles/hpf_spritesheet2.png";
    public static final String SKIES = "/com/c4nn4/resources/Graphics/BigImages/sky.png";
    public static final String SPACE = "/com/c4nn4/resources/Graphics/BigImages/MENU_BACK.png";
    public static final String HELL = "/com/c4nn4/resources/Graphics/BigImages/HELL_BACK.png";
    public static final String WATER = "/com/c4nn4/resources/Graphics/Tiles/water.png";
    public static final String LAVA = "/com/c4nn4/resources/Graphics/Tiles/lava.png";
    public static final String SUSHI = "/com/c4nn4/resources/Graphics/Tiles/sushi.png";
    public static final String PLAYER = "/com/c4nn4/resources/Graphics/Tiles/joe_spritesheet.png";
    public static final String CAT = "/com/c4nn4/resources/Graphics/Tiles/cat_spritesheet.png";
    public static final String BALL = "/com/c4nn4/resources/Graphics/Others/BALL.png";
    public static final String DIRT = "/com/c4nn4/resources/Graphics/Others/DIRT_PARTICLE.png";
    public static final String TREE = "/com/c4nn4/resources/Graphics/Tiles/tree.png";
    public static final String PASCAL = "/com/c4nn4/resources/Graphics/Others/PASCAL.png";
    public static final String STARS = "/com/c4nn4/resources/Graphics/Tiles/stars_spritesheet.png";
    public static final String TITLE = "/com/c4nn4/resources/Graphics/BigImages/TITLE.png";

    public static final BufferedImage NOT_FOUND = createDefault();

    private Map<String, SpriteSheet> spriteSheets;
    private Map<String, BigImage> bigImages;

    private static BufferedImage createDefault() {
        BufferedImage bi = new BufferedImage(TILE_SIZE, TILE_SIZE, BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < TILE_SIZE; x ++)
            for(int y = 0; y < TILE_SIZE; y ++)
                bi.setRGB(x, y, 0xFFFFFFFF);

        return bi;
    }

    private PixManager() {
        spriteSheets = new HashMap<>();
        bigImages = new HashMap<>();
    }

    public void loadBigImage(String path, String name) {
        if(bigImages.containsKey(name))
            return;

        BigImage ss = BigImage.loadBigImage(path, name);

        if(ss != null)
            bigImages.put(name, ss);
    }

    public void loadSpriteSheet(String path, String name, int width, int height) {
        if(spriteSheets.containsKey(name))
            return;

        SpriteSheet ss = SpriteSheet.loadSpriteSheet(path, name, width, height);

        if(ss != null)
            spriteSheets.put(name, ss);
    }

    public void unloadSpriteSheet(String name) {
        if(spriteSheets.containsKey(name))
            spriteSheets.remove(name);
    }

    public BufferedImage getSprite(String spriteSheetName, int index) {
        SpriteSheet ss = spriteSheets.get(spriteSheetName);

        return getSpriteSheetSprite(ss, index);
    }

    public BufferedImage[] getSprites(String spriteSheetName, int... indexes) {
        BufferedImage[] sprites;
        sprites = new BufferedImage[indexes.length];

        if(indexes.length > 0) {
            SpriteSheet ss = spriteSheets.get(spriteSheetName);
            for(int i = 0; i < indexes.length; i ++) {
                sprites[i] = getSpriteSheetSprite(ss, indexes[i]);
            }
        }

        return sprites;
    }

    private BufferedImage getSpriteSheetSprite(SpriteSheet ss, int index) {
        try {
            return ss.getSprite(index).getImage();
        }
        catch(SpriteNotFoundException | NullPointerException e) {
            return NOT_FOUND;
        }
    }

    public BufferedImage getBigImage(String name) {
        BigImage bi = bigImages.get(name);

        try {
            return bi.getImage();
        }
        catch(ImageNotFoundException | NullPointerException e) {
            return NOT_FOUND;
        }
    }
}
