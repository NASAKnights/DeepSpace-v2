package xyz.nasaknights.deepspace.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import xyz.nasaknights.deepspace.RobotMap;
import xyz.nasaknights.deepspace.util.motors.Lazy_SparkMAX;
import xyz.nasaknights.deepspace.util.motors.factory.SparkMAXFactory;

public class CAM extends Subsystem {
    private static CAM instance;

    private Lazy_SparkMAX left;
    private Lazy_SparkMAX right;

    private CAM() {
        left = SparkMAXFactory.getSparkMAX(RobotMap.kLeftCAMSparkMAXID);
        right = SparkMAXFactory.getSparkMAX(RobotMap.kRightCAMSparkMAXID);
    }

    public static CAM getInstance() {
        if (instance == null) {
            instance = new CAM();
        }

        return instance;
    }

    @Override
    protected void initDefaultCommand() {

    }
}
