package xyz.nasaknights.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import xyz.nasaknights.deepspace.RobotMap;
import xyz.nasaknights.deepspace.util.motors.Lazy_TalonSRX;
import xyz.nasaknights.deepspace.util.motors.Lazy_VictorSPX;
import xyz.nasaknights.deepspace.util.motors.factory.TalonSRXFactory;
import xyz.nasaknights.deepspace.util.motors.factory.VictorSPXFactory;

public class Elevator extends Subsystem {
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

    public void setState(ElevatorState state) {
        this.state = state;
    }

    public long getEncoderHeight() {
        return talon.getSensorCollection().getQuadraturePosition();
    }

    public void setPower(double power) {
        this.talon.set(ControlMode.PercentOutput, power);
        this.victor.set(ControlMode.PercentOutput, power);
    }

    public enum ElevatorState {
        MOVING,
        BRAKING;
    }

    public enum ElevatorHeight {
        BOTTOM(-1750),
        MIDDLE(-17625),
        TOP(-33500);

        private int height;

        ElevatorHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return this.height;
        }
    }
}
