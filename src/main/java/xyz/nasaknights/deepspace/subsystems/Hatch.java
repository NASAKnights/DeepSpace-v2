package xyz.nasaknights.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import xyz.nasaknights.deepspace.RobotMap;
import xyz.nasaknights.deepspace.util.motors.Lazy_VictorSPX;
import xyz.nasaknights.deepspace.util.motors.factory.VictorSPXFactory;

public class Hatch extends Subsystem {
    private static Hatch instance = new Hatch();

    private static DoubleSolenoid grabber = new DoubleSolenoid(4, 5);

    private Lazy_VictorSPX victor;
    private Encoder encoder;
    private DigitalInput dio = new DigitalInput(1);

    public Hatch() {
        victor = VictorSPXFactory.createVictor(RobotMap.kHatchVictorID);
        encoder = new Encoder(dio, dio);

        encoder.setReverseDirection(true);
    }

    public static Hatch getInstance() {
        return instance;
    }


    @Override
    protected void initDefaultCommand() {

    }

    public int getEncoderValue() {
        return encoder.get();
    }

    public void setPower(double power) {
        victor.set(ControlMode.PercentOutput, power);
    }

    public boolean isExtended() {
        return grabber.get() == DoubleSolenoid.Value.kForward;
    }

    public void setExtended(boolean extended) {
        grabber.set(extended ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public void toggleExtension() {
        setExtended(!isExtended());
    }

    public enum HatchAngle {
        DOWN_FIFTY(),
        ZERO(),
        UP_THIRTY();
    }
}
