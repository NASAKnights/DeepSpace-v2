package xyz.nasaknights.deepspace.util.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * This class is a lazy implementation of the {@link VictorSPX} class, which allows advanced
 * ways of manipulating VictorSPX motor controllers.
 */
public class Lazy_VictorSPX extends VictorSPX {
    private double lastValue;
    private ControlMode lastControlMode;

    /**
     * Constructor
     *
     * @param deviceNumber [0,62]
     */
    public Lazy_VictorSPX(int deviceNumber) {
        super(deviceNumber);
    }

    public double getLastValue() {
        return this.lastValue;
    }

    @Override
    public void set(ControlMode mode, double outputValue) {
        if (this.lastControlMode != mode || this.lastValue != outputValue) {
            this.lastControlMode = mode;
            this.lastValue = outputValue;
            super.set(mode, outputValue);
        }
    }
}
