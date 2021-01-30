package be.helmo.level.map;

import be.helmo.level.Platform;

import java.util.List;

public class RandomGenerator/* implements MapGenerator*/ {


    /*private static final int    RANGE_MIN = 1,
                                RANGE_MAX = 2;

    @Override
    public void initMap(int amount, int avgLength, int maxBorder, int level, List<Platform> platforms) {
        if (amount > 0) {
            Platform previousPlatform = new Platform(10, 0, 3);
            platforms.add(previousPlatform);

            if (amount > 1) {
                for (int i = 0; i < amount - 1; i++) {
                    Platform newPlatform;

                    do {
                        newPlatform = new Platform(randomEx(0, width), previousPlatform.getY() + MapGenerator.randomEx(1, 4), avgLength + MapGenerator.randomEx(0, 2));
                    }
                    while (screenOverflow(newPlatform, maxBorder) || !isDistanceGood(newPlatform, previousPlatform, level));//to change

                    platforms.add(previousPlatform = newPlatform);
                }
            }

            computeTileMap();

            createOrnament();
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
    public void computeTileMap() {

    }*/
}
