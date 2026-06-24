package test.Day08.IOTest;

import org.junit.Test;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day08.io.TxtJunctionBoxDeserializer;
import software.ulpgc.aoc.day08.model.JunctionBox;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtJunctionBoxDeserializerTest {

    private final Deserializer<JunctionBox> deserializer = new TxtJunctionBoxDeserializer();

    @Test
    public void should_deserialize_junction_box_correctly() {
        JunctionBox box = deserializer.deserialize("10,20,30");
        assertThat(box).isNotNull();
        assertThat(box.x()).isEqualTo(10);
        assertThat(box.y()).isEqualTo(20);
        assertThat(box.z()).isEqualTo(30);

        JunctionBox boxSpaced = deserializer.deserialize("  5 , 15 , 25  ");
        assertThat(boxSpaced).isNotNull();
        assertThat(boxSpaced.x()).isEqualTo(5);
        assertThat(boxSpaced.y()).isEqualTo(15);
        assertThat(boxSpaced.z()).isEqualTo(25);
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
        assertThatThrownBy(() -> deserializer.deserialize("10,20"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid junction box format: 10,20");

        assertThatThrownBy(() -> deserializer.deserialize("10,20,30,40"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid junction box format: 10,20,30,40");
    }

    @Test
    public void should_throw_exception_when_numbers_are_invalid() {
        assertThatThrownBy(() -> deserializer.deserialize("10,abc,30"))
                .isInstanceOf(NumberFormatException.class);
    }
}
