package xyz.nasaknights.deepspace;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import xyz.nasaknights.deepspace.commands.cam.CAMCommand;
import xyz.nasaknights.deepspace.commands.cam.CAMExtensionCommand;
import xyz.nasaknights.deepspace.commands.cargo.AngledCargoCommand;
import xyz.nasaknights.deepspace.commands.cargo.CargoCommand;
import xyz.nasaknights.deepspace.commands.drive.GearShiftCommand;
import xyz.nasaknights.deepspace.commands.elevator.ElevatorCommand;
import xyz.nasaknights.deepspace.commands.elevator.ElevatorStopCommand;
import xyz.nasaknights.deepspace.commands.hatch.HatchAngleCommand;
import xyz.nasaknights.deepspace.commands.hatch.HatchExtensionCommand;
import xyz.nasaknights.deepspace.control.ControllerMappings;
import xyz.nasaknights.deepspace.control.JoystickFactory;
import xyz.nasaknights.deepspace.subsystems.Drivetrain;
import xyz.nasaknights.deepspace.subsystems.Elevator;

public class OI {
    private AngledCargoCommand cargo = new AngledCargoCommand(0, 0);
    private static long timeLastAngleSet = System.currentTimeMillis();
    private HatchAngleCommand angle = new HatchAngleCommand(HatchAngleCommand.HatchAngles.CARGO_IN);

    public void prepareInputs() {
        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.DRIVER), ControllerMappings.PS4Controller.RIGHT_BUMPER.getID()).whileHeld(new GearShiftCommand());

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.DRIVER), ControllerMappings.PS4Controller.TRIANGLE.getID()).whileHeld(new CAMCommand());

//        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.DRIVER), ControllerMappings.PS4Controller.SHARE.getID()).whileHeld(new DriveAssistCommand());

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.CIRCLE.getID()).whenPressed(new ElevatorCommand(Elevator.ElevatorHeight.BOTTOM));
        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.CIRCLE.getID()).whenReleased(new ElevatorStopCommand());

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.TRIANGLE.getID()).whenPressed(new ElevatorCommand(Elevator.ElevatorHeight.TOP));
        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.TRIANGLE.getID()).whenReleased(new ElevatorStopCommand());

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.LEFT_BUMPER.getID()).whileHeld(new CargoCommand(-.9));
        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.RIGHT_BUMPER.getID()).whileHeld(new CargoCommand(1));

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.OPTIONS.getID()).whenPressed(new HatchExtensionCommand());

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.SHARE.getID()).whenPressed(new CAMExtensionCommand());

        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.X.getID()).whileHeld(new HatchAngleCommand(HatchAngleCommand.HatchAngles.HATCH));
        new JoystickButton(JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR), ControllerMappings.PS4Controller.SQUARE.getID()).whileHeld(new HatchAngleCommand(HatchAngleCommand.HatchAngles.TOP));
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

        if (driverLeftXJoystick <= .1 && (driverRightXJoystick >= .1 || driverRightXJoystick <= -.1)) {
            Drivetrain.getInstance().setRamp(.45);
        } else {
            Drivetrain.getInstance().setRamp(Drivetrain.getInstance().isInHighGear() ? Drivetrain.kHighGearRampSeconds : Drivetrain.kLowGearRampSeconds);
        }

        int pov = JoystickFactory.getJoystick(JoystickFactory.Controllers.OPERATOR).getPOV();

        if ((pov == 0 || pov == 180) && (System.currentTimeMillis() - timeLastAngleSet) >= 300) {
            angle.setAngle(pov == 0 ? HatchAngleCommand.HatchAngles.getNext(angle.getAngle()) : HatchAngleCommand.HatchAngles.getPrevious(angle.getAngle()));
            angle.start();
            timeLastAngleSet = System.currentTimeMillis();
        }
    }
}

