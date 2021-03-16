package com.c4nn4.level.entities.particles;

import java.util.Random;

public interface ParticlesInitialVelSet {
    double getVelX();
    double getVelY();

    static double randomExDouble(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

    default int randomEx(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
