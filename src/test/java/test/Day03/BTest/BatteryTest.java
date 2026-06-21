package test.Day03.BTest;

import org.junit.Test;
import software.ulpgc.aoc.day03.model.BatteryBank;
import software.ulpgc.aoc.day03.model.BatteryBankMaxJoltageCalculator;
import software.ulpgc.aoc.day03.model.TotalBatteryJoltageCalculator;
import software.ulpgc.aoc.day03.model.Joltage;
import software.ulpgc.aoc.day03.model.Length;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BatteryTest {
    private static final String banks = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
            """;

    @Test
    public void battery_bank_max_joltage_should_be_correctly() {
        Length length = new Length(12);
        assertThat(bank(length, "987654321111111")).isEqualTo(new Joltage(987654321111L));
        assertThat(bank(length, "811111111111119")).isEqualTo(new Joltage(811111111119L));
        assertThat(bank(length, "234234234234278")).isEqualTo(new Joltage(434234234278L));
        assertThat(bank(length, "818181911112111")).isEqualTo(new Joltage(888911112111L));
    }

    private Joltage bank(Length length, String digits) {
        return new BatteryBankMaxJoltageCalculator().calculate(BatteryBank.create(digits), length);
    }

    @Test
    public void calculator_sum_all_returns_correctly_sum() {
        Length length = new Length(12);
        TotalBatteryJoltageCalculator totalCalculator = new TotalBatteryJoltageCalculator(
                new BatteryBankMaxJoltageCalculator(), length);
        assertThat(totalCalculator.sumAllMaxJoltageFrom(parse("987654321111111"))).isEqualTo(new Joltage(987654321111L));
        assertThat(totalCalculator.sumAllMaxJoltageFrom(parseMultiline(banks))).isEqualTo(new Joltage(3121910778619L));
    }

    private List<BatteryBank> parse(String... batteryBanks) {
        return Arrays.stream(batteryBanks)
                .map(BatteryBank::create)
                .toList();
    }

    private List<BatteryBank> parseMultiline(String batteryBanks) {
        return parse(batteryBanks.split("\n"));
    }
}
