package xyz.nasaknights.deepspace.commands.drive;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.Drivetrain;
import xyz.nasaknights.deepspace.util.networking.VisionClient;

public class DriveAssistCommand extends Command {
    private final PIDController x = new PIDController(.01, 0, 0, new PIDSource() {
        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {

        }

        @Override
        public double pidGet() {
            return VisionClient.getInstance().getX();
        }


    }, new PIDOutput() {
        @Override
        public void pidWrite(double output) {
            Drivetrain.getInstance().setMiddlePower(output);
        }
    });

    private final PIDController y = new PIDController(0.01, 0, 0, new PIDSource() {
        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {

        }

        @Override
        public double pidGet() {
            return VisionClient.getInstance().getY();
        }


    }, new PIDOutput() {
        @Override
        public void pidWrite(double output) {
            Drivetrain.getInstance().setPower(output);
        }
    });

    private final PIDController rotation = new PIDController(0, 0, 0, new PIDSource() {
        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {

        }

        @Override
        public double pidGet() {
            return VisionClient.getInstance().getRotation();
        }


    }, new PIDOutput() {
        @Override
        public void pidWrite(double output) {
            Drivetrain.getInstance().setLeftPower(output);
            Drivetrain.getInstance().setRightPower(-output);
        }
    });

    public DriveAssistCommand() {
        x.setAbsoluteTolerance(3);
        y.setAbsoluteTolerance(3);
        rotation.setAbsoluteTolerance(5);

        y.setSetpoint(35);
        rotation.setSetpoint(0);
        x.setSetpoint(0);
    }

    @Override
    protected void initialize() {
//        x.enable();
        y.enable();
//        rotation.enable();
    }

    @Override
    protected void execute() {
        System.out.println(VisionClient.getInstance().getY());
    }

    @Override
    protected boolean isFinished() {
//        return rotation.onTarget() && x.onTarget() && y.onTarget();
        return y.onTarget();
    }

    @Override
    protected void end() {
//        x.disable();
        y.disable();
//        rotation.disable();
    }

    @Override
    protected void interrupted() {
//        x.disable();
        y.disable();
//        rotation.disable();
    }
}
