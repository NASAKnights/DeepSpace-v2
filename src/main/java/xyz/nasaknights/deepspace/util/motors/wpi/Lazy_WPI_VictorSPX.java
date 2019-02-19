package xyz.nasaknights.deepspace.util.motors.wpi;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * This class is a lazy implementation of the {@link WPI_VictorSPX} class, which provides a friendly
 * implementation of the class that works with existing WPILib drivetrain bases.
 *
 * @deprecated This class has been deprecated due to its lack of features and stability. {@link xyz.nasaknights.deepspace.util.motors.Lazy_VictorSPX} is preferred.
 */
@Deprecated
public class Lazy_WPI_VictorSPX extends WPI_VictorSPX {
    private ControlMode lastControlMode;
    private double lastValue;

    /**
     * Constructor for motor controller
     *
     * @param deviceNumber device ID of motor controller
     */
    public Lazy_WPI_VictorSPX(int deviceNumber) {
        super(deviceNumber);
    }

    public double getLastValue() {
        return this.lastValue;
    }

    @Override
    public void set(ControlMode mode, double value) {
        if (lastControlMode != mode || lastValue != value) {
            this.lastControlMode = mode;
            this.lastValue = value;
            super.set(mode, value);
        }
    }
}
