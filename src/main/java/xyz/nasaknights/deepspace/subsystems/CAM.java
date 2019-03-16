package xyz.nasaknights.deepspace.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import xyz.nasaknights.deepspace.RobotMap;
import xyz.nasaknights.deepspace.util.motors.Lazy_SparkMAX;
import xyz.nasaknights.deepspace.util.motors.factory.SparkMAXFactory;

public class CAM extends Subsystem {
    private static CAM instance = new CAM();

    private Lazy_SparkMAX left;
    private Lazy_SparkMAX right;

    private DoubleSolenoid rearPistons = new DoubleSolenoid(2, 3);

    private CAM() {
        left = SparkMAXFactory.getSparkMAX(RobotMap.kLeftCAMSparkMAXID);
        right = SparkMAXFactory.getSparkMAX(RobotMap.kRightCAMSparkMAXID);

        right.setInverted(true);
    }

    public static CAM getInstance() {
        return instance;
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void setPower(double power) {
        left.set(power);
        right.set(power);
    }

    public void setPower(double leftPower, double rightPower) {
        left.set(leftPower);
        right.set(rightPower);
    }

    public void setPistonsExtended(boolean extended) {
        rearPistons.set(extended ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    public void togglePistonsExtended() {
        rearPistons.set((rearPistons.get() == DoubleSolenoid.Value.kForward) ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    }

    public boolean arePistonsExtended() {
        return rearPistons.get() == DoubleSolenoid.Value.kForward;
    }

    public double getLeftPosition() {
        return left.getEncoder().getPosition();
    }

    public double getRightPosition() {
        return right.getEncoder().getPosition();
    }

    public void setLeftPower(double power) {
        left.set(power);
    }

    public void setRightPower(double power) {
        right.set(power);
    }
}
