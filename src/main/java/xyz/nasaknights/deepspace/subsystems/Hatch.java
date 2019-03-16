package xyz.nasaknights.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import xyz.nasaknights.deepspace.RobotMap;
import xyz.nasaknights.deepspace.util.motors.Lazy_VictorSPX;
import xyz.nasaknights.deepspace.util.motors.factory.VictorSPXFactory;

/**
 * This class functions as a singleton for management of the Hatch panel grabber and its related components, comprising
 * of the various different individual components that function together to create commands that are used to utilize the
 * components.
 *
 * @author Bradley Hooten (hello@bradleyh.me)
 */
public class Hatch extends Subsystem {
    private static Hatch instance = new Hatch();

    private static int totalTicks = Integer.MAX_VALUE;

    private static DoubleSolenoid grabber = new DoubleSolenoid(4, 5);

    private final Lazy_VictorSPX victor = VictorSPXFactory.createVictor(RobotMap.kHatchVictorID);
    private final AnalogTrigger trigger = new AnalogTrigger(0);
    private final Counter counter = new Counter();
    private final DigitalInput limitSwitch = new DigitalInput(2);

    private Hatch() {
        trigger.setLimitsVoltage(2, 3.4);
        counter.setUpSource(trigger, AnalogTriggerOutput.AnalogTriggerType.kInWindow);
    }

    /**
     * Fetches the singleton instance of the Hatch class.
     *
     * @return Instance of hatch class
     */
    public static Hatch getInstance() {
        return instance;
    }


    @Override
    protected void initDefaultCommand() {

    }

    /**
     * Get encoder value from counter.
     *
     * @return Encoder value
     */
    public int getCounterValue() {
        return totalTicks;
    }

    /**
     * Sets the power of the VictorSPX controlling the Hatch / Cargo arm rotation.
     *
     * @param power Power to move the arm at (negative for downward movement, positive for upward)
     */
    public void setPower(double power) {
        victor.set(ControlMode.PercentOutput, power);
    }

    /**
     * Returns whether the hatch panel grabber is open or shut.
     *
     * @return Extension status of gripper arm (true if open, false if shut)
     */
    public boolean isExtended() {
        return grabber.get() == DoubleSolenoid.Value.kForward;
    }

    /**
     * Sets whether the hatch panel gripper is open or shut.
     *
     * @param extended Desired status of hatch panel gripper (true if open, false if shut)
     */
    public void setExtended(boolean extended) {
        grabber.set(!extended ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    /**
     * Toggles the extension status of the gripper arm. Sets the gripper arm to the opposite of its current position.
     */
    public void toggleExtension() {
        setExtended(isExtended());
    }

    /**
     * Functions as a helper method to read and interpret pulses from the Bosch Seat motor. This method should be called
     * in a recurrent fashion, with an optimal time at 20ms, as specified in {@link TimedRobot#robotPeriodic()}.
     */
    public void processPulses() {
        if (isLimitSwitchPressed()) {
            counter.clearUpSource();
            counter.clearDownSource();

            totalTicks = 0;
        }

        if (totalTicks >= 3000) {
            return;
        }

        if (victor.getLastValue() < 0) // Rotating up, counter.get() is negative
        {
            totalTicks = totalTicks - counter.get();

            counter.clearUpSource();
            counter.setDownSource(trigger, AnalogTriggerOutput.AnalogTriggerType.kInWindow);
        } else if (victor.getLastValue() > 0) // Rotating down, counter.get() is positive
        {
            totalTicks = totalTicks - counter.get();

            counter.clearDownSource();
            counter.setUpSource(trigger, AnalogTriggerOutput.AnalogTriggerType.kInWindow);
        }
    }

    /**
     * Returns state of limit switch in cargo well. May be used to calibrate location of Bosch seat motor for gripper
     * arm rotation.
     *
     * @return Whether limit switch is pressed or not
     */
    public boolean isLimitSwitchPressed() {
        return !limitSwitch.get();
    }

    public enum HatchAngle {
        TOP(52),
        ZERO(0),
        BOTTOM(-20);

        private int pulses;

        HatchAngle(int pulses) {
            this.pulses = pulses;
        }

        public int getPulses() {
            return this.pulses;
        }
    }
}
