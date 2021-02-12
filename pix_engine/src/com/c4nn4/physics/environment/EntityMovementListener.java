package com.c4nn4.physics.environment;

import com.c4nn4.physics.coords.Coords;
import com.c4nn4.physics.environment.Entity;

public interface EntityMovementListener {
    void setEntityAttachement(final Entity entity);

    void onEntityMoved(Coords coords);
}
