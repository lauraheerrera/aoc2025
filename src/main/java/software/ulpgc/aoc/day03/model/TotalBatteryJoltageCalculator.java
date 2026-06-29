package software.ulpgc.aoc.day03.model;

import java.util.List;

public record TotalBatteryJoltageCalculator(BatteryBankMaxJoltageCalculator singleCalculator) {
    public Joltage sumAllMaxJoltageFrom(List<BatteryBank> batteryBanks) {
        return batteryBanks.stream()
                .map(singleCalculator::calculate)
                .reduce(Joltage.ZERO, Joltage::add);
    }
}
