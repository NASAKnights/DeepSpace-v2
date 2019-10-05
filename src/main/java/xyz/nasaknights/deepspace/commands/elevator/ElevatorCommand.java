package xyz.nasaknights.deepspace.commands.elevator;

import edu.wpi.first.wpilibj.command.InstantCommand;
import xyz.nasaknights.deepspace.subsystems.Elevator;

public final class ElevatorCommand extends InstantCommand {
    private Elevator.ElevatorHeight height;

    public ElevatorCommand(Elevator.ElevatorHeight height) {
        this.height = height;
        requires(Elevator.getInstance());
    }

    @Override
    protected void execute() {
        Elevator.getInstance().setPosition(height);
    }
}
