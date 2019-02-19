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
}
