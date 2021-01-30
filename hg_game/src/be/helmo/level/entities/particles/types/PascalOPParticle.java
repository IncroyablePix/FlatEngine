package be.helmo.level.entities.particles.types;

import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.level.entities.particles.ParticlesInitialVelSet;
import be.helmo.level.entities.particles.SimpleUpwardsVelSet;
import be.helmo.manager.image.PixManager;

public class PascalOPParticle implements ParticleFabric {
    private final double x, y;
    private final ParticlesInitialVelSet initialVelSet;

    public PascalOPParticle(double x, double y) {
        this.x = x;
        this.y = y;

        this.initialVelSet = new SimpleUpwardsVelSet();
    }

    @Override
    public ActiveSprite getActiveSprite() {
        return new ActiveSprite(x, y, 1, PixManager.get().getSprite("PASCAL", 0));
        //particles[i] = new Particle(level, , x, y, 1.0, 1.25);
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
        return 1.0;
    }

    @Override
    public double getSizeY() {
        return 1.25;
    }

    @Override
    public double getMass() { return 15.0; }
}
