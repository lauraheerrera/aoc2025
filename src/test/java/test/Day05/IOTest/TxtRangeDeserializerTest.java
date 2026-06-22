package test.Day05.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day05.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day05.model.ID;
import software.ulpgc.aoc.day05.model.Range;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtRangeDeserializerTest {

    private final TxtRangeDeserializer deserializer = new TxtRangeDeserializer();

    @Test
    public void should_deserialize_range_correctly() {
        Range range = deserializer.deserialize("123-456");
        assertThat(range).isNotNull();
        assertThat(range.start()).isEqualTo(new ID(123L));
        assertThat(range.end()).isEqualTo(new ID(456L));

        Range rangeSpaced = deserializer.deserialize("  9988 - 10022  ");
        assertThat(rangeSpaced).isNotNull();
        assertThat(rangeSpaced.start()).isEqualTo(new ID(9988L));
        assertThat(rangeSpaced.end()).isEqualTo(new ID(10022L));
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

    @Test
    public void should_throw_exception_when_format_is_invalid() {
        assertThatThrownBy(() -> deserializer.deserialize("123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid range format: 123");

        assertThatThrownBy(() -> deserializer.deserialize("123-456-789"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid range format: 123-456-789");
    }

    @Test
    public void should_throw_exception_when_number_is_invalid() {
        assertThatThrownBy(() -> deserializer.deserialize("abc-456"))
                .isInstanceOf(NumberFormatException.class);

        assertThatThrownBy(() -> deserializer.deserialize("123-xyz"))
                .isInstanceOf(NumberFormatException.class);
    }
}
