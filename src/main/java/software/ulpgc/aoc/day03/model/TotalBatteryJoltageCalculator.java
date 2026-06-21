package software.ulpgc.aoc.day03.model;

import java.util.List;

public class TotalBatteryJoltageCalculator {
    private final BatteryBankMaxJoltageCalculator singleCalculator;
    private final Length length;

    public TotalBatteryJoltageCalculator(BatteryBankMaxJoltageCalculator singleCalculator, Length length) {
        this.singleCalculator = singleCalculator;
        this.length = length;
    }

    public Joltage sumAllMaxJoltageFrom(List<BatteryBank> batteryBanks) {
        return batteryBanks.stream()
                .map(b -> singleCalculator.calculate(b, length))
                .reduce(Joltage.ZERO, Joltage::add);
    }
}
