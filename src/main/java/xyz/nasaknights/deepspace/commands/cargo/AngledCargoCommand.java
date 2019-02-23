package xyz.nasaknights.deepspace.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Cargo;

public class AngledCargoCommand extends Command {
    private double leftPower;
    private double rightPower;

    public AngledCargoCommand(double leftPower, double rightPower) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;
    }

    @Override
    protected void execute() {
        Cargo.getInstance().setLeftPower(leftPower);
        Cargo.getInstance().setRightPower(rightPower);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Cargo.getInstance().setPower(0);
    }

    @Override
    protected void interrupted() {
        Cargo.getInstance().setPower(0);
    }

    public void setPower(double leftPower, double rightPower) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;
    }
}
