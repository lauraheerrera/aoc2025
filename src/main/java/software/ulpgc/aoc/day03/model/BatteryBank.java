package software.ulpgc.aoc.day03.model;

import java.util.stream.IntStream;

public class BatteryBank {
    private final String digits;

    private BatteryBank(String digits) {
        this.digits = digits;
    }

    public static BatteryBank create(String digits) {
        return new BatteryBank(digits);
    }

    public long maxJoltageOfLength(int length) {
        return Long.parseLong(buildMaxNumber(0, length));
    }

    private String buildMaxNumber(int start, int digitsNeeded) {
        return (digitsNeeded == 0) ? ""
                : selectDigitAndRecurse(findMaxIndex(start, digits.length() - digitsNeeded), digitsNeeded);
    }

    private String selectDigitAndRecurse(int index, int digitsNeeded) {
        return digits.charAt(index) + buildMaxNumber(index + 1, digitsNeeded - 1);
    }

    private int findMaxIndex(int start, int digitsNeeded) {
        return IntStream.rangeClosed(start, digitsNeeded)
                .reduce((i, j) -> digits.charAt(i) >= digits.charAt(j) ? i : j)
                .orElse(start);
    }
}
