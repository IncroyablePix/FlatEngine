package be.helmo.level.entities.particles.types;

import be.helmo.graphics.sprites.ActiveSprite;
import be.helmo.level.GameLevel;
import be.helmo.level.entities.particles.Particles;
import be.helmo.level.entities.particles.ParticlesInitialVelSet;

public interface ParticleFabric {
    ActiveSprite getActiveSprite();
    double getPosX();
    double getPosY();
    double getVelX();
    double getVelY();
    double getSizeX();
    double getSizeY();
    double getMass();

    static void createParticles(ParticleType type, double x, double y, int amount, int lifeSpan, boolean physical, GameLevel level) {
        if(level != null && type != null)
            level.addParticles(new Particles(level, amount, lifeSpan, physical, createParticleFabric(type, x, y)));
    }

    static ParticleFabric createParticleFabric(ParticleType type, double x, double y) {
        ParticleFabric fabric = null;
        switch(type) {
            case DIRT_PARTICLE:
                fabric = new DirtParticle(x, y);
                break;
            case STAR_PARTICLE:
                fabric = new StarsParticle(x, y);
                break;
            case PASCAL_OP_PARTICLE:
                fabric = new PascalOPParticle(x, y);
                break;
            case BALL_PARTICLE:
                fabric = new BallParticle(x, y);
                break;
        }

        return fabric;
    }

    enum ParticleType {
        DIRT_PARTICLE,
        STAR_PARTICLE,
        PASCAL_OP_PARTICLE,
        BALL_PARTICLE
    }
}
