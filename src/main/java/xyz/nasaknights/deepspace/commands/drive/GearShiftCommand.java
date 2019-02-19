package xyz.nasaknights.deepspace.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Drivetrain;

public class GearShiftCommand extends Command {
    private boolean previousState;

    public GearShiftCommand() {
        requires(Drivetrain.getInstance());

        previousState = Drivetrain.getInstance().isInHighGear();
    }

    @Override
    protected void initialize() {
        Drivetrain.getInstance().toggleHighGear();
    }

    @Override
    protected void execute() {
        if (previousState == Drivetrain.getInstance().isInHighGear()) {
            Drivetrain.getInstance().toggleHighGear();
        }
    }

    @Override
    protected boolean isFinished() {
        return previousState != Drivetrain.getInstance().isInHighGear();
    }
}
