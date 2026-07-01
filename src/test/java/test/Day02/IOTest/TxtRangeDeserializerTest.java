package test.Day02.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day02.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day02.model.IdRange;
import software.ulpgc.aoc.day02.a.model.Id;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtRangeDeserializerTest {

    private final TxtRangeDeserializer<Id> deserializer =
            new TxtRangeDeserializer<>(Id::create);

    @Test
    public void should_deserialize_ranges_correctly() {

        // Given a valid range string
        String input1 = "11-22";

        // When deserializing
        IdRange<Id> range = deserializer.deserialize(input1);

        // Then start and end should be parsed correctly
        assertThat(range).isNotNull();
        assertThat(range.start()).isEqualTo(11);
        assertThat(range.end()).isEqualTo(22);

        // Given a larger range
        String input2 = "998-1012";

        // When deserializing
        IdRange<Id> rangeLarge = deserializer.deserialize(input2);

        // Then values should be correct
        assertThat(rangeLarge.start()).isEqualTo(998);
        assertThat(rangeLarge.end()).isEqualTo(1012);
    }

    @Test
    public void should_throw_exception_when_input_is_null_empty_or_blank() {

        // Given invalid inputs

        // When / Then null should fail
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Input cannot be null or empty");

        // When / Then empty should fail
        assertThatThrownBy(() -> deserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Input cannot be null or empty");

        // When / Then blank should fail
        assertThatThrownBy(() -> deserializer.deserialize("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Input cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_format_is_invalid() {

        // Given malformed range formats

        // When / Then missing dash should fail
        assertThatThrownBy(() -> deserializer.deserialize("11"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid range format: 11");

        // When / Then too many parts should fail
        assertThatThrownBy(() -> deserializer.deserialize("11-22-33"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid range format: 11-22-33");
    }

    @Test
    public void should_throw_exception_when_numbers_are_invalid() {

        // Given invalid numeric values

        // When / Then invalid right value
        assertThatThrownBy(() -> deserializer.deserialize("11-abc"))
                .isInstanceOf(NumberFormatException.class);

        // When / Then invalid left value
        assertThatThrownBy(() -> deserializer.deserialize("xyz-22"))
                .isInstanceOf(NumberFormatException.class);
    }
}