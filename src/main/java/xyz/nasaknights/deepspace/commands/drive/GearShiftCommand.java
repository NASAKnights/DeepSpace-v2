package xyz.nasaknights.deepspace.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Drivetrain;

public class GearShiftCommand extends Command {
    private boolean done = false;

    public GearShiftCommand() {
        requires(Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {
        Drivetrain.getInstance().toggleHighGear();
        done = true;
    }

    @Override
    protected boolean isFinished() {
        return done;
    }

    @Override
    protected void end() {
        new DriveCommand().start();
    }

    @Override
    protected void interrupted() {
        new DriveCommand().start();
    }
}
