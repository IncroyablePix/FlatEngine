package com.c4nn4.pix_engine.physics.environment;

import com.c4nn4.pix_engine.physics.coords.Coords;

public interface EntityMovementListener {
    void setEntityAttachement(final Entity entity);

    void onEntityMoved(Coords coords);
}
