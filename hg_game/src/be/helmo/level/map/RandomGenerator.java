package be.helmo.level.map;

import be.helmo.level.Platform;
import com.c4nn4.physics.ColParams;
import com.c4nn4.physics.environment.Tile;
import be.helmo.level.TileMap;
import com.c4nn4.manager.image.PixManager;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import static be.helmo.level.map.TileLabel.*;

public class RandomGenerator implements MapGenerator {


    private static final int    RANGE_MIN = 1,
                                RANGE_MAX = 2;

    private static final byte   TOP = (byte) 0b11000000,
                                BOTTOM = (byte) 0b00110000,
                                LEFT = (byte) 0b00001100,
                                RIGHT = (byte) 0b00000011;

    private final int level;
    private final int width;

    public RandomGenerator(int level, int width) {
        this.level = level;
        this.width = width;
    }

    @Override
    public void initMap(int amount, int avgLength, int maxBorder, List<Platform> platforms) {
        if (amount > 0) {
            Platform previousPlatform = new Platform(10, 0, 3);
            platforms.add(previousPlatform);

            if (amount > 1) {
                for (int i = 0; i < amount - 1; i++) {
                    Platform newPlatform;

                    do {
                        newPlatform = new Platform(MapGenerator.randomEx(0, width), previousPlatform.getY() + MapGenerator.randomEx(1, 4), avgLength + MapGenerator.randomEx(0, 2));
                    }
                    while (screenOverflow(newPlatform, maxBorder) || !isDistanceGood(newPlatform, previousPlatform, level));//to change

                    platforms.add(previousPlatform = newPlatform);
                }
            }
        }
    }

    private static boolean isDistanceGood(Platform p1, Platform p2, int level) {
        double dist = distanceBetweenTwoPlatforms(p1, p2);

        return getLevelDistance(level, RANGE_MAX) > dist && dist > getLevelDistance(level, RANGE_MIN);
    }

    private static boolean screenOverflow(Platform platform, int max) {
        return platform.getX() + platform.getLength() > max;
    }

    private static double getLevelDistance(int level, int range) {
        double pos = 0;
        switch (level / 4) {
            case 0: {
                pos = (range == RANGE_MAX ? 4.0 : 2.0);
                break;
            }
            case 1: {
                pos = (range == RANGE_MAX ? 5.0 : 3.0);
                break;
            }
            case 2: {
                pos = (range == RANGE_MAX ? 6.0 : 4.0);
                break;
            }
            case 3: {
                pos = (range == RANGE_MAX ? 8.0 : 6.0);
                break;
            }
            case 4: {
                pos = (range == RANGE_MAX ? 12.0 : 8.0);
                break;
            }
            default: {
                pos = (range == RANGE_MAX ? 15.0 : 10.0);
                break;
            }
        }
        return pos;
    }

