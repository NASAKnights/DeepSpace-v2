package xyz.nasaknights.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import xyz.nasaknights.deepspace.util.motors.Lazy_TalonSRX;
import xyz.nasaknights.deepspace.util.motors.Lazy_VictorSPX;
import xyz.nasaknights.deepspace.util.motors.factory.TalonSRXFactory;
import xyz.nasaknights.deepspace.util.motors.factory.VictorSPXFactory;

import static xyz.nasaknights.deepspace.RobotMap.*;

public class Drivetrain extends Subsystem {
    public static final double kLowGearRampSeconds = 1.39; // just adjust this for high and the below for low?
    public static final double kHighGearRampSeconds = .40;
    public static final double kMiddleWheelMaxSpeed = 1;
    public static final double kMiddleWheelRampSeconds = .5;
    public static final double kElevatorMaxSpeed = .3;

    private static DoubleSolenoid gearShifter;

    private static final TalonSRXFactory.MotorConfiguration kDrivetrainTalonSRXMotorConfiguration = new TalonSRXFactory.MotorConfiguration() {{
        kP = 0;
        kI = 0;
        kD = 0;
        kF = 0;
    }};

    private static final TalonSRXFactory.MotorConfiguration kDrivetrainInvertedTalonSRXMotorConfiguration = new TalonSRXFactory.MotorConfiguration() {{
        kP = 0;
        kI = 0;
        kD = 0;
        kF = 0;

        inverted = true;
    }};

    private static Drivetrain instance = new Drivetrain();

    private final DoubleSolenoid.Value kHighGear = DoubleSolenoid.Value.kForward;
    private final DoubleSolenoid.Value kLowGear = DoubleSolenoid.Value.kReverse;

    private Lazy_TalonSRX frontLeftTalon, frontRightTalon, middleTalon;

    private Lazy_VictorSPX rearLeftVictor;
    private Lazy_VictorSPX rearRightVictor;

    private boolean isInHighGear;

    private DrivetrainState state = DrivetrainState.TELEOP;

    private Drivetrain() {
        this.frontLeftTalon = TalonSRXFactory.createTalon(kFrontLeftTalonID, kDrivetrainInvertedTalonSRXMotorConfiguration);
        this.frontRightTalon = TalonSRXFactory.createTalon(kFrontRightTalonID, kDrivetrainTalonSRXMotorConfiguration);
        this.middleTalon = TalonSRXFactory.createTalon(kMiddleTalonID, kDrivetrainInvertedTalonSRXMotorConfiguration);

        this.rearLeftVictor = VictorSPXFactory.createSlaveVictor(kRearLeftVictorID, VictorSPXFactory.kInvertedVictorSlaveConfiguration, this.frontLeftTalon);
        this.rearRightVictor = VictorSPXFactory.createSlaveVictor(kRearRightVictorID, VictorSPXFactory.kVictorSlaveConfiguration, this.frontRightTalon);

        this.middleTalon.configOpenloopRamp(kMiddleWheelRampSeconds);
        this.middleTalon.configPeakOutputForward(kMiddleWheelMaxSpeed);
        this.middleTalon.configPeakOutputReverse(kMiddleWheelMaxSpeed * -1);

        gearShifter = new DoubleSolenoid(0, 0, 1);

        setHighGear(true);
    }

    /**
     * Fetches the singleton instance of the Drivetrain class.
     *
     * @return Instance of drivetrain class
     */
    public final static Drivetrain getInstance() {
        return instance;
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void drive(double throttle, double lateral, double rotate) {
        double rotation = rotate * .75;

        if (Elevator.getInstance().getEncoderHeight() <= Elevator.ElevatorHeight.MIDDLE.getHeight()) {
            frontLeftTalon.configPeakOutputForward(kElevatorMaxSpeed);
            frontRightTalon.configPeakOutputForward(kElevatorMaxSpeed);
            frontLeftTalon.configPeakOutputReverse(kElevatorMaxSpeed * -1);
            frontRightTalon.configPeakOutputReverse(kElevatorMaxSpeed * -1);
        } else {
            frontLeftTalon.configPeakOutputForward(1);
            frontRightTalon.configPeakOutputForward(1);
            frontLeftTalon.configPeakOutputReverse(-1);
            frontRightTalon.configPeakOutputReverse(-1);
        }

        if (throttle >= .07 || throttle <= -.07) {
            setRamp(isInHighGear() ? kHighGearRampSeconds : kLowGearRampSeconds);
            if (rotation >= .07 || rotation <= -.07) {
                frontLeftTalon.set(ControlMode.PercentOutput, throttle * -1 + rotation);
                frontRightTalon.set(ControlMode.PercentOutput, throttle * -1 - rotation);
            } else {
                frontLeftTalon.set(ControlMode.PercentOutput, throttle * -1);
                frontRightTalon.set(ControlMode.PercentOutput, throttle * -1);
            }
        } else {
            if (rotation >= .07 || rotation <= -.07) {
                setRamp(.3);
                frontLeftTalon.set(ControlMode.PercentOutput, rotation);
                frontRightTalon.set(ControlMode.PercentOutput, -rotation);
            } else {
                setRamp(isInHighGear() ? kHighGearRampSeconds : kLowGearRampSeconds);
                frontLeftTalon.set(ControlMode.PercentOutput, 0);
                frontRightTalon.set(ControlMode.PercentOutput, 0);
            }
        }

        if (lateral >= .07 || lateral <= -.07) {
            middleTalon.set(ControlMode.PercentOutput, lateral);
        } else {
            middleTalon.set(ControlMode.PercentOutput, 0);
        }
    }

    public DrivetrainState getState() {
        return this.state;
    }

    public void setState(DrivetrainState state) {
        if (this.state != state) {
            this.state = state;
        }
    }

    public boolean isInHighGear() {
        return this.isInHighGear;
    }

    public void setHighGear(boolean highGear) {
        isInHighGear = highGear;
        gearShifter.set(!highGear ? kHighGear : kLowGear);

        setRamp(highGear ? kLowGearRampSeconds : kHighGearRampSeconds);
    }

    public void toggleHighGear() {
        setHighGear(!isInHighGear);
    }

    public void halt() {
        frontLeftTalon.set(ControlMode.PercentOutput, 0);
        frontRightTalon.set(ControlMode.PercentOutput, 0);
        middleTalon.set(ControlMode.PercentOutput, 0);
    }

    public void setRamp(double ramp) {
        frontLeftTalon.configOpenloopRamp(ramp);
        frontRightTalon.configOpenloopRamp(ramp);
    }

    public void setLeftPower(double power) {
        frontLeftTalon.set(ControlMode.PercentOutput, power);
    }

    public void setRightPower(double power) {
        frontRightTalon.set(ControlMode.PercentOutput, power);
    }

    public void setMiddlePower(double power) {
        middleTalon.set(ControlMode.PercentOutput, power);
    }

    public void setPower(double power) {
        frontLeftTalon.set(ControlMode.PercentOutput, power);
        frontRightTalon.set(ControlMode.PercentOutput, power);
    }

    public enum DrivetrainState {
        AUTONOMOUS,
        TELEOP
    }
}
