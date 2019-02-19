package xyz.nasaknights.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import xyz.nasaknights.deepspace.RobotMap;
import xyz.nasaknights.deepspace.util.motors.Lazy_TalonSRX;
import xyz.nasaknights.deepspace.util.motors.Lazy_VictorSPX;
import xyz.nasaknights.deepspace.util.motors.factory.TalonSRXFactory;
import xyz.nasaknights.deepspace.util.motors.factory.VictorSPXFactory;

public class Elevator extends Subsystem {
    private static Elevator instance;

    private Lazy_TalonSRX talon;
    private Lazy_VictorSPX victor;

    private ElevatorState state;

    private Elevator() {
        talon = TalonSRXFactory.createTalon(RobotMap.kElevatorTalonID);
        victor = VictorSPXFactory.createVictor(RobotMap.kElevatorVictorID);
    }

    public static Elevator getInstance() {
        if (instance == null) {
            instance = new Elevator();
        }

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
        return 0; //talon.getSensorCollection().getQuadraturePosition();
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
        BOTTOM(0),
        MIDDLE(10000),
        TOP(20000);

        private int height;

        ElevatorHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return this.height;
        }
    }
}
