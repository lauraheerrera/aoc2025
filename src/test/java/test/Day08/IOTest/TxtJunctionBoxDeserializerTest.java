package test.Day08.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day08.io.TxtJunctionBoxDeserializer;
import software.ulpgc.aoc.day08.model.JunctionBox;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtJunctionBoxDeserializerTest {

    private final Deserializer<JunctionBox> deserializer =
            new TxtJunctionBoxDeserializer();

    @Test
    public void should_deserialize_junction_box_correctly() {

        // Given a valid junction box string
        String input1 = "10,20,30";

        // When deserializing
        JunctionBox box = deserializer.deserialize(input1);

        // Then coordinates should match
        assertThat(box).isNotNull();
        assertThat(box.x()).isEqualTo(10);
        assertThat(box.y()).isEqualTo(20);
        assertThat(box.z()).isEqualTo(30);

        // Given a spaced input
        String input2 = "  5 , 15 , 25  ";

        // When deserializing
        JunctionBox boxSpaced = deserializer.deserialize(input2);

        // Then values should still be parsed correctly
        assertThat(boxSpaced.x()).isEqualTo(5);
        assertThat(boxSpaced.y()).isEqualTo(15);
        assertThat(boxSpaced.z()).isEqualTo(25);
    }

    @Test
    public void should_throw_exception_when_input_is_null_or_empty() {

        // Given invalid inputs

        // When / Then null should fail
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");

        // When / Then empty should fail
        assertThatThrownBy(() -> deserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_format_is_invalid() {

        // Given malformed inputs

        // When / Then missing dimension
        assertThatThrownBy(() -> deserializer.deserialize("10,20"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid junction box format: 10,20");

        // When / Then too many dimensions
        assertThatThrownBy(() -> deserializer.deserialize("10,20,30,40"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid junction box format: 10,20,30,40");
    }

    @Test
    public void should_throw_exception_when_numbers_are_invalid() {

        // Given invalid numeric input

        // When / Then non-numeric value
        assertThatThrownBy(() -> deserializer.deserialize("10,abc,30"))
                .isInstanceOf(NumberFormatException.class);
    }
}