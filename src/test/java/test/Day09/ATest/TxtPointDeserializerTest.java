package test.Day09.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day09.io.TxtPointDeserializer;
import software.ulpgc.aoc.day09.model.Tile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtPointDeserializerTest {

    private final TxtPointDeserializer deserializer = new TxtPointDeserializer();

    @Test
    public void should_deserialize_point_correctly() {
        Tile point = deserializer.deserialize("10,20");
        assertThat(point).isNotNull();
        assertThat(point.x()).isEqualTo(10);
        assertThat(point.y()).isEqualTo(20);

        Tile pointSpaced = deserializer.deserialize("  5 , 15  ");
        assertThat(pointSpaced).isNotNull();
        assertThat(pointSpaced.x()).isEqualTo(5);
        assertThat(pointSpaced.y()).isEqualTo(15);
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
    public void should_throw_exception_when_format_is_invalid() {
        assertThatThrownBy(() -> deserializer.deserialize("10"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid point format: 10");

        assertThatThrownBy(() -> deserializer.deserialize("10,20,30"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid point format: 10,20,30");
    }

    @Test
    public void should_throw_exception_when_numbers_are_invalid() {
        assertThatThrownBy(() -> deserializer.deserialize("10,abc"))
                .isInstanceOf(NumberFormatException.class);
    }
}
