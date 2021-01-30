package be.helmo.physics;

public interface Collider {
    boolean isOnTopOfSomething(Physical physical);

    PosAndVel checkX(Physical physical);

    PosAndVel checkY(Physical physical);
}
