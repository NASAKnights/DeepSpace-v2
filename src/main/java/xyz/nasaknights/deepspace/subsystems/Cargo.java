package xyz.nasaknights.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import xyz.nasaknights.deepspace.RobotMap;
import xyz.nasaknights.deepspace.util.motors.Lazy_VictorSPX;
import xyz.nasaknights.deepspace.util.motors.factory.VictorSPXFactory;

public class Cargo {
    private static Cargo instance;
    private final static DigitalInput limit = new DigitalInput(0);

    private Lazy_VictorSPX left;
    private Lazy_VictorSPX right;

    public Cargo() {
        left = VictorSPXFactory.createVictor(RobotMap.kLeftIntakeVictorID);
        right = VictorSPXFactory.createVictor(RobotMap.kRightIntakeVictorID, VictorSPXFactory.kInvertedVictorMasterConfiguration);
    }

    public static Cargo getInstance() {
        if (instance == null) {
            instance = new Cargo();
        }

        return instance;
    }

    public void setPower(double power) {
        right.set(ControlMode.PercentOutput, power);
        left.set(ControlMode.PercentOutput, power);
    }

    public boolean isLimitActive() {
        return !limit.get();
    }

    public void setLeftPower(double power) {
        left.set(ControlMode.PercentOutput, power);
    }

    public void setRightPower(double power) {
        right.set(ControlMode.PercentOutput, power);
    }
}
