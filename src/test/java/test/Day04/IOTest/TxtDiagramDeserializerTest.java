package test.Day04.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day04.io.TxtDiagramDeserializer;
import software.ulpgc.aoc.day04.model.Tile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtDiagramDeserializerTest {

    private final TxtDiagramDeserializer deserializer = new TxtDiagramDeserializer();

    @Test
    public void should_deserialize_diagram_line_correctly() {
        Tile[] line = deserializer.deserialize(".@.");
        assertThat(line).isNotNull();
        assertThat(line).containsExactly(Tile.EMPTY, Tile.ROLL, Tile.EMPTY);
    }

    @Test
    public void should_throw_exception_when_line_is_null() {
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null");
    }
}
