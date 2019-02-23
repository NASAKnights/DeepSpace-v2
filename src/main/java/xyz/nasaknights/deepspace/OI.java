package xyz.nasaknights.deepspace;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import xyz.nasaknights.deepspace.commands.cam.CAMCommand;
import xyz.nasaknights.deepspace.commands.cargo.AngledCargoCommand;
import xyz.nasaknights.deepspace.commands.cargo.CargoCommand;
import xyz.nasaknights.deepspace.commands.drive.GearShiftCommand;
import xyz.nasaknights.deepspace.commands.elevator.BasicElevatorCommand;
import xyz.nasaknights.deepspace.commands.elevator.ElevatorCommand;
import xyz.nasaknights.deepspace.commands.hatch.HatchCommand;
import xyz.nasaknights.deepspace.commands.hatch.HatchExtensionCommand;
import xyz.nasaknights.deepspace.control.ControllerMappings;
import xyz.nasaknights.deepspace.control.JoystickFactory;
import xyz.nasaknights.deepspace.subsystems.Drivetrain;

public class OI {
    private AngledCargoCommand cargo = new AngledCargoCommand(0, 0);

    public void prepareInputs() {
        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.DRIVER), ControllerMappings.PS4Controller.X.getID()).whenPressed(new GearShiftCommand());
        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.DRIVER), ControllerMappings.PS4Controller.TRIANGLE.getID()).whileHeld(new CAMCommand());

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.TRIANGLE.getID()).whileHeld(new ElevatorCommand(true));
        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.CIRCLE.getID()).whileHeld(new BasicElevatorCommand(false));

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.LEFT_BUMPER.getID()).whileHeld(new CargoCommand(-.3));
        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.RIGHT_BUMPER.getID()).whileHeld(new CargoCommand(1));

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.OPTIONS.getID()).whenPressed(new HatchExtensionCommand());

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.X.getID()).whileHeld(new HatchCommand(true));
        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.SQUARE.getID()).whileHeld(new HatchCommand(false));
    }

    public void processAxis() {
        double operatorLeftTrigger = JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR).getRawAxis(ControllerMappings.PS4Controller.LEFT_TRIGGER.getID());
        double operatorRightTrigger = JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR).getRawAxis(ControllerMappings.PS4Controller.RIGHT_TRIGGER.getID());

        if (operatorLeftTrigger > .075 || operatorRightTrigger > .075) {
            cargo.setPower(operatorLeftTrigger, operatorRightTrigger);

            if (!cargo.isRunning()) {
                cargo.start();
            }
        } else {
            if (cargo.isRunning()) {
                cargo.setPower(0, 0);
                cargo.cancel();
            }
        }

        // Run ramp check
        double driverLeftXJoystick = JoystickFactory.getJoystick(JoystickFactory.Controllers.DRIVER).getRawAxis(ControllerMappings.PS4Controller.LEFT_X_AXIS.getID());
        double driverRightXJoystick = JoystickFactory.getJoystick(JoystickFactory.Controllers.DRIVER).getRawAxis(ControllerMappings.PS4Controller.RIGHT_X_AXIS.getID());

        if (driverLeftXJoystick <= .2 && driverRightXJoystick >= .1) {
            Drivetrain.getInstance().setRamp(.2);
        } else {
            Drivetrain.getInstance().setRamp(Drivetrain.getInstance().isInHighGear() ? Drivetrain.kHighGearRampSeconds : Drivetrain.kLowGearRampSeconds);
        }
    }
}

