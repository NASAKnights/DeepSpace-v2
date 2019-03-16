package xyz.nasaknights.deepspace.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Drivetrain;

public class GearShiftCommand extends Command {
    @Override
    protected void initialize() {
        Drivetrain.getInstance().setHighGear(true);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Drivetrain.getInstance().setHighGear(false);
    }

    @Override
    protected void interrupted() {
        Drivetrain.getInstance().setHighGear(false);
    }
}
