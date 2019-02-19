package xyz.nasaknights.deepspace.control;

import edu.wpi.first.wpilibj.Joystick;
import xyz.nasaknights.deepspace.RobotMap;

public class JoystickFactory {
    public static Joystick getJoystick(Controllers controller) {
        Joystick js = controller.getJoystick();

        if (js == null) {
            js = new Joystick(controller.getID());
            controller.setJoystick(js);
        }

        return js;
    }

    public enum Controllers {
        DRIVER(RobotMap.kDriverControllerID),
        OPERATOR(RobotMap.kOperatorControllerID);

        private Joystick joystick;
        private int id;

        Controllers(int id) {
            this.id = id;
        }

        public int getID() {
            return this.id;
        }

        public Joystick getJoystick() {
            return this.joystick;
        }

        public void setJoystick(Joystick joystick) {
            this.joystick = joystick;
        }
    }
}
