package xyz.nasaknights.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import xyz.nasaknights.deepspace.RobotMap;
import xyz.nasaknights.deepspace.util.motors.Lazy_TalonSRX;
import xyz.nasaknights.deepspace.util.motors.Lazy_VictorSPX;
import xyz.nasaknights.deepspace.util.motors.factory.TalonSRXFactory;
import xyz.nasaknights.deepspace.util.motors.factory.VictorSPXFactory;

public class Elevator extends Subsystem {
    private static double kFDown = 0;

    private static double kFUp = 0;

    private static Elevator instance = new Elevator();

    private final double kMaxTalonSRXSpeed = .6;

    private Lazy_TalonSRX talon;
    private Lazy_VictorSPX victor;

    private ElevatorState state;

    private Elevator() {
        talon = TalonSRXFactory.createTalon(RobotMap.kElevatorTalonID);
        victor = VictorSPXFactory.createVictor(RobotMap.kElevatorVictorID);

        talon.configPeakOutputForward(kMaxTalonSRXSpeed);
        talon.configPeakOutputReverse(kMaxTalonSRXSpeed * -1);

        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);

        talon.configOpenloopRamp(.5);

        talon.configMotionCruiseVelocity(0);
        talon.configMotionAcceleration(0); // TODO update

        talon.setNeutralMode(NeutralMode.Brake);
        victor.setNeutralMode(NeutralMode.Brake);

        victor.follow(talon);
    }

    public static Elevator getInstance() {
        return instance;
    }

    @Override
    protected void initDefaultCommand() {

    }

    public ElevatorState getState() {
        return this.state;
    }

    public long getEncoderHeight() {
        return talon.getSensorCollection().getQuadraturePosition();
    }

    public void setPower(double power) {
        this.talon.set(ControlMode.PercentOutput, power);
    }

    public double getVoltage() {
        return talon.getMotorOutputVoltage();
    }

    public void setPosition(ElevatorHeight height) {
        if (getEncoderHeight() > height.getHeight()) {
            talon.config_kF(0, kFDown);
        } else {
            talon.config_kF(0, kFUp);
        }

        talon.set(ControlMode.MotionMagic, height.getHeight());
    }

    public void stop() {
        talon.set(ControlMode.Position, getEncoderHeight());
    }

    public int getVelocity() {
        return talon.getSelectedSensorVelocity();
    }

    public enum ElevatorState {
        MOVING,
        BRAKING
    }

    public enum ElevatorHeight {
        BOTTOM(-500),
        SHIP_HATCH(0),
        SHIP_CARGO(0),
        ROCKET_FIRST_HATCH(0),
        ROCKET_FIRST_CARGO(0),
        ROCKET_SECOND_HATCH(0),
        ROCKET_SECOND_CARGO(0),
        ROCKET_THIRD_HATCH(0),
        ROCKET_THIRD_CARGO(0),
        MIDDLE(-17625),
        TOP(-34500);

        private int height;

        ElevatorHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return this.height;
        }
    }
}
