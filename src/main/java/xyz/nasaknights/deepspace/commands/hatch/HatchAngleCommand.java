package xyz.nasaknights.deepspace.commands.hatch;

import edu.wpi.first.wpilibj.command.PIDCommand;
import xyz.nasaknights.deepspace.subsystems.Hatch;

public class HatchAngleCommand extends PIDCommand {
    private static double kP = .01;
    private static double kI;
    private static double kD = .0001;
    private HatchAngles angle;

    public HatchAngleCommand(HatchAngles angle) {
        super("HatchAngle", kP, kI, kD);

        this.angle = angle;

        getPIDController().setSetpoint(this.angle.getEncoderValue());
        getPIDController().setOutputRange(-1, 1);
        getPIDController().setAbsoluteTolerance(40);
    }

    @Override
    protected double returnPIDInput() {
        return Hatch.getInstance().getEncoderTicks();
    }

    @Override
    protected void usePIDOutput(double output) {
        Hatch.getInstance().setPower(output * -1);
    }

    public HatchAngles getAngle() {
        return angle;
    }

    public void setAngle(HatchAngles angle) {
        this.angle = angle;
        getPIDController().setSetpoint(this.angle.getEncoderValue());
    }

    @Override
    protected boolean isFinished() {
        return getPIDController().onTarget();
    }

    public enum HatchAngles {
        TOP(530),
        HATCH(-268),
        CARGO_IN(-84);

        private int encoderValue;

        HatchAngles(int encoderValue) {
            this.encoderValue = encoderValue;
        }

        public static HatchAngles getNext(HatchAngles angle) {
            switch (angle) {
                case HATCH:
                    return CARGO_IN;
                default:
                    return TOP;
            }
        }

        public static HatchAngles getPrevious(HatchAngles angle) {
            switch (angle) {
                case TOP:
                    return CARGO_IN;
                default:
                    return HATCH;
            }
        }

        public int getEncoderValue() {
            return this.encoderValue;
        }
    }
}
