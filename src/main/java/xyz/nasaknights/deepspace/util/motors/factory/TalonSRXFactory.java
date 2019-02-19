package xyz.nasaknights.deepspace.util.motors.factory;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import xyz.nasaknights.deepspace.util.motors.Lazy_TalonSRX;

public class TalonSRXFactory {
    public static final MotorConfiguration kTalonMasterConfiguration = new MotorConfiguration();
    public static final MotorConfiguration kTalonSlaveConfiguration = new MotorConfiguration() {{
        kControlMode = ControlMode.Follower;
    }};
    public static final MotorConfiguration kInvertedTalonMasterConfiguration = new MotorConfiguration() {{
        inverted = true;
    }};
    public static final MotorConfiguration kInvertedTalonSlaveConfiguration = new MotorConfiguration() {{
        inverted = true;
        kControlMode = ControlMode.Follower;
    }};

    private static final int kSlavePlaceholderID = -1;

    static {
        kTalonSlaveConfiguration.kControlMode = ControlMode.Follower;
    }

    public static Lazy_TalonSRX createTalon(int id) {
        return createTalon(id, kTalonMasterConfiguration, kSlavePlaceholderID);
    }

    public static Lazy_TalonSRX createSlaveTalon(int id, int masterID) {
        return createTalon(id, kTalonSlaveConfiguration, masterID);
    }

    public static Lazy_TalonSRX createSlaveTalon(int id, TalonSRX master) {
        return createTalon(id, kTalonSlaveConfiguration, master.getDeviceID());
    }

    public static Lazy_TalonSRX createTalon(int id, MotorConfiguration config) {
        return createTalon(id, config, kSlavePlaceholderID);
    }

    public static Lazy_TalonSRX createSlaveTalon(int id, MotorConfiguration config, int masterID) {
        return createTalon(id, config, masterID);
    }

    public static Lazy_TalonSRX createSlaveTalon(int id, MotorConfiguration config, TalonSRX master) {
        return createTalon(id, config, master.getDeviceID());
    }

    private static Lazy_TalonSRX createTalon(int id, MotorConfiguration config, int masterID) {
        Lazy_TalonSRX talon = new Lazy_TalonSRX(id);

        talon.set(config.kControlMode, ((config.kControlMode == ControlMode.Follower && masterID != kSlavePlaceholderID) ? masterID : config.kControlValue));

        talon.enableCurrentLimit(config.kCurrentLimit);
        talon.configPeakCurrentLimit(config.kCurrentLimitCeiling);
        talon.configPeakCurrentDuration(config.kCurrentLimitTime);

        talon.setNeutralMode(config.kNeutralMode);

        talon.config_kP(0, config.kP);
        talon.config_kI(0, config.kI);
        talon.config_kD(0, config.kD);
        talon.config_kF(0, config.kF);

        talon.setInverted(config.inverted);

        return talon;
    }

    public static class MotorConfiguration {
        public boolean kCurrentLimit = false;
        public int kCurrentLimitCeiling = 10;
        public int kCurrentLimitTime = 0;

        public NeutralMode kNeutralMode = NeutralMode.Coast;

        public ControlMode kControlMode = ControlMode.PercentOutput;
        public double kControlValue = 0.0;

        public double kP = 0;
        public double kI = 0;
        public double kD = 0;
        public double kF = 0;

        public boolean inverted = false;
    }
}
