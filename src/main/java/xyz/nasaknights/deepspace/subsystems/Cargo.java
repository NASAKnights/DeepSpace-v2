package xyz.nasaknights.deepspace.subsystems;

import xyz.nasaknights.deepspace.RobotMap;
import xyz.nasaknights.deepspace.util.motors.Lazy_VictorSPX;
import xyz.nasaknights.deepspace.util.motors.factory.VictorSPXFactory;

public class Cargo {
    private static Cargo instance;

    private Lazy_VictorSPX left;
    private Lazy_VictorSPX right;

    public Cargo() {
        left = VictorSPXFactory.createVictor(RobotMap.kLeftIntakeVictorID);
        right = VictorSPXFactory.createVictor(RobotMap.kRightIntakeVictorID);
    }

    public static Cargo getInstance() {
        if (instance == null) {
            instance = new Cargo();
        }

        return instance;
    }
}
