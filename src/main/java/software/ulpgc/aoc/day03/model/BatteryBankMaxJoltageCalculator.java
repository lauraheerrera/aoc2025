package software.ulpgc.aoc.day03.model;

import java.util.stream.IntStream;

public class BatteryBankMaxJoltageCalculator {

    public Joltage calculate(BatteryBank batteryBank, Length length) {
        String digits = batteryBank.digits();
        return new Joltage(Long.parseLong(buildMaxNumber(digits, 0, length.value())));
    }

    private String buildMaxNumber(String digits, int start, int digitsNeeded) {
        return (digitsNeeded == 0) ? ""
                : selectDigitAndRecurse(digits, findMaxIndex(digits, start, digits.length() - digitsNeeded), digitsNeeded);
    }

    private String selectDigitAndRecurse(String digits, int index, int digitsNeeded) {
        return digits.charAt(index) + buildMaxNumber(digits, index + 1, digitsNeeded - 1);
    }

    private int findMaxIndex(String digits, int start, int digitsNeeded) {
        return IntStream.rangeClosed(start, digitsNeeded)
                .reduce((i, j) -> digits.charAt(i) >= digits.charAt(j) ? i : j)
                .orElse(start);
    }
}