    private static double distanceBetweenTwoPlatforms(Platform p1, Platform p2) { //double x1, double y1, double x2, double y2)
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    @Override
    public void computeTileMap(List<Platform> platforms, TileMap tileMap) {
        Iterator<Platform> it = platforms.iterator();
        byte col = ColParams.BOTTOM, noCol = ColParams.NO_COL;

        while (it.hasNext()) {
            Platform current = it.next();

            int xEdge = current.getX();
            int yEdge = current.getY();

            /*
                The top of a platform always overdraws whatever was there before
             */
            for (int i = xEdge, end = xEdge + current.getLength(); i < end; i++) {
                byte surrounded = isTileSurrounded(tileMap, i, yEdge);

                if (i == xEdge) {
                    setTile(((surrounded & TOP) == TOP ? HigherGroundsTileLabel.T_D_TOP_LEFT :
                            HigherGroundsTileLabel.T_TOP_LEFT), tileMap, col, i, yEdge);
                }
                else if (i == end - 1) {
                    setTile(((surrounded & TOP) == TOP ? HigherGroundsTileLabel.T_D_TOP_RIGHT :
                            HigherGroundsTileLabel.T_TOP_RIGHT), tileMap, col, i, yEdge);
                }
                else {
                    setTile(((surrounded & TOP) == TOP ? HigherGroundsTileLabel.T_D_TOP_MIDDLE :
                            HigherGroundsTileLabel.T_TOP_MIDDLE), tileMap, col, i, yEdge);
                }
            }

            if (yEdge > 0) {
                for (int y = yEdge - 1; y >= 0; y--) {
                    for (int x = xEdge, end = xEdge + current.getLength(); x < end; x++) {
                        Tile tile = tileMap.getTile(x, y);

                        if (tile == null || tile.isNoCol()) {//We don't want to overdraw a tile that's a platform
                            byte surrounded = isTileSurrounded(tileMap, x, y);

                            if (x == xEdge) {
                                if (surrounded != 0) {
                                    setTile(((surrounded & LEFT) == LEFT ? HigherGroundsTileLabel.T_DIRT_MIDDLE :
                                            HigherGroundsTileLabel.T_DIRT_LEFT), tileMap, noCol, x, y);

                                    reformatCells(tileMap, x, y, surrounded, (byte) 0xFF);
                                }
                                else {
                                    setTile(HigherGroundsTileLabel.T_DIRT_LEFT, tileMap, col, x, y);
                                }
                            }
                            else if (x == end - 1) {
                                if (surrounded != 0) {
                                    setTile(((surrounded & RIGHT) == RIGHT ? HigherGroundsTileLabel.T_DIRT_MIDDLE :
                                            HigherGroundsTileLabel.T_DIRT_RIGHT), tileMap, noCol, x, y);

                                    reformatCells(tileMap, x, y, surrounded, (byte) (0xFF));
                                }
                                else {
                                    setTile(HigherGroundsTileLabel.T_DIRT_RIGHT, tileMap, noCol, x, y);
                                }
                            }
                            else {
                                setTile(HigherGroundsTileLabel.T_DIRT_MIDDLE, tileMap, noCol, x, y);

                                if (surrounded != 0) {
                                    reformatCells(tileMap, x, y, surrounded, BOTTOM);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private byte isTileSurrounded(TileMap tileMap, int x, int y) {
        byte where = 0;

        if (tileMap.getTile(x - 1, y) != null)
            where |= LEFT;
        if (tileMap.getTile(x + 1, y) != null)
            where |= RIGHT;
        if (tileMap.getTile(x, y + 1) != null)
            where |= TOP;
        if (tileMap.getTile(x, y - 1) != null)
            where |= BOTTOM;

        return where;
    }

    private void reformatCells(final TileMap tileMap, int x, int y, byte surrounding, byte checkmask) {
        surrounding &= checkmask;
        if (surrounding != 0) {
            byte col = ColParams.BOTTOM, noCol = ColParams.NO_COL;

            if ((surrounding & LEFT) == LEFT) {
                if (tileMap.getTile(x - 1, y).getAbstractType() == HigherGroundsTileLabel.T_DIRT_RIGHT)
                    setTile(HigherGroundsTileLabel.T_DIRT_MIDDLE, tileMap, noCol, x - 1, y);
                else if (tileMap.getTile(x - 1, y).getAbstractType() == HigherGroundsTileLabel.T_TOP_RIGHT)
                    setTile(HigherGroundsTileLabel.T_D_TOP_RIGHT, tileMap, col, x - 1, y);
            }
            if ((surrounding & RIGHT) == RIGHT) {
                if (tileMap.getTile(x + 1, y).getAbstractType() == HigherGroundsTileLabel.T_DIRT_LEFT)
                    setTile(HigherGroundsTileLabel.T_DIRT_MIDDLE, tileMap, noCol, x + 1, y);
                else if (tileMap.getTile(x + 1, y).getAbstractType() == HigherGroundsTileLabel.T_TOP_LEFT)
                    setTile(HigherGroundsTileLabel.T_D_TOP_LEFT, tileMap, col, x + 1, y);
            }
            if ((surrounding & BOTTOM) == BOTTOM) {
                if (tileMap.getTile(x, y - 1).getAbstractType() == HigherGroundsTileLabel.T_TOP_LEFT)
                    setTile(HigherGroundsTileLabel.T_D_TOP_LEFT, tileMap, col, x, y - 1);
                else if (tileMap.getTile(x, y - 1).getAbstractType() == HigherGroundsTileLabel.T_TOP_RIGHT)
                    setTile(HigherGroundsTileLabel.T_D_TOP_RIGHT, tileMap, col, x, y - 1);
                else if (tileMap.getTile(x, y - 1).getAbstractType() == HigherGroundsTileLabel.T_TOP_MIDDLE)
                    setTile(HigherGroundsTileLabel.T_D_TOP_MIDDLE, tileMap, col, x, y - 1);
            }
        }
    }

    private void setTile(HigherGroundsTileLabel atl, TileMap tileMap, byte col, int x, int y) {
        BufferedImage tt = getTile(getTileIndex(atl));
        tileMap.setTile(new Tile(tt, col, atl, x, y, 1.0, 1.0), x, y);
    }

    public static TileLabel getTileIndex(HigherGroundsTileLabel type) {
        TileLabel tile;

        switch (type) {
            case T_D_TOP_LEFT: {
                tile = TOP_D_LEFT;
                break;
            }
            case T_D_TOP_MIDDLE: {
                int random = MapGenerator.randomEx(0, 2);
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
                int random = MapGenerator.randomEx(0, 2);
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
                int random = MapGenerator.randomEx(0, 2);
                if (random == 0)
                    tile = DIRT_LEFT_1;
                else
                    tile = DIRT_LEFT_2;
                break;
            }
            case T_DIRT_MIDDLE: {
                int random = MapGenerator.randomEx(0, 20);

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
                int random = MapGenerator.randomEx(0, 2);
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

    public BufferedImage getTile(TileLabel typeid) {
        PixManager pm = PixManager.get();

        BufferedImage image;

        image = pm.getSprite("TILES", typeid != null ? typeid.ordinal() : 0);

        return image;
    }
}
