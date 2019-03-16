package xyz.nasaknights.deepspace.commands.cam;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.CAM;

public class CAMExtensionCommand extends Command {
    private boolean finished = false;

    @Override
    protected void execute() {
        do {
            CAM.getInstance().setPistonsExtended(!CAM.getInstance().arePistonsExtended());
            finished = true;
        } while (!finished);
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }
}