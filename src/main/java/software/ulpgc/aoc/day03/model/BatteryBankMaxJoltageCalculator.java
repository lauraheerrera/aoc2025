package software.ulpgc.aoc.day03.model;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BatteryBankMaxJoltageCalculator {
    private final Length length;

    private BatteryBankMaxJoltageCalculator(Length length) {
        this.length = length;
    }

    public static BatteryBankMaxJoltageCalculator of(Length length) {
        return new BatteryBankMaxJoltageCalculator(length);
    }

    public Joltage calculate(BatteryBank batteryBank) {
        String result = Stream.iterate(
                new Selector(batteryBank.digits(), 0, length.value()),
                Selector::hasNext,
                Selector::next)
                .map(Selector::bestDigit)
                .collect(Collectors.joining());

        return new Joltage(Long.parseLong(result));
    }

    private record Selector(String digits, int start, int digitsNeeded) {

        boolean hasNext() {
            return digitsNeeded > 0;
        }

        Selector next() {
            return new Selector(digits, bestIndex() + 1, digitsNeeded - 1);
        }

        String bestDigit() {
            return String.valueOf(digits.charAt(bestIndex()));
        }

        int bestIndex() {
            return IntStream.rangeClosed(start, digits.length() - digitsNeeded)
                    .reduce((i, j) -> digits.charAt(i) >= digits.charAt(j) ? i : j)
                    .orElse(start);
        }
    }
}
