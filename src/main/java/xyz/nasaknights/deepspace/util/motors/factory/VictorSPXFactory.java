package xyz.nasaknights.deepspace.util.motors.factory;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import xyz.nasaknights.deepspace.util.motors.Lazy_VictorSPX;

public class VictorSPXFactory {
    public static final MotorConfiguration kVictorMasterConfiguration = new MotorConfiguration();
    public static final MotorConfiguration kVictorSlaveConfiguration = new MotorConfiguration() {{
        kControlMode = ControlMode.Follower;
    }};
    public static final MotorConfiguration kInvertedVictorMasterConfiguration = new MotorConfiguration() {{
        inverted = true;
    }};
    public static final MotorConfiguration kInvertedVictorSlaveConfiguration = new MotorConfiguration() {{
        inverted = true;
        kControlMode = ControlMode.Follower;
    }};

    static {
        kVictorSlaveConfiguration.kControlMode = ControlMode.Follower;
    }

    public static Lazy_VictorSPX createVictor(int id) {
        return createVictor(id, kVictorMasterConfiguration, null);
    }

    public static Lazy_VictorSPX createSlaveVictor(int id, IMotorController master) {
        return createVictor(id, kVictorSlaveConfiguration, master);
    }

    public static Lazy_VictorSPX createVictor(int id, MotorConfiguration config) {
        return createVictor(id, config, null);
    }

    public static Lazy_VictorSPX createSlaveVictor(int id, MotorConfiguration config, IMotorController master) {
        return createVictor(id, config, master);
    }

    private static Lazy_VictorSPX createVictor(int id, MotorConfiguration config, IMotorController master) {
        Lazy_VictorSPX victor = new Lazy_VictorSPX(id);

        if (config.kControlMode == ControlMode.Follower && master != null) {
            victor.follow(master);
        } else {
            victor.set(config.kControlMode, config.kControlValue);
        }

        victor.setNeutralMode(config.kNeutralMode);
        victor.setInverted(config.inverted);

        return victor;
    }

    public static class MotorConfiguration {
        public boolean kCurrentLimit = false;
        public int kCurrentLimitCeiling = 10;
        public int kCurrentLimitTime = 0;

        public NeutralMode kNeutralMode = NeutralMode.Coast;

        public ControlMode kControlMode = ControlMode.PercentOutput;
        public double kControlValue = 0.0;

        public boolean inverted = false;
    }
}
