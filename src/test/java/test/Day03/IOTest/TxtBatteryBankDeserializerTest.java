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

        // Given a valid battery bank string
        // When the deserializer parses it
        BatteryBank bank = deserializer.deserialize("987654321111111");

        // Then it should preserve the digits exactly
        assertThat(bank).isNotNull();
        assertThat(bank.digits()).isEqualTo("987654321111111");

        // Given a valid battery bank string with extra spaces
        // When the deserializer parses it
        BatteryBank bankSpaced = deserializer.deserialize("  12345  ");

        // Then it should trim and preserve digits correctly
        assertThat(bankSpaced).isNotNull();
        assertThat(bankSpaced.digits()).isEqualTo("12345");
    }

    @Test
    public void should_throw_exception_when_line_is_null() {

        // Given a null input
        // When deserializing
        // Then it should reject the input as invalid
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_line_is_empty() {

        // Given an empty string
        // When deserializing
        // Then it should reject the input as invalid
        assertThatThrownBy(() -> deserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_line_is_blank() {

        // Given a blank string with only spaces
        // When deserializing
        // Then it should reject the input as invalid
        assertThatThrownBy(() -> deserializer.deserialize("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");
    }
}