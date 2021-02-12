package com.c4nn4.physics;

public interface Physical {
    void onCollision(Collision collision);

    double getX();

    double getY();

    void setPos(final double x, final double y);

    void setMass(final double g);

    double getVelX();

    double getVelY();

    void setMovingVector(double xVel, double yVel);

    double getSizeX();

    double getSizeY();

    double getBounciness();
}
