package xyz.nasaknights.deepspace.commands.cam;

import edu.wpi.first.wpilibj.command.CommandGroup;
import xyz.nasaknights.deepspace.commands.drive.TimedDriveCommand;

public class CAMCommandRoutine extends CommandGroup {
    public CAMCommandRoutine() {
        addSequential(new CAMCommand(CAMCommand.CAMSetpoint.HALFWAY));
        addSequential(new CAMExtensionCommand());
        addSequential(new TimedDriveCommand(.3, 0), .7);
        addParallel(new CAMCommand(CAMCommand.CAMSetpoint.FULL));
    }
}
