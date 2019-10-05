package xyz.nasaknights.deepspace;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import xyz.nasaknights.deepspace.commands.drive.DriveCommand;
import xyz.nasaknights.deepspace.subsystems.Drivetrain;
import xyz.nasaknights.deepspace.subsystems.Elevator;
import xyz.nasaknights.deepspace.subsystems.Hatch;
import xyz.nasaknights.deepspace.util.camera.CameraUtil;

public class Robot extends TimedRobot {
    private static GameState currentState;
    private Compressor compressor;
    private OI oi = new OI();

    @Override
    public void robotInit() {
        compressor = new Compressor(0);
        compressor.setClosedLoopControl(true);

        currentState = GameState.DISABLED;

//        VisionClient.getInstance().start();

        oi.prepareInputs();

        CameraUtil.prepareCamera();

//        VisionClient.getInstance().setLightOn(true);
    }

    @Override
    public void disabledInit() {
        currentState = GameState.DISABLED;
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        Elevator.getInstance().setState(Elevator.ElevatorState.BRAKING);

        new DriveCommand().start();

        currentState = GameState.AUTONOMOUS;
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        Drivetrain.getInstance().setState(Drivetrain.DrivetrainState.TELEOP);

        new DriveCommand().start();

        currentState = GameState.TELEOP;

        Hatch.getInstance().setExtended(true);
    }

    @Override
    public void teleopPeriodic() {
        System.out.println(Elevator.getInstance().getVoltage());
    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
        oi.processAxis();
    }

    @Override
    public void testInit() {
        currentState = GameState.TEST;
    }

    @Override
    public void testPeriodic() {
        Scheduler.getInstance().run();
    }

    public enum GameState {
        TELEOP,
        AUTONOMOUS,
        TEST,
        DISABLED
    }
}
