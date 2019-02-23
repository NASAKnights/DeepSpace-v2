package xyz.nasaknights.deepspace.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import xyz.nasaknights.deepspace.RobotMap;
import xyz.nasaknights.deepspace.util.motors.Lazy_SparkMAX;
import xyz.nasaknights.deepspace.util.motors.factory.SparkMAXFactory;

public class CAM extends Subsystem {
    private static CAM instance = new CAM();

    private Lazy_SparkMAX left;
    private Lazy_SparkMAX right;

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
}
