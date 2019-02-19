package xyz.nasaknights.deepspace.util.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Lazy_DoubleSolenoid extends DoubleSolenoid {
    private Value lastValue;

    public Lazy_DoubleSolenoid(int forwardChannel, int reverseChannel) {
        super(forwardChannel, reverseChannel);
    }

    public Value getLastValue() {
        return lastValue;
    }

    @Override
    public void set(Value value) {
        if (lastValue != value) {
            lastValue = value;
            super.set(value);
        }
    }
}
