package xyz.nasaknights.deepspace;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import xyz.nasaknights.deepspace.commands.drive.DriveCommand;
import xyz.nasaknights.deepspace.subsystems.Drivetrain;
import xyz.nasaknights.deepspace.subsystems.Elevator;
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

        oi.prepareInputs();

        CameraUtil.getCamera().setResolution(640, 480);
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
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
        oi.processAxis();

        System.out.println(Elevator.getInstance().getEncoderHeight());
    }

    @Override
    public void testInit() {
        currentState = GameState.TEST;
    }

    public enum GameState {
        TELEOP,
        AUTONOMOUS,
        TEST,
        DISABLED;
    }
}
