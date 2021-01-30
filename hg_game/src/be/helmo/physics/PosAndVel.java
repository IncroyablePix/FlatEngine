package be.helmo.physics;

class PosAndVel {
    private final double pos, vel;

    public PosAndVel(double pos, double vel) {
        this.pos = pos;
        this.vel = vel;
    }

    public double getPos() {
        return pos;
    }

    public double getVel() {
        return vel;
    }
}