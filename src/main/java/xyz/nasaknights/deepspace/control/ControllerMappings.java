package xyz.nasaknights.deepspace.control;

public class ControllerMappings {
    public enum PS4Controller {
        TRIANGLE(4),
        SQUARE(3),
        CIRCLE(2),
        X(1),
        RIGHT_BUMPER(6),
        LEFT_BUMPER(5),
        SHARE(7),
        OPTIONS(8),
        LEFT_JOYSTICK(9),
        RIGHT_JOYSTICK(10),
        LEFT_X_AXIS(0),
        LEFT_Y_AXIS(1),
        LEFT_TRIGGER(2),
        RIGHT_TRIGGER(3),
        RIGHT_X_AXIS(4),
        RIGHT_Y_AXIS(5);

        private int id;

        PS4Controller(int id) {
            this.id = id;
        }

        public int getID() {
            return this.id;
        }
    }

    public enum LogitechController {
        A(2),
        X(1),
        Y(4),
        B(3),
        LEFT_BUMPER(5),
        RIGHT_BUMPER(6),
        LEFT_TRIGGER(7),
        RIGHT_TRIGGER(8),
        BACK(9),
        START(10),
        LEFT_JOYSTICK(11),
        RIGHT_JOYSTICK(12),
        LEFT_X(0),
        LEFT_Y(1),
        RIGHT_X(2),
        RIGHT_Y(3);

        private int id;

        LogitechController(int id) {
            this.id = id;
        }

        public int getID() {
            return this.id;
        }
    }
}
