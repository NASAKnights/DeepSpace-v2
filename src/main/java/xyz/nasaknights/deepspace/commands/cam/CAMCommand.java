package xyz.nasaknights.deepspace.commands.cam;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import xyz.nasaknights.deepspace.subsystems.CAM;

/**
 * This command class is used to orchestrate the movement of the CAM wheels and the elevation of the robot to HAB level
 * two. This command is not feature-complete and should not be used.
 *
 * @author Bradley Hooten (hello@bradleyh.me)
 */
public class CAMCommand extends Command {
    private final PIDController left = new PIDController(0, 0, 0, new PIDSource() {
        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {

        }

        @Override
        public double pidGet() {
            return CAM.getInstance().getLeftPosition();
        }


    }, new PIDOutput() {
        @Override
        public void pidWrite(double output) {
            CAM.getInstance().setLeftPower(output);
        }
    });

    private final PIDController right = new PIDController(0, 0, 0, new PIDSource() {
        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {

        }

        @Override
        public double pidGet() {
            return CAM.getInstance().getRightPosition();
        }


    }, new PIDOutput() {
        @Override
        public void pidWrite(double output) {
            CAM.getInstance().setRightPower(output);
        }
    });

    /**
     * Serves as a placeholder constructor to instantiate a normal instance of this command. A normal instance will
     * rotate the CAM 360 degrees before stopping.
     */
    public CAMCommand() {
    }

    /**
     * Serves as a constructor to instantiate a instance of the command with a differing setpoint other than 360 degrees
     *
     * @param setpoint The value of the encoder to reach
     * @see CAMSetpoint
     */
    public CAMCommand(CAMSetpoint setpoint) {
        left.setSetpoint(setpoint.getSetpoint());
        right.setSetpoint(setpoint.getSetpoint());
    }

    @Override
    protected void initialize() {
        CAM.getInstance().setPower(.8);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        CAM.getInstance().setPower(0);
    }

    @Override
    protected void interrupted() {
        CAM.getInstance().setPower(0);
    }

    /**
     * Serves as an enumerator to fetch commonly used encoder values for the CAM.
     */
    public enum CAMSetpoint {
        STARTING(0),
        HALFWAY(1000),
        FULL(2000);

        private double setpoint;

        CAMSetpoint(double setpoint) {
            this.setpoint = setpoint;
        }

        public double getSetpoint() {
            return setpoint;
        }
    }
}
