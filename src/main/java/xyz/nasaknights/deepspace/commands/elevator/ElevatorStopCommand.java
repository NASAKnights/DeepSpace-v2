package xyz.nasaknights.deepspace.commands.elevator;

import edu.wpi.first.wpilibj.command.InstantCommand;
import xyz.nasaknights.deepspace.subsystems.Elevator;

public class ElevatorStopCommand extends InstantCommand {
    @Override
    protected void execute() {
        Elevator.getInstance().stop();
    }
}
