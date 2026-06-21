package software.ulpgc.aoc.day03.model;

public class BatteryBank {
    private final String digits;

    private BatteryBank(String digits) {
        this.digits = digits;
    }

    public static BatteryBank create(String digits) {
        return new BatteryBank(digits);
    }

    public String digits() {
        return digits;
    }
}
