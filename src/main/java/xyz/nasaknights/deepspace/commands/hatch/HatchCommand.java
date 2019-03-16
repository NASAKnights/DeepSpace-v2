package xyz.nasaknights.deepspace.commands.hatch;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Hatch;

public class HatchCommand extends Command {
    private boolean up;

    public HatchCommand(boolean up) {
        this.up = up;
    }

    @Override
    protected void execute() {
        Hatch.getInstance().setPower(up ? -1 : 1);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Hatch.getInstance().setPower(0);
    }

    @Override
    protected void interrupted() {
        Hatch.getInstance().setPower(0);
    }
}
