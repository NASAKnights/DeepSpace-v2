package xyz.nasaknights.deepspace.util.motors;

import com.revrobotics.CANSparkMax;

/**
 * This class is a lazy implementation of the {@link CANSparkMax} class, which allows advanced
 * ways of manipulating Spark MAX motor controllers.
 */
public class Lazy_SparkMAX extends CANSparkMax {
    private double lastValue;

    public Lazy_SparkMAX(int deviceID, MotorType type) {
        super(deviceID, type);
    }

    public double getLastValue() {
        return this.lastValue;
    }

    @Override
    public void set(double speed) {
        this.lastValue = speed;
        super.set(speed);
    }
}
