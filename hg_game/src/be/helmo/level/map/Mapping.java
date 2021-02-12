package be.helmo.level.map;

import com.c4nn4.game.level.Camera;
import com.c4nn4.graphics.render.Renderer;
import com.c4nn4.graphics.sprites.ActiveSprite;
import be.helmo.level.*;
import be.helmo.level.ornamentation.Ornament;
import be.helmo.level.ornamentation.Tree;
import com.c4nn4.manager.image.PixManager;
import com.c4nn4.physics.Collider;
import be.helmo.physics.TileCollider;
import com.c4nn4.physics.coords.Coords;

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

    private final Camera camera;

    private final MapGenerator generator;

    private final TileMap tileMap;
    private final Collider collider;

    private final List<Platform> platforms;
    private final List<Ornament> ornament;

    public Mapping(int width, int height, Camera camera, MapGenerator generator) {
        this.width = width;
        this.height = height;

        this.camera = camera;

        this.generator = generator;

        platforms = new LinkedList<>();

        tileMap = new TileMap(width, height, camera);
        collider = new TileCollider(tileMap);

        ornament = new ArrayList<>();
    }

    public void addPlatform(Platform platform) {
        if (platform != null) {
            platforms.add(platform);
            generator.computeTileMap(platforms, tileMap);
        }
    }

    public Collider getCollider() {
        return this.collider;
    }

    public void update() {
        for (Ornament value : ornament)
            value.update();
    }

    public void draw(final Renderer renderer) {
        tileMap.draw(renderer);

        for (Ornament value : ornament)
            value.draw(renderer, camera);
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

    public void addScrolling(double x, double y) {
        camera.increment(x, y);
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

    public void initMap(int amount, int avgLength, int maxBorder) {
        generator.initMap(amount, avgLength, maxBorder, platforms);
        generator.computeTileMap(platforms, tileMap);
        //createOrnament();
    }

    private void createOrnament() {
        int size = this.platforms.size();
        boolean[] platforms = new boolean[size];

        for (int i = 0; i < size; i++)
            platforms[i] = false;

        BufferedImage tree = PixManager.get().getSprite("TREE", 0);

        int maxTrees = size / 3;

        for (int i = 0; i < maxTrees; i++) {
            int rand;
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

    public boolean isLastPlatform(final Platform platform) {
        return platform != null && platform.equals(platforms.get(platforms.size() - 1));
    }
}
