package test.Day02.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day02.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day02.model.IdRange;
import software.ulpgc.aoc.day02.a.model.Id;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtRangeDeserializerTest {

    private final TxtRangeDeserializer<Id> deserializer = new TxtRangeDeserializer<>(Id::create);

    @Test
    public void should_deserialize_ranges_correctly() {
        IdRange<Id> range = deserializer.deserialize("11-22");
        assertThat(range).isNotNull();
        assertThat(range.start()).isEqualTo(11);
        assertThat(range.end()).isEqualTo(22);

        IdRange<Id> rangeLarge = deserializer.deserialize("998-1012");
        assertThat(rangeLarge.start()).isEqualTo(998);
        assertThat(rangeLarge.end()).isEqualTo(1012);
    }

    @Test
    public void should_throw_exception_when_input_is_null() {
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Input cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_input_is_empty() {
        assertThatThrownBy(() -> deserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Input cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_input_is_blank() {
        assertThatThrownBy(() -> deserializer.deserialize("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Input cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_format_is_invalid() {
        assertThatThrownBy(() -> deserializer.deserialize("11"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid range format: 11");

        assertThatThrownBy(() -> deserializer.deserialize("11-22-33"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid range format: 11-22-33");
    }

    @Test
    public void should_throw_exception_when_number_is_invalid() {
        assertThatThrownBy(() -> deserializer.deserialize("11-abc"))
                .isInstanceOf(NumberFormatException.class);

        assertThatThrownBy(() -> deserializer.deserialize("xyz-22"))
                .isInstanceOf(NumberFormatException.class);
    }
}
