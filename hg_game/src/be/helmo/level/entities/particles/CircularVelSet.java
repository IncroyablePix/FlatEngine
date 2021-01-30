package be.helmo.level.entities.particles;

public class CircularVelSet implements ParticlesInitialVelSet {
    private static int count = 0;
    private final double[] angles = setupAngles();

    private final double speed;

    public CircularVelSet(double speed) {
        this.speed = speed;
    }

    private static double[] setupAngles() {
        double[] angles = new double[50];

        for(int i = 0; i < 50; i ++)
            angles[i] = Math.toRadians(ParticlesInitialVelSet.randomExDouble(0.0, 360.0));

        return angles;
    }

    @Override
    public double getVelX() {
        return speed * Math.sin(angles[increment()]);
    }

    @Override
    public double getVelY() {
        return speed * Math.cos(angles[increment()]);
    }

    private static int increment() {
        int val = count;

        if(++count >= 50)
            count = 0;

        return val;
    }
}
