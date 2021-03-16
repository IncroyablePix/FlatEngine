package com.c4nn4.level;

import com.c4nn4.game.level.Camera;
import com.c4nn4.pix_engine.graphics.layers.TileLayer;
import com.c4nn4.pix_engine.graphics.render.Renderer;
import com.c4nn4.pix_engine.main.screen.Screen;
import com.c4nn4.pix_engine.physics.environment.Tile;

import java.util.ArrayList;
import java.util.List;

import static com.c4nn4.level.HigherGroundsLevel.BOARD;
import static com.c4nn4.level.HigherGroundsLevel.BOARD_HEIGHT;

public class TileMap {

    private Tile[][] tiles;
    private final Camera camera;

    private final TileLayer layer;

    public TileMap(int width, int height, Camera camera) {
        if (camera == null)
            throw new IllegalArgumentException("Scrolling cannot be null");


        this.camera = camera;
        tiles = new Tile[width][height];

        layer = new TileLayer(Screen.WIN_HEIGHT, Screen.WIN_WIDTH);
    }

    public void draw(final Renderer renderer) {
        double xScrolling = camera.getX();
        double yScrolling = camera.getY();

        double maxY = (camera.getY() + BOARD_HEIGHT > tiles[0].length ? tiles[0].length : (camera.getY() + BOARD_HEIGHT));
        double maxX = (camera.getX() + BOARD > tiles.length ? tiles.length : (camera.getX() + BOARD));

        for (double x = (xScrolling == 0 ? xScrolling : xScrolling - 1); x < maxX + 1; x++) {
            for (double y = (yScrolling == 0 ? yScrolling : yScrolling - 1); y < maxY + 1; y++) {
                Tile tile = getTile((int) x, (int) y);

                if (tile != null) {
                    tile.draw(renderer, camera);
                }
            }
        }
    }

    public Tile[] searchByRange(double xMin, double xMax, double yMin, double yMax) {
        List<Tile> matchesList = new ArrayList<>();
        int[][] toExplore = toIndexRange(
                (int) Math.floor(xMin),
                (int) Math.ceil(xMax),
                (int) Math.floor(yMin),
                (int) Math.ceil(yMax));
        Tile[] matches;

        for (int i = 0; i < toExplore.length; i++) {
            Tile tile;
            if ((tile = getTile(toExplore[i][0], toExplore[i][1])) != null)
                matchesList.add(tile);
        }

        matches = new Tile[matchesList.size()];
        return matchesList.toArray(matches);
    }

    private int[][] toIndexRange(int xMin, int xMax, int yMin, int yMax) {
        int xRange = xMax - xMin;
        int yRange = yMax - yMin;
        int index = 0;
        int[][] positions = new int[xRange * yRange][2];

        for (int i = xMin; i < xMax; i++) {
            for (int j = yMin; j < yMax; j++) {
                positions[index][0] = i;
                positions[index][1] = j;
                index++;
            }
        }

        return positions;
    }

    public void setTile(Tile tile, int x, int y) {
        if ((tiles.length > x && x >= 0) && (tiles[x].length > y && y >= 0)) {
            tiles[x][y] = tile;
        }
    }

    public Tile getTile(int x, int y) {
        return ((tiles.length > x && x >= 0) && (tiles[x].length > y && y >= 0)) ? tiles[x][y] : null;
    }
}
