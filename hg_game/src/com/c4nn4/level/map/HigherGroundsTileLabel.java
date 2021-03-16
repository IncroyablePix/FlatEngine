package com.c4nn4.level.map;

import com.c4nn4.pix_engine.physics.environment.AbstractTileLabel;

/**
 * AbstractTiles
 * <p>
 * There are multiple tiles for each type, but sometimes we need to know which type it is
 *
 * @author IncroyablePix
 */
public enum HigherGroundsTileLabel implements AbstractTileLabel {
    T_TOP_LEFT,
    T_TOP_MIDDLE,
    T_TOP_RIGHT,

    T_D_TOP_LEFT,
    T_D_TOP_MIDDLE,
    T_D_TOP_RIGHT,

    T_DIRT_LEFT,
    T_DIRT_MIDDLE,
    T_DIRT_RIGHT
}
