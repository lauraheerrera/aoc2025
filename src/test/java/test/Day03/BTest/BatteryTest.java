package test.Day03.BTest;

import org.junit.Test;
import software.ulpgc.aoc.day03.model.BatteryBank;
import software.ulpgc.aoc.day03.model.BatteryBankMaxJoltageCalculator;

import static org.assertj.core.api.Assertions.assertThat;

public class BatteryTest {
    private static final String banks= """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
            """;

    @Test
    public void battery_bank_max_joltage_should_be_correctly(){
        assertThat(BatteryBank.create("987654321111111").maxJoltageOfLength(12)).isEqualTo(987654321111L);
        assertThat(BatteryBank.create("811111111111119").maxJoltageOfLength(12)).isEqualTo(811111111119L);
        assertThat(BatteryBank.create("234234234234278").maxJoltageOfLength(12)).isEqualTo(434234234278L);
        assertThat(BatteryBank.create("818181911112111").maxJoltageOfLength(12)).isEqualTo(888911112111L);
    }

    @Test
    public void calculator_sum_all_returns_correctly_sum(){
        assertThat(BatteryBankMaxJoltageCalculator.create(12)
                .add("987654321111111").sumAll()).isEqualTo(987654321111L);
        assertThat(BatteryBankMaxJoltageCalculator.create(12).addAll(banks).sumAll()).isEqualTo(3121910778619L);
    }

}
