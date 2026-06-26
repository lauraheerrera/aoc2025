package test.Day10.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day10.a.io.TxtMachineDeserializer;
import software.ulpgc.aoc.day10.a.model.Machine;
import software.ulpgc.aoc.day10.model.Button;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtMachineDeserializerTest {

    private final TxtMachineDeserializer deserializer = new TxtMachineDeserializer();

    @Test
    public void should_deserialize_machine_correctly() {
        Machine machine = deserializer.deserialize("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1)");
        assertThat(machine).isNotNull();
        assertThat(machine.targetMask()).isEqualTo(6L);
        assertThat(machine.buttons()).containsExactly(
                new Button(List.of(3)),
                new Button(List.of(1, 3)),
                new Button(List.of(2)),
                new Button(List.of(2, 3)),
                new Button(List.of(0, 2)),
                new Button(List.of(0, 1)));
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
    public void should_throw_exception_when_brackets_are_missing() {
        assertThatThrownBy(() -> deserializer.deserialize(".##. (3) (1,3)"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Missing or invalid target diagram bracket format");
    }

    @Test
    public void should_throw_exception_when_numbers_are_invalid() {
        assertThatThrownBy(() -> deserializer.deserialize("[.##.] (abc)"))
                .isInstanceOf(NumberFormatException.class);
    }
}
