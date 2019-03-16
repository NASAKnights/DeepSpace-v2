package xyz.nasaknights.deepspace.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.control.ControllerMappings;
import xyz.nasaknights.deepspace.control.JoystickFactory;
import xyz.nasaknights.deepspace.subsystems.Drivetrain;

public class DriveCommand extends Command {
    public DriveCommand() {
        requires(Drivetrain.getInstance());
    }

    @Override
    protected void execute() {
        double leftY = JoystickFactory.getJoystick(JoystickFactory.Controllers.DRIVER).getRawAxis(ControllerMappings.PS4Controller.LEFT_Y_AXIS.getID());

        Drivetrain.getInstance().drive(leftY * Math.abs(leftY),
                JoystickFactory.getJoystick(JoystickFactory.Controllers.DRIVER).getRawAxis(ControllerMappings.PS4Controller.LEFT_X_AXIS.getID()),
                JoystickFactory.getJoystick(JoystickFactory.Controllers.DRIVER).getRawAxis(ControllerMappings.PS4Controller.RIGHT_X_AXIS.getID()));
    }

    @Override
    protected boolean isFinished() {
        return Drivetrain.getInstance().getState() != Drivetrain.DrivetrainState.TELEOP;
    }

    @Override
    protected void end() {
        Drivetrain.getInstance().halt();
    }

    @Override
    protected void interrupted() {
        Drivetrain.getInstance().halt();
    }
}
