package xyz.nasaknights.deepspace.commands.diagnostic;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Hatch;

public class HatchLimitSwitchDepressionTest extends Command {
    @Override
    protected void execute() {
        System.out.println(Hatch.getInstance().isLimitSwitchPressed());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
