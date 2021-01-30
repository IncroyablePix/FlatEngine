package be.helmo.level.map;

import be.helmo.enums.AbstractTiles;
import be.helmo.graphics.Renderer;
import be.helmo.graphics.TileType;
import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.level.*;
import be.helmo.level.ornamentation.Ornament;
import be.helmo.level.ornamentation.Tree;
import be.helmo.manager.image.PixManager;
import be.helmo.physics.Collider;
import be.helmo.physics.TileCollider;
import be.helmo.physics.coords.Coords;

import java.awt.image.BufferedImage;
import java.util.*;

import static be.helmo.level.map.MapGenerator.randomEx;

public class Mapping {
    private static final byte   TOP = (byte) 0b11000000,
                                BOTTOM = (byte) 0b00110000,
                                LEFT = (byte) 0b00001100,
                                RIGHT = (byte) 0b00000011;

    private static final int    RANGE_MIN = 1,
                                RANGE_MAX = 2;

    public static final int     GROUND_POS = 0;

    private final int width;
    private final int height;

    private final GameLevel gl;
    private final Camera camera;

    private final MapGenerator generator;

    private final TileMap tileMap;
    private final Collider collider;
    private final Water water;

    private final List<Platform> platforms;
    private final List<Ornament> ornament;

    public Mapping(GameLevel gl, int width, int height, Camera camera, MapGenerator generator) {
        this.width = width;
        this.height = height;

        this.gl = gl;
        this.camera = camera;

        this.generator = generator;

        platforms = new LinkedList<>();

        tileMap = new TileMap(width, height, camera);
        collider = new TileCollider(tileMap);

        water = new Water(width);
        water.toggleWaterMovement(true);

        ornament = new ArrayList<>();
    }

    public void addPlatform(Platform platform) {
        if (platform != null) {
            platforms.add(platform);
            computeTileMap();
        }
    }

    public Collider getCollider() {
        return this.collider;
    }

    public void update() {
        if (water != null)
            water.update();

        if (this.ornament != null) {
            Iterator<Ornament> it = ornament.iterator();

            while (it.hasNext())
                it.next().update();
        }

    }

    public void draw(final Renderer renderer) {
        tileMap.draw(renderer);

        if (this.ornament != null) {
            Iterator<Ornament> it = ornament.iterator();

            while (it.hasNext())
                it.next().draw(renderer, camera);
        }

        water.draw(renderer, camera);
    }

    public void setScrolling(double x, double y) {
        camera.setPos(x, y);
    }

    public double getXScrolling() {
        return camera.getX();
    }

    public double getYScrolling() {
        return camera.getY();
    }

    public void toggleWaterMovement(boolean moving) {
        water.toggleWaterMovement(moving);
    }

    public double getWaterLevel() {
        return water.getWaterLevel();
    }

    public void addScrolling(double x, double y) {
        camera.increment(x, y);
    }

