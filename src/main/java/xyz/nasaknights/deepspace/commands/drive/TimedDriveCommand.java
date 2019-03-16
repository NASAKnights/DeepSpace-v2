package xyz.nasaknights.deepspace.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Drivetrain;

public class TimedDriveCommand extends Command {
    private double throttle;
    private double rotation;

    public TimedDriveCommand(double throttle, double rotation) {
        this.throttle = throttle;
        this.rotation = rotation;
    }

    @Override
    protected void execute() {
        Drivetrain.getInstance().drive(throttle, 0, rotation);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
