package test.Day03.ATest;

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
    public void battery_bank_digits_should_be_stored_correctly() {
        BatteryBank bank = BatteryBank.create("12345");
        assertThat(bank.digits()).isEqualTo("12345");
    }

    @Test
    public void battery_bank_max_joltage_should_be_correctly() {
        Length length = new Length(2);
        assertThat(bank(length, "987654321111111")).isEqualTo(new Joltage(98));
        assertThat(bank(length, "811111111111119")).isEqualTo(new Joltage(89));
        assertThat(bank(length, "234234234234278")).isEqualTo(new Joltage(78));
        assertThat(bank(length, "818181911112111")).isEqualTo(new Joltage(92));
    }

    private Joltage bank(Length length, String digits) {
        return new BatteryBankMaxJoltageCalculator().calculate(BatteryBank.create(digits), length);
    }

    @Test
    public void calculator_should_add_multiple_banks_with_varargs() {
        TotalBatteryJoltageCalculator totalCalculator = new TotalBatteryJoltageCalculator(
                new BatteryBankMaxJoltageCalculator(), new Length(2));
        assertThat(totalCalculator.sumAllMaxJoltageFrom(parse("987654321111111", "811111111111119")))
                .isEqualTo(new Joltage(187));
    }

    @Test
    public void calculator_should_add_multiple_banks_with_list() {
        List<BatteryBank> list = List.of(
                BatteryBank.create("987654321111111"),
                BatteryBank.create("811111111111119"));
        TotalBatteryJoltageCalculator totalCalculator = new TotalBatteryJoltageCalculator(
                new BatteryBankMaxJoltageCalculator(), new Length(2));
        assertThat(totalCalculator.sumAllMaxJoltageFrom(list)).isEqualTo(new Joltage(187));
    }

    @Test
    public void calculator_sum_all_returns_correctly_sum() {
        Length length = new Length(2);
        TotalBatteryJoltageCalculator totalCalculator = new TotalBatteryJoltageCalculator(
                new BatteryBankMaxJoltageCalculator(), length);
        assertThat(totalCalculator.sumAllMaxJoltageFrom(parse("987654321111111"))).isEqualTo(new Joltage(98));
        assertThat(totalCalculator.sumAllMaxJoltageFrom(parse("987654321111111", "811111111111119")))
                .isEqualTo(new Joltage(187));
        assertThat(totalCalculator.sumAllMaxJoltageFrom(parse("987654321111111", "811111111111119", "234234234234278")))
                .isEqualTo(new Joltage(265));
        assertThat(totalCalculator.sumAllMaxJoltageFrom(parseMultiline(banks))).isEqualTo(new Joltage(357));
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