    /**
     * Gives a specific tile to every position on the board that needs one, while keeping it coherent
     * in its structure.
     */
    private void computeTileMap() {
        Iterator<Platform> it = platforms.iterator();
        PixManager pm = PixManager.get();

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
                    TileType tt = pm.getTile(PixManager.getTileIndex(
                            ((surrounded & TOP) == TOP) ? AbstractTiles.T_D_TOP_LEFT :
                                    AbstractTiles.T_TOP_LEFT));
                    tileMap.setTile(new Tile(tt, i, yEdge, 1.0, 1.0), i, yEdge);
                }
                else if (i == end - 1) {
                    TileType tt = pm.getTile(PixManager.getTileIndex(
                            ((surrounded & TOP) == TOP) ? AbstractTiles.T_D_TOP_RIGHT :
                                    AbstractTiles.T_TOP_RIGHT));
                    tileMap.setTile(new Tile(tt, i, yEdge, 1.0, 1.0), i, yEdge);
                }
                else {
                    TileType tt = pm.getTile(PixManager.getTileIndex(
                            ((surrounded & TOP) == TOP) ? AbstractTiles.T_D_TOP_MIDDLE :
                                    AbstractTiles.T_TOP_MIDDLE));
                    tileMap.setTile(new Tile(tt, i, yEdge, 1.0, 1.0), i, yEdge);
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
                                    TileType tt = pm.getTile(PixManager.getTileIndex(
                                            ((surrounded & LEFT) == LEFT) ? AbstractTiles.T_DIRT_MIDDLE :
                                                    AbstractTiles.T_DIRT_LEFT));
                                    tileMap.setTile(new Tile(tt, x, y, 1.0, 1.0), x, y);

                                    reformatCells(tileMap, x, y, surrounded, (byte) 0xFF);
                                }
                                else {
                                    TileType tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_DIRT_LEFT));
                                    tileMap.setTile(new Tile(tt, x, y, 1.0, 1.0), x, y);
                                }
                            }
                            else if (x == end - 1) {
                                if (surrounded != 0) {
                                    TileType tt = pm.getTile(PixManager.getTileIndex(
                                            ((surrounded & RIGHT) == RIGHT ? AbstractTiles.T_DIRT_MIDDLE :
                                                    AbstractTiles.T_DIRT_RIGHT)));
                                    tileMap.setTile(new Tile(tt, x, y, 1.0, 1.0), x, y);

                                    reformatCells(tileMap, x, y, surrounded, (byte) (0xFF));
                                }
                                else {
                                    TileType tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_DIRT_RIGHT));
                                    tileMap.setTile(new Tile(tt, x, y, 1.0, 1.0), x, y);
                                }
                            }
                            else {
                                TileType tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_DIRT_MIDDLE));
                                tileMap.setTile(new Tile(tt, x, y, 1.0, 1.0), x, y);

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

    private void reformatCells(final TileMap tileMap, int x, int y, byte surrounding, byte checkmask) {
        surrounding &= checkmask;
        if (surrounding != 0) {
            PixManager pm = PixManager.get();

            if ((surrounding & LEFT) == LEFT) {
                if (tileMap.getTile(x - 1, y).getAbstractType() == AbstractTiles.T_DIRT_RIGHT) {
                    TileType tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_DIRT_MIDDLE));
                    tileMap.setTile(new Tile(tt, x - 1, y, 1.0, 1.0), x - 1, y);
                }
                else if (tileMap.getTile(x - 1, y).getAbstractType() == AbstractTiles.T_TOP_RIGHT) {
                    TileType tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_D_TOP_RIGHT));
                    tileMap.setTile(new Tile(tt, x - 1, y, 1.0, 1.0), x - 1, y);
                }
            }
            if ((surrounding & RIGHT) == RIGHT) {
                if (tileMap.getTile(x + 1, y).getAbstractType() == AbstractTiles.T_DIRT_LEFT) {
                    TileType tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_DIRT_MIDDLE));
                    tileMap.setTile(new Tile(tt, x + 1, y, 1.0, 1.0), x + 1, y);
                }
                else if (tileMap.getTile(x + 1, y).getAbstractType() == AbstractTiles.T_TOP_LEFT) {
                    TileType tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_D_TOP_LEFT));
                    tileMap.setTile(new Tile(tt, x + 1, y, 1.0, 1.0), x + 1, y);
                }
            }
            if ((surrounding & BOTTOM) == BOTTOM) {
                if (tileMap.getTile(x, y - 1).getAbstractType() == AbstractTiles.T_TOP_LEFT) {
                    TileType tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_D_TOP_LEFT));
                    tileMap.setTile(new Tile(tt, x, y - 1, 1.0, 1.0), x, y - 1);
                }
                else if (tileMap.getTile(x, y - 1).getAbstractType() == AbstractTiles.T_TOP_RIGHT) {
                    TileType tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_D_TOP_RIGHT));
                    tileMap.setTile(new Tile(tt, x, y - 1, 1.0, 1.0), x, y - 1);
                }
                else if (tileMap.getTile(x, y - 1).getAbstractType() == AbstractTiles.T_TOP_MIDDLE) {
                    TileType tt = pm.getTile(PixManager.getTileIndex(AbstractTiles.T_D_TOP_MIDDLE));
                    tileMap.setTile(new Tile(tt, x, y - 1, 1.0, 1.0), x, y - 1);
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

    public Platform getGroundPos(double x, double y) {
        Platform ground = new Platform(0, 0, 0);
        ListIterator<Platform> it = platforms.listIterator(platforms.size());

        while (it.hasPrevious()) {
            Platform current = it.previous();

            int x1 = current.getX();
            int x2 = current.getLength() + x1;
            int y1 = current.getY() + 1;
            //Debug.log("(" + x + ", " + y + ") => [" + x1 +" - "+ x2 + ", " + y1 + "]");

            if (y >= y1 && x2 - 0.1 >= x && x >= x1 - 0.4) {
                //Debug.log("Matching ground");
                ground = current;
                break;
            }
        }

        return ground;
    }

    public Coords getFirstPlatformMiddlePos() {
        Platform platform = platforms.get(0);
        return new Coords(platform.getMiddlePos(), platform.getY());
    }

    public Coords getLastPlatformMiddlePos() {
        Platform platform = platforms.get(platforms.size() - 1);
        return new Coords(platform.getMiddlePos(), platform.getY());
    }

    public void initMap(int amount, int avgLength, int maxBorder, int level) {
        if (amount > 0) {
            Platform previousPlatform = new Platform(10, 0, 3);
            platforms.add(previousPlatform);

            if (amount > 1) {
                for (int i = 0; i < amount - 1; i++) {
                    Platform newPlatform;

                    do {
                        newPlatform = new Platform(randomEx(0, width), previousPlatform.getY() + randomEx(1, 4), avgLength + randomEx(0, 2));
                    }
                    while (screenOverflow(newPlatform, maxBorder) || !isDistanceGood(newPlatform, previousPlatform, level));//to change

                    platforms.add(previousPlatform = newPlatform);
                }
            }

            computeTileMap();

            //createOrnament();
        }
    }

    private void createOrnament() {
        int size = this.platforms.size();
        boolean[] platforms = new boolean[size];

        for (int i = 0; i < size; i++)
            platforms[i] = false;

        BufferedImage tree = PixManager.get().getSprite("TREE", 0);

        int maxTrees = size / 3;

        for (int i = 0; i < maxTrees; i++) {
            int rand = -1;
            do {
                rand = randomEx(1, size - 1);
            }
            while (platforms[rand]);

            double x = this.platforms.get(rand).getX();
            double x2 = this.platforms.get(rand).getLength();
            double y = this.platforms.get(rand).getY();

            x = MapGenerator.randomExDouble(x, x + x2 - 1.5);

            ornament.add(new Tree(new ActiveSprite(x, y, 1, tree), x, y));

            platforms[rand] = true;
        }

        ornament.sort(Comparator.naturalOrder());
    }

    public Coords[] getEmptyRandomPlatforms(int amount) {
        int size = this.platforms.size();
        int maxPos = Math.min(amount, size - 2);

        Coords[] coords = new Coords[maxPos];

        boolean[] platforms = new boolean[size];

        for (int i = 0; i < size; i++)
            platforms[i] = false;

        for (int i = 0; i < maxPos; i++) {
            int rand;
            do {
                rand = randomEx(1, size - 1);
            }
            while (platforms[rand]);

            coords[i] = new Coords(this.platforms.get(rand).getMiddlePos(), this.platforms.get(rand).getY());
            platforms[rand] = true;
        }

        return coords;
    }

    private static boolean isDistanceGood(Platform p1, Platform p2, int level) {
        double dist = distanceBetweenTwoPlatforms(p1, p2);

        return getLevelDistance(level, RANGE_MAX) > dist && dist > getLevelDistance(level, RANGE_MIN);
    }

    private static boolean screenOverflow(Platform platform, int max) {
        return platform.getX() + platform.getLength() > max;
    }

    private static double distanceBetweenTwoPlatforms(Platform p1, Platform p2) { //double x1, double y1, double x2, double y2)
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
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

    public boolean isLastPlatform(final Platform platform) {
        return platform != null && platforms != null && platform.equals(platforms.get(platforms.size() - 1));
    }

    public void increaseWaterVel(double vel) {
        water.increaseWaterVel(vel);
    }

    private void gameOver() {
        gl.gameOver();
    }
}
