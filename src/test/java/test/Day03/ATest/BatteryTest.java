package test.Day03.ATest;

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
        assertThat(BatteryBank.create("987654321111111").maxJoltage()).isEqualTo(98);
        assertThat(BatteryBank.create("811111111111119").maxJoltage()).isEqualTo(89);
        assertThat(BatteryBank.create("234234234234278").maxJoltage()).isEqualTo(78);
        assertThat(BatteryBank.create("818181911112111").maxJoltage()).isEqualTo(92);
    }

    @Test
    public void calculator_sum_all_returns_correctly_sum(){
        assertThat(BatteryBankMaxJoltageCalculator.create()
                .add("987654321111111").sumAll()).isEqualTo(98);
        assertThat(BatteryBankMaxJoltageCalculator.create()
                .add("987654321111111","811111111111119").sumAll()).isEqualTo(187);
        assertThat(BatteryBankMaxJoltageCalculator.create()
                .add("987654321111111","811111111111119","234234234234278").sumAll()).isEqualTo(265);
            assertThat(BatteryBankMaxJoltageCalculator.create()
                .addAll(banks).sumAll()).isEqualTo(357);
    }

}
