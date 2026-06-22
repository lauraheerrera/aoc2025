package test.Day05.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day05.io.TxtIDDeserializer;
import software.ulpgc.aoc.day05.model.ID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtIDDeserializerTest {

    private final TxtIDDeserializer deserializer = new TxtIDDeserializer();

    @Test
    public void should_deserialize_id_correctly() {
        ID id = deserializer.deserialize("12345");
        assertThat(id).isNotNull();
        assertThat(id.value()).isEqualTo(12345L);

        ID idSpaced = deserializer.deserialize("  998877  ");
        assertThat(idSpaced).isNotNull();
        assertThat(idSpaced.value()).isEqualTo(998877L);
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
        assertThatThrownBy(() -> deserializer.deserialize("abc"))
                .isInstanceOf(NumberFormatException.class);
    }
}
