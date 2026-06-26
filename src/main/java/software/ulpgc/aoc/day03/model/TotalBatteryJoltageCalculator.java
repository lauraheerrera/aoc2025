package software.ulpgc.aoc.day03.model;

import java.util.List;

public record TotalBatteryJoltageCalculator(BatteryBankMaxJoltageCalculator singleCalculator, Length length) {
    public Joltage sumAllMaxJoltageFrom(List<BatteryBank> batteryBanks) {
        return batteryBanks.stream()
                .map(b -> singleCalculator.calculate(b, length))
                .reduce(Joltage.ZERO, Joltage::add);
    }
}
