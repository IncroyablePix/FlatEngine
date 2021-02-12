package com.c4nn4.physics;

public interface Collider {
    boolean isOnTopOfSomething(Physical physical);

    PosAndVel checkX(Physical physical, ColParams colParams);

    PosAndVel checkY(Physical physical, ColParams colParams);
}