package be.helmo.level.map;

import be.helmo.level.Platform;
import be.helmo.level.TileMap;

import java.util.List;
import java.util.Random;

public interface MapGenerator {
    void computeTileMap(List<Platform> platforms, TileMap tileMap);

    void initMap(int amount, int avgLength, int maxBorder, List<Platform> platforms);

    static int randomEx(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    static double randomExDouble(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }
}
