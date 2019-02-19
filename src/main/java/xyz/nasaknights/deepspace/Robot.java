package xyz.nasaknights.deepspace;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import xyz.nasaknights.deepspace.commands.drive.DriveCommand;
import xyz.nasaknights.deepspace.subsystems.Drivetrain;
import xyz.nasaknights.deepspace.subsystems.Elevator;

public class Robot extends TimedRobot {
    private static GameState currentState;
    private Compressor compressor;

    @Override
    public void robotInit() {
        compressor = new Compressor(0);
        compressor.start();

        currentState = GameState.DISABLED;
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
    }

    @Override
    public void testInit() {
        currentState = GameState.TEST;
        compressor.start();
    }

    public enum GameState {
        TELEOP,
        AUTONOMOUS,
        TEST,
        DISABLED;
    }
}
