package xyz.nasaknights.deepspace.util.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * This class is a lazy implementation of the {@link TalonSRX} class, which allows advanced
 * ways of manipulating TalonSRX motor controllers.
 */
public class Lazy_TalonSRX extends TalonSRX {
    private double lastValue;
    private ControlMode lastControlMode;

    /**
     * Constructor for TalonSRX object
     *
     * @param deviceNumber CAN Device ID of Device
     */
    public Lazy_TalonSRX(int deviceNumber) {
        super(deviceNumber);
    }

    public double getLastValue() {
        return this.lastValue;
    }

    @Override
    public void set(ControlMode mode, double outputValue) {
        if (outputValue != this.lastValue || mode != this.lastControlMode) {
            this.lastValue = outputValue;
            this.lastControlMode = mode;
            super.set(mode, outputValue);
        }
    }
}
