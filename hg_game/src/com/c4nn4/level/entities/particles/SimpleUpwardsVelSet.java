package com.c4nn4.level.entities.particles;

public class SimpleUpwardsVelSet implements ParticlesInitialVelSet {
    @Override
    public double getVelX() {
        return ParticlesInitialVelSet.randomExDouble(-3.0, 3.0);
    }

    @Override
    public double getVelY() {
        return ParticlesInitialVelSet.randomExDouble(0.0, 7.5);
    }
}
