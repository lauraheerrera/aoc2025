package software.ulpgc.aoc.day03.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BatteryBankMaxJoltageCalculator {
    private final List<BatteryBank> batteryBankList;
    private final int length;

    public BatteryBankMaxJoltageCalculator(int length) {
        this.length = length;
        this.batteryBankList = new ArrayList<>();
    }

    public static BatteryBankMaxJoltageCalculator create(int length) {
        return new BatteryBankMaxJoltageCalculator(length);
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

    public long sumAll() {
        return batteryBankList.stream()
                .mapToLong(this::maxJoltage)
                .sum();
    }

    private long maxJoltage(BatteryBank b) {
        return b.maxJoltageOfLength(length);
    }

}
