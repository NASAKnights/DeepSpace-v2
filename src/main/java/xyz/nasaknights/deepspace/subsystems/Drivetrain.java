package xyz.nasaknights.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import xyz.nasaknights.deepspace.commands.drive.GearShiftCommand;
import xyz.nasaknights.deepspace.control.ControllerMappings;
import xyz.nasaknights.deepspace.control.JoystickFactory;
import xyz.nasaknights.deepspace.util.motors.Lazy_TalonSRX;
import xyz.nasaknights.deepspace.util.motors.Lazy_VictorSPX;
import xyz.nasaknights.deepspace.util.motors.factory.TalonSRXFactory;
import xyz.nasaknights.deepspace.util.motors.factory.VictorSPXFactory;

import static xyz.nasaknights.deepspace.RobotMap.*;

public class Drivetrain extends Subsystem {
    private static final double kHighGearRampSeconds = 1.5;
    private static final double kLowGearRampSeconds = .65;
    private static final double kElevatorMaxSpeed = .3;
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
    private static Drivetrain instance = null;
    private final DoubleSolenoid.Value kHighGear = DoubleSolenoid.Value.kForward;
    private final DoubleSolenoid.Value kLowGear = DoubleSolenoid.Value.kReverse;

    private Lazy_TalonSRX frontLeftTalon;
    private Lazy_TalonSRX frontRightTalon;
    private Lazy_TalonSRX middleTalon;

    private Lazy_VictorSPX rearLeftVictor;
    private Lazy_VictorSPX rearRightVictor;

    private DoubleSolenoid gearShifter;
    private boolean isInHighGear;

    private DrivetrainState state = DrivetrainState.TELEOP;

    private Drivetrain() {
        this.frontLeftTalon = TalonSRXFactory.createTalon(kFrontLeftTalonID, kDrivetrainInvertedTalonSRXMotorConfiguration);
        this.frontRightTalon = TalonSRXFactory.createTalon(kFrontRightTalonID, kDrivetrainTalonSRXMotorConfiguration);
        this.middleTalon = TalonSRXFactory.createTalon(kMiddleTalonID, kDrivetrainInvertedTalonSRXMotorConfiguration);

        this.rearLeftVictor = VictorSPXFactory.createSlaveVictor(kRearLeftVictorID, VictorSPXFactory.kInvertedVictorSlaveConfiguration, this.frontLeftTalon);
        this.rearRightVictor = VictorSPXFactory.createSlaveVictor(kRearRightVictorID, VictorSPXFactory.kVictorSlaveConfiguration, this.frontRightTalon);

        this.gearShifter = new DoubleSolenoid(0, 1);

        setHighGear(true);

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.DRIVER), ControllerMappings.PS4Controller.X.getID()).whenPressed(new GearShiftCommand());
    }

    public final static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }

        return instance;
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void drive(double throttle, double lateral, double rotation) {
        if (Elevator.getInstance().getEncoderHeight() >= Elevator.ElevatorHeight.MIDDLE.getHeight()) {
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
            if (rotation >= .07 || rotation <= -.07) {
                frontLeftTalon.set(ControlMode.PercentOutput, throttle * -1 + rotation);
                frontRightTalon.set(ControlMode.PercentOutput, throttle * -1 - rotation);
            } else {
                frontLeftTalon.set(ControlMode.PercentOutput, throttle * -1);
                frontRightTalon.set(ControlMode.PercentOutput, throttle * -1);
            }
        } else {
            if (rotation >= .07 || rotation <= -.07) {
                frontLeftTalon.set(ControlMode.PercentOutput, rotation);
                frontRightTalon.set(ControlMode.PercentOutput, -rotation);
            } else {
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
        if (this.isInHighGear != highGear) {
            isInHighGear = highGear;
            gearShifter.set(highGear ? kHighGear : kLowGear);

            frontLeftTalon.configOpenloopRamp(highGear ? kHighGearRampSeconds : kLowGearRampSeconds);
            frontRightTalon.configOpenloopRamp(highGear ? kHighGearRampSeconds : kLowGearRampSeconds);
        }
    }

    public void toggleHighGear() {
        setHighGear(!isInHighGear);
    }

    public void halt() {
        frontLeftTalon.set(ControlMode.PercentOutput, 0);
        frontRightTalon.set(ControlMode.PercentOutput, 0);
        middleTalon.set(ControlMode.PercentOutput, 0);
    }

    public enum DrivetrainState {
        AUTONOMOUS,
        TELEOP
    }
}
