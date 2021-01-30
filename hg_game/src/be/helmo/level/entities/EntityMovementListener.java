package be.helmo.level.entities;

import be.helmo.physics.coords.Coords;

public interface EntityMovementListener {
    void setEntityAttachement(final Entity entity);

    void onEntityMoved(Coords coords);
}
