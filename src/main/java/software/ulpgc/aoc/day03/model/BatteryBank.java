package software.ulpgc.aoc.day03.model;

import java.util.stream.IntStream;

public record BatteryBank(String digits) {

    public int maxJoltage() {
        return generateNumbers(digits)
                .max()
                .orElse(-1);
    }

    public static BatteryBank create(String digits) {
        return new BatteryBank(digits);
    }

    private IntStream generateNumbers(String digits) {
        return IntStream.range(0, digits.length() - 1)
                .flatMap(i -> numbersFromIndex(digits, i));
    }

    private IntStream numbersFromIndex(String digits, int i) {
        return IntStream.range(i + 1, digits.length())
                .map(j -> pairToNumber(digits.charAt(i), digits.charAt(j)));
    }

    private int pairToNumber(char first, char second) {
        return charToInt(first) * 10 + charToInt(second);
    }

    private int charToInt(char character) {
        return character - '0';
    }
}
