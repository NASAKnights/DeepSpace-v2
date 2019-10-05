package xyz.nasaknights.deepspace.commands.elevator;

import edu.wpi.first.wpilibj.command.PIDCommand;
import xyz.nasaknights.deepspace.subsystems.Elevator;
import xyz.nasaknights.deepspace.subsystems.Elevator.ElevatorHeight;
import xyz.nasaknights.deepspace.subsystems.Elevator.ElevatorState;

public final class ElevatorCommand extends PIDCommand {
    private static final double kP = -.00013;
    private static final double kI = 0; //0.000000005;
    private static final double kD = -.0002;
    private static final double kF = 0; //.00001;

    public ElevatorCommand(boolean up) {
        super(kP, kI, kD, kF);

        requires(Elevator.getInstance());

        getPIDController().setSetpoint(up ? ElevatorHeight.TOP.getHeight() : Elevator.ElevatorHeight.BOTTOM.getHeight());
        getPIDController().setAbsoluteTolerance(150);
    }

    public ElevatorCommand(ElevatorHeight height) {
        super(kP, kI, kD, kF);

        requires(Elevator.getInstance());

        getPIDController().setSetpoint(height.getHeight());
        getPIDController().setAbsoluteTolerance(75);
    }

    @Override
    protected void initialize() {
        Elevator.getInstance().setState(ElevatorState.MOVING);
        getPIDController().enable();
    }

    @Override
    protected double returnPIDInput() {
        return Elevator.getInstance().getEncoderHeight();
    }

    @Override
    protected void usePIDOutput(double output) {
        Elevator.getInstance().setPower(output);
    }

    @Override
    protected boolean isFinished() {
        return (Elevator.getInstance().getState() != ElevatorState.MOVING) || getPIDController().onTarget();
    }

    @Override
    protected void end() {
        getPIDController().disable();
        Elevator.getInstance().setState(ElevatorState.BRAKING);
    }

    @Override
    protected void interrupted() {
        getPIDController().disable();
        Elevator.getInstance().setState(ElevatorState.BRAKING);
    }
}
