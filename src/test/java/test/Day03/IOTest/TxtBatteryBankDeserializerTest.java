package test.Day03.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day03.io.TxtBatteryBankDeserializer;
import software.ulpgc.aoc.day03.model.BatteryBank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtBatteryBankDeserializerTest {

    private final TxtBatteryBankDeserializer deserializer = new TxtBatteryBankDeserializer();

    @Test
    public void should_deserialize_battery_bank_correctly() {
        BatteryBank bank = deserializer.deserialize("987654321111111");
        assertThat(bank).isNotNull();
        assertThat(bank.digits()).isEqualTo("987654321111111");

        BatteryBank bankSpaced = deserializer.deserialize("  12345  ");
        assertThat(bankSpaced).isNotNull();
        assertThat(bankSpaced.digits()).isEqualTo("12345");
    }

    @Test
    public void should_throw_exception_when_line_is_null() {
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_line_is_empty() {
        assertThatThrownBy(() -> deserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_line_is_blank() {
        assertThatThrownBy(() -> deserializer.deserialize("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");
    }
}
