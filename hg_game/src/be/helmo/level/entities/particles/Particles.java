package be.helmo.level.entities.particles;

import be.helmo.graphics.Renderer;
import be.helmo.graphics.TempElement;
import be.helmo.level.Camera;
import be.helmo.level.GameLevel;
import be.helmo.level.entities.particles.types.ParticleFabric;
import be.helmo.main.GameThread;
import be.helmo.physics.Collider;

public class Particles implements TempElement {
    private final Camera camera;
    private final Particle[] particles;
    private final Collider collider;

    private int length;
    private int ticks;

    private float alpha;

    public Particles(final GameLevel level, int amount, int lifeSpan, boolean physical, ParticleFabric fabric) {
        this.particles = new Particle[amount];

        this.camera = level.getCamera();
        this.collider = level.getCollider();

        this.ticks = GameThread.ticks();
        this.length = Math.abs(lifeSpan);

        this.alpha = 1.f;

        createParticles(level, amount, physical, fabric);
    }

    private void createParticles(GameLevel level, int amount, boolean physical, ParticleFabric type) {
        for(int i = 0; i < amount; i ++) {
            particles[i] = new Particle(level, type.getActiveSprite(), type.getPosX(), type.getPosY(), type.getSizeX(), type.getSizeY(), physical);
            particles[i].setMass(type.getMass());
            particles[i].setMovingVector(type.getVelX(), type.getVelY());
        }
    }

    public void update() {
        updateAlpha();
        for(Particle particle : particles) {
            if(particle != null) {
                particle.setAlpha(alpha);
                particle.update(collider);
            }
        }
    }

    private int getTicks() {
        return GameThread.ticksFrom(ticks);
    }

    private void updateAlpha() {
        int maxTick = 30;
        int currentTick = getTicks();

        if (length == currentTick) {
            alpha = 0.f;
        }
        else if (length != -1 && length - maxTick < currentTick) {
            int delta = length - currentTick;
            alpha = (float) delta / maxTick;
        }
        else if (currentTick < maxTick) {
            alpha = (float) currentTick / maxTick;
        }
        else {
            alpha = 1.f;
        }
    }

    @Override
    public boolean spoiled() {
        return length != -1 && GameThread.ticksFrom(ticks) >= length;
    }

    @Override
    public void resetStartingTick() {
        this.ticks = GameThread.ticks();
    }

    @Override
    public void setLength(int ticks) {
        length = ticks;
    }

    public void draw(Renderer renderer) {
        for(Particle particle : particles) {
            if(particle != null)
                particle.draw(renderer, camera);
        }
    }
}