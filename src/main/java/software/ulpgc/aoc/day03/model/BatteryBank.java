package software.ulpgc.aoc.day03.model;

public class BatteryBank {

    private final String digits;

    public static BatteryBank create(String digits) {
        return new BatteryBank(digits);
    }

    private BatteryBank(String digits) {
        this.digits = digits;
    }

    public String digits() {
        return digits;
    }
}
