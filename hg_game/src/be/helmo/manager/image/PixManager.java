package be.helmo.manager.image;

import be.helmo.enums.AbstractTiles;
import be.helmo.enums.Directions;
import be.helmo.enums.TileTypes;
import be.helmo.graphics.TileType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

import static be.helmo.enums.TileTypes.*;

public class PixManager {
    private static final PixManager pm = new PixManager();

    public static PixManager get() {
        return pm;
    }

    /*private final SpriteSheet tiles = SpriteSheet.loadSpriteSheet("/be/helmo/resources/Graphics/Tiles/pf_spritesheet.png");
    public Sprite grass = new Sprite(TILE_SIZE, TILE_SIZE, 0, 0, tiles);*/

    public static final Color WATER_COLOR = Color.decode("#00b0a5");

    private static final int    SHEET_ROWS = 4,
                                SHEET_COLS = 4;

    public static final int TILE_SIZE = 60;

    public static final String TILES = "/be/helmo/resources/Graphics/Tiles/pf_spritesheet.png";
    public static final String SKIES = "/be/helmo/resources/Graphics/BigImages/sky.png";
    public static final String SPACE = "/be/helmo/resources/Graphics/BigImages/MENU_BACK.png";
    public static final String HELL = "/be/helmo/resources/Graphics/BigImages/HELL_BACK.png";
    public static final String WATER = "/be/helmo/resources/Graphics/Tiles/water.png";
    public static final String SUSHI = "/be/helmo/resources/Graphics/Tiles/sushi.png";
    public static final String PLAYER = "/be/helmo/resources/Graphics/Tiles/joe_spritesheet.png";
    public static final String CAT = "/be/helmo/resources/Graphics/Tiles/cat_spritesheet.png";
    public static final String BALL = "/be/helmo/resources/Graphics/Others/BALL.png";
    public static final String DIRT = "/be/helmo/resources/Graphics/Others/DIRT_PARTICLE.png";
    public static final String TREE = "/be/helmo/resources/Graphics/Tiles/tree.png";
    public static final String PASCAL = "/be/helmo/resources/Graphics/Others/berfin.png";
    public static final String STARS = "/be/helmo/resources/Graphics/Tiles/stars_spritesheet.png";

    public static final BufferedImage NOT_FOUND = createDefault();

    private final TileType[] tileSet = new TileType[SHEET_ROWS * SHEET_COLS];
    private BufferedImage sky;
    private final BufferedImage[] water = new BufferedImage[4];
    private BufferedImage[][] player;
    private BufferedImage[][] cat;
    private BufferedImage ball;
    private BufferedImage dirt;
    private BufferedImage sushi;
    private BufferedImage tree;
    private BufferedImage pascal;

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

        loadTiles();
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

    private void loadTiles() {
        //---Tiles
        BufferedImage[][] tileSprites = Content.load(TILES, TILE_SIZE, TILE_SIZE);

        for (int i = 0; i < SHEET_COLS; i++) {
            for (int j = 0; j < SHEET_ROWS; j++) {
                AbstractTiles type;
                if (i == 0) {
                    if (j == 0)
                        type = AbstractTiles.T_TOP_LEFT;
                    else if (j == 1)
                        type = AbstractTiles.T_D_TOP_LEFT;
                    else
                        type = AbstractTiles.T_DIRT_LEFT;
                }
                else if (i == SHEET_COLS - 1) {
                    if (j == 0)
                        type = AbstractTiles.T_TOP_RIGHT;
                    else if (j == 1)
                        type = AbstractTiles.T_D_TOP_RIGHT;
                    else
                        type = AbstractTiles.T_DIRT_RIGHT;
                }
                else {
                    if (j == 0)
                        type = AbstractTiles.T_TOP_MIDDLE;
                    else if (j == 1)
                        type = AbstractTiles.T_D_TOP_MIDDLE;
                    else
                        type = AbstractTiles.T_DIRT_MIDDLE;
                }

                tileSet[i * SHEET_ROWS + j] = new TileType(tileSprites[i][j], (j == 0 || j == 1) ? TileType.TYPE_BLOCKED : TileType.TYPE_NO_COL, type);
            }
        }
    }

    public TileType getTile(TileTypes typeid) {
        TileType type;

        switch (typeid) {
            case TOP_LEFT: {
                type = tileSet[0];
                break;
            }
            case TOP_MIDDLE_1: {
                type = tileSet[4];
                break;
            }
            case TOP_MIDDLE_2: {
                type = tileSet[8];
                break;
            }
            case TOP_RIGHT: {
                type = tileSet[12];
                break;
            }

            case TOP_D_LEFT: {
                type = tileSet[1];
                break;
            }
            case TOP_D_MIDDLE_1: {
                type = tileSet[5];
                break;
            }
            case TOP_D_MIDDLE_2: {
                type = tileSet[9];
                break;
            }
            case TOP_D_RIGHT: {
                type = tileSet[13];
                break;
            }

            case DIRT_LEFT_1: {
                type = tileSet[2];
                break;
            }
            case DIRT_MIDDLE_1: {
                type = tileSet[6];
                break;
            }
            case DIRT_MIDDLE_2: {
                type = tileSet[10];
                break;
            }
            case DIRT_RIGHT_1: {
                type = tileSet[14];
                break;
            }

            case DIRT_LEFT_2: {
                type = tileSet[3];
                break;
            }
            case DIRT_MIDDLE_3: {
                type = tileSet[7];
                break;
            }
            case DIRT_MIDDLE_4: {
                type = tileSet[11];
                break;
            }
            case DIRT_RIGHT_2:
            default: {
                type = tileSet[15];
                break;
            }
        }

        return type;
    }

    public static TileTypes getTileIndex(AbstractTiles type) {
        TileTypes tile;

        switch (type) {
            case T_D_TOP_LEFT: {
                tile = TOP_D_LEFT;
                break;
            }
            case T_D_TOP_MIDDLE: {
                int random = randomEx(0, 2);
                if (random == 0)
                    tile = TOP_D_MIDDLE_1;
                else
                    tile = TOP_D_MIDDLE_2;
                break;
            }
            case T_D_TOP_RIGHT: {
                tile = TOP_D_RIGHT;
                break;
            }
            case T_TOP_LEFT: {
                tile = TOP_LEFT;
                break;
            }
            case T_TOP_MIDDLE: {
                int random = randomEx(0, 2);
                if (random == 0)
                    tile = TOP_MIDDLE_1;
                else
                    tile = TOP_MIDDLE_2;
                break;
            }
            case T_TOP_RIGHT: {
                tile = TOP_RIGHT;
                break;
            }
            case T_DIRT_LEFT: {
                int random = randomEx(0, 2);
                if (random == 0)
                    tile = DIRT_LEFT_1;
                else
                    tile = DIRT_LEFT_2;
                break;
            }
            case T_DIRT_MIDDLE: {
                int random = randomEx(0, 20);

                if (random == 0)
                    tile = DIRT_MIDDLE_3;
                else if (random < 10)
                    tile = DIRT_MIDDLE_2;
                else if (random < 18)
                    tile = DIRT_MIDDLE_1;
                else
                    tile = DIRT_MIDDLE_4;

                break;
            }
            case T_DIRT_RIGHT: {
                int random = randomEx(0, 2);
                if (random == 0)
                    tile = DIRT_RIGHT_1;
                else {
                    tile = DIRT_RIGHT_2;
                }

                break;
            }
            default: {
                tile = DIRT_MIDDLE_1;
                break;
            }
        }

        return tile;
    }

    public static int randomEx(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
