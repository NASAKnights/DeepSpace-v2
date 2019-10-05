package xyz.nasaknights.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import xyz.nasaknights.deepspace.RobotMap;
import xyz.nasaknights.deepspace.util.motors.Lazy_TalonSRX;
import xyz.nasaknights.deepspace.util.motors.factory.TalonSRXFactory;

/**
 * This class functions as a singleton for management of the Hatch panel grabber and its related components, comprising
 * of the various different individual components that function together to create commands that are used to utilize the
 * components.
 *
 * @author Bradley Hooten (hello@bradleyh.me)
 */
public class Hatch extends Subsystem {
    private static Hatch instance = new Hatch();

    private static DoubleSolenoid grabber = new DoubleSolenoid(4, 5);

    private final static PIDController talonPID = new PIDController(0.0, 0.0, 0.0, 0.0, new PIDSource() {
        @Override
        public PIDSourceType getPIDSourceType() {
            return null;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
            return;
        }

        @Override
        public double pidGet() {
            return getInstance().getEncoderTicks();
        }
    }, output -> getInstance().setPower(output));
    private final Lazy_TalonSRX talon = TalonSRXFactory.createTalon(RobotMap.kHatchTalonID);

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
     * Sets the power of the VictorSPX controlling the Hatch / Cargo arm rotation.
     *
     * @param power Power to move the arm at (negative for downward movement, positive for upward)
     */
    public void setPower(double power) {
        talon.set(ControlMode.PercentOutput, power);
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
     * Returns the encoder position on the cargo / hatch mechanism.
     *
     * @return Encoder position of cargo arm
     */
    public long getEncoderTicks() {
        return talon.getSensorCollection().getQuadraturePosition();
    }

    /**
     * Toggles the extension status of the gripper arm. Sets the gripper arm to the opposite of its current position.
     */
    public void toggleExtension() {
        setExtended(isExtended());
    }
}
