package xyz.nasaknights.deepspace.commands.elevator;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Elevator;

public class ElevatorStallCommand extends Command {
    @Override
    protected void execute() {
        if (!(Math.abs(Elevator.getInstance().getEncoderHeight()) - Math.abs(Elevator.ElevatorHeight.BOTTOM.getHeight()) < 500)) {
            Elevator.getInstance().setPower(.065);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
