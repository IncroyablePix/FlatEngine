package be.helmo.level.entities;

import be.helmo.graphics.Renderer;
import be.helmo.level.Camera;

public interface Pickupable {
    void onPickUpPickup(Entity entity);
}
