package test.Day09.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day09.io.TxtPointDeserializer;
import software.ulpgc.aoc.day09.model.Tile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtPointDeserializerTest {

    private final TxtPointDeserializer deserializer = new TxtPointDeserializer();

    @Test
    public void should_deserialize_valid_points_correctly() {

        // Given a valid point string
        String input1 = "10,20";

        // When deserializing the point
        Tile point = deserializer.deserialize(input1);

        // Then coordinates should be parsed correctly
        assertThat(point).isNotNull();
        assertThat(point.x()).isEqualTo(10);
        assertThat(point.y()).isEqualTo(20);

        // Given a spaced point string
        String input2 = "  5 , 15  ";

        // When deserializing the point
        Tile pointSpaced = deserializer.deserialize(input2);

        // Then whitespace should be ignored and values parsed correctly
        assertThat(pointSpaced).isNotNull();
        assertThat(pointSpaced.x()).isEqualTo(5);
        assertThat(pointSpaced.y()).isEqualTo(15);
    }

    @Test
    public void should_throw_exception_when_line_is_null_or_empty() {

        // Given invalid input: null
        // When / Then it should fail
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");

        // Given invalid input: empty string
        // When / Then it should fail
        assertThatThrownBy(() -> deserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_format_is_invalid() {

        // Given malformed point strings

        // When / Then invalid format should fail
        assertThatThrownBy(() -> deserializer.deserialize("10"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid point format: 10");

        assertThatThrownBy(() -> deserializer.deserialize("10,20,30"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid point format: 10,20,30");
    }

    @Test
    public void should_throw_exception_when_numbers_are_invalid() {

        // Given non-numeric input
        String input = "10,abc";

        // When / Then parsing should fail with NumberFormatException
        assertThatThrownBy(() -> deserializer.deserialize(input))
                .isInstanceOf(NumberFormatException.class);
    }
}