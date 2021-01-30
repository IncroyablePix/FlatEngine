package be.helmo.level;

public class Platform {
    private final int length;
    private final int x;
    private final int y;

    public Platform(int x, int y, int length) {
        this.length = length;
        this.x = x;
        this.y = y;
    }

    public double getMiddlePos() {
        return (this.x) + ((double) (this.length) / 2) - 0.5;
    }

    public int getLength() {
        return this.length;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public static boolean isPlatformWater(final Platform platform) {
        return platform != null && platform.x == 0 && platform.y == 0 && platform.length == 0;
    }
}
