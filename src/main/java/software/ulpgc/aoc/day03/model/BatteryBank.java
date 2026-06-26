package software.ulpgc.aoc.day03.model;

public record BatteryBank(String digits) {
    public static BatteryBank create(String digits) {
        return new BatteryBank(digits);
    }
}
