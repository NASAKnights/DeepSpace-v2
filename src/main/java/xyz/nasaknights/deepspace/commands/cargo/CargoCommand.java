package xyz.nasaknights.deepspace.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Cargo;

public class CargoCommand extends Command {
    private static boolean inCage = false;
    private static long lastChecked = -1;
    private double power;

    public CargoCommand(double power) {
        this.power = power;
    }

    @Override
    protected void execute() {
        if ((inCage || Cargo.getInstance().isLimitActive()) && power < 0) {
            if (lastChecked != -1 && System.currentTimeMillis() - lastChecked > 500) {
                Cargo.getInstance().setPower(0);
                inCage = true;
            } else if (lastChecked == -1) {
                lastChecked = System.currentTimeMillis();
                Cargo.getInstance().setPower(0);
            }
            return;
        }

        if (inCage && power > 0) {
            inCage = false;
            lastChecked = -1;
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
