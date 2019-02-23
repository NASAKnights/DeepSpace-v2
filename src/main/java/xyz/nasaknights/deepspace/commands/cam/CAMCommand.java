package xyz.nasaknights.deepspace.commands.cam;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.CAM;

public class CAMCommand extends Command {
    @Override
    protected void initialize() {
        CAM.getInstance().setPower(.5);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        CAM.getInstance().setPower(0);
    }

    @Override
    protected void interrupted() {
        CAM.getInstance().setPower(0);
    }
}
