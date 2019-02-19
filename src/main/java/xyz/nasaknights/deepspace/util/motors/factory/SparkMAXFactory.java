package xyz.nasaknights.deepspace.util.motors.factory;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import xyz.nasaknights.deepspace.util.motors.Lazy_SparkMAX;

public class SparkMAXFactory {
    public static MotorConfiguration kSparkMAXMasterConfiguration = new MotorConfiguration();

    public static Lazy_SparkMAX getSparkMAX(int id) {
        return getSparkMAX(id, kSparkMAXMasterConfiguration);
    }

    public static Lazy_SparkMAX getSparkMAX(int id, MotorConfiguration config) {
        Lazy_SparkMAX spark = new Lazy_SparkMAX(id, config.kMotorType);

        spark.setIdleMode(config.kNeutralMode);

        return spark;
    }

    public static class MotorConfiguration {
        public CANSparkMaxLowLevel.MotorType kMotorType = CANSparkMaxLowLevel.MotorType.kBrushless;
        public CANSparkMax.IdleMode kNeutralMode = CANSparkMax.IdleMode.kCoast;
    }
}
