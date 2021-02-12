package com.c4nn4.physics;

import com.c4nn4.physics.environment.Tile;

public class Collision {
    public enum CollisionDirection {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    private final Tile tile;
    private final CollisionDirection direction;

    public Collision(Tile tile, CollisionDirection direction) {
        this.tile = tile;
        this.direction = direction;
    }

    public Tile getTile() {
        return tile;
    }

    public CollisionDirection getDirection() {
        return direction;
    }
}
