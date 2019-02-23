package xyz.nasaknights.deepspace.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Cargo;

public class CargoCommand extends Command {
    private static boolean inCage = false;
    private double power;

    public CargoCommand(double power) {
        this.power = power;
    }

    @Override
    protected void execute() {
        if (Cargo.getInstance().isLimitActive() && power < 0) {
            Cargo.getInstance().setPower(0);
            inCage = true;
            return;
        }

        if (inCage && power < 0) {
            Cargo.getInstance().setPower(0);
            return;
        }

        if (inCage && power > 0) {
            inCage = false;
        }

        Cargo.getInstance().setPower(power);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    public void setPower(double power) {
        this.power = power;
    }

    @Override
    protected void end() {
        Cargo.getInstance().setPower(0);
    }
}
