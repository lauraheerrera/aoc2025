package software.ulpgc.aoc.day03.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BatteryBankMaxJoltageCalculator {
    private final List<BatteryBank> batteryBankList;

    public BatteryBankMaxJoltageCalculator() {
        this.batteryBankList = new ArrayList<>();
    }

    public static BatteryBankMaxJoltageCalculator create() {
        return new BatteryBankMaxJoltageCalculator();
    }

    public BatteryBankMaxJoltageCalculator addAll(List<BatteryBank> batteryBanks) {
        batteryBanks.forEach(this::add);
        return this;
    }

    public BatteryBankMaxJoltageCalculator addAll(String batteryBanks) {
        return add(batteryBanks.split("\n"));
    }

    public BatteryBankMaxJoltageCalculator add(String... batteryBanks) {
        Arrays.stream(batteryBanks)
                .map(BatteryBank::new)
                .forEach(this::add);
        return this;
    }

    private void add(BatteryBank batteryBank){
        batteryBankList.add(batteryBank);
    }

    public int sumAll() {
        return batteryBankList.stream()
                .mapToInt(this::maxJoltage)
                .sum();
    }

    private int maxJoltage(BatteryBank b) {
        return b.maxJoltage();
    }

}
