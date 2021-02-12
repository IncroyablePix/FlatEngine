package be.helmo.level.entities.particles.types;

import com.c4nn4.graphics.sprites.ActiveSprite;
import be.helmo.level.entities.particles.CircularVelSet;
import be.helmo.level.entities.particles.ParticlesInitialVelSet;
import com.c4nn4.manager.image.PixManager;

public class StarsParticle implements ParticleFabric {
    private final double x, y;
    private final ParticlesInitialVelSet initialVelSet;

    public StarsParticle(double x, double y) {
        this.x = x;
        this.y = y;

        this.initialVelSet = new CircularVelSet(1.0);
    }

    @Override
    public ActiveSprite getActiveSprite() {
        return new ActiveSprite(x, y, 3, PixManager.get().getSprites("STARS", 0, 1, 2, 3, 4, 5, 6, 7));//PixManager.get().dirt()
    }

    @Override
    public double getPosX() {
        return this.x;
    }

    @Override
    public double getPosY() {
        return this.y;
    }

    @Override
    public double getVelX() {
        return this.initialVelSet.getVelX();
    }

    @Override
    public double getVelY() {
        return this.initialVelSet.getVelY();
    }

    @Override
    public double getSizeX() {
        return 0.65;
    }

    @Override
    public double getSizeY() {
        return 0.65;
    }

    @Override
    public double getMass() { return 0.0; }
}
