package xyz.nasaknights.deepspace.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Elevator;

public class BasicElevatorCommand extends Command {
    private boolean up;

    public BasicElevatorCommand(boolean up) {
        this.up = up;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {
        Elevator.getInstance().setPower(up ? .5 : -.5);
    }

    @Override
    protected void end() {
        Elevator.getInstance().setPower(.075);
    }

    @Override
    protected void interrupted() {
        Elevator.getInstance().setPower(.075);
    }
}
