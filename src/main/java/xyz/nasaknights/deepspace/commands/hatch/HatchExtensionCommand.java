package xyz.nasaknights.deepspace.commands.hatch;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Hatch;

public class HatchExtensionCommand extends Command {
    private boolean done = false;

    @Override
    protected void execute() {
        Hatch.getInstance().toggleExtension();
        done = true;
    }

    @Override
    protected boolean isFinished() {
        return done;
    }
}
