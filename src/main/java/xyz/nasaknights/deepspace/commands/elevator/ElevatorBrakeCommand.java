package xyz.nasaknights.deepspace.commands.elevator;

import edu.wpi.first.wpilibj.command.PIDCommand;
import xyz.nasaknights.deepspace.subsystems.Elevator;
import xyz.nasaknights.deepspace.subsystems.Elevator.ElevatorState;

public final class ElevatorBrakeCommand extends PIDCommand {
    public ElevatorBrakeCommand() {
        super(0, 0, 0);

        requires(Elevator.getInstance());

        getPIDController().setAbsoluteTolerance(15);
    }

    @Override
    protected void initialize() {
        getPIDController().setSetpoint(Elevator.getInstance().getEncoderHeight());
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
        return Elevator.getInstance().getState() != ElevatorState.BRAKING;
    }

    @Override
    protected void end() {
        Elevator.getInstance().setPower(0);
    }

    @Override
    protected void interrupted() {
        Elevator.getInstance().setPower(0);
    }
}
