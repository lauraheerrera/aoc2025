package test.Day11.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day11.io.TxtDeviceDeserializer;
import software.ulpgc.aoc.day11.model.Device;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtDeviceDeserializerTest {

    private final TxtDeviceDeserializer deserializer = new TxtDeviceDeserializer();

    @Test
    public void should_deserialize_device_with_multiple_outputs() {

        // Given a line with multiple outputs
        String line = "you: bbb ccc";

        // When deserializing the line
        Device device = deserializer.deserialize(line);

        // Then the device name is parsed correctly
        assertThat(device.name()).isEqualTo("you");

        // And the outputs are parsed correctly
        assertThat(device.outputs()).containsExactly("bbb", "ccc");
    }

    @Test
    public void should_deserialize_device_with_single_output() {

        // Given a line with a single output
        String line = "zqk: svb";

        // When deserializing the line
        Device device = deserializer.deserialize(line);

        // Then the device name is parsed correctly
        assertThat(device.name()).isEqualTo("zqk");

        // And the output is parsed correctly
        assertThat(device.outputs()).containsExactly("svb");
    }

    @Test
    public void should_deserialize_device_with_no_outputs() {

        // Given a line with no outputs
        String line = "iii:";

        // When deserializing the line
        Device device = deserializer.deserialize(line);

        // Then the device name is parsed correctly
        assertThat(device.name()).isEqualTo("iii");

        // And there are no outputs
        assertThat(device.outputs()).isEmpty();
    }

    @Test
    public void should_throw_exception_when_line_is_empty() {

        // Given an empty line
        String line = "";

        // When / Then an exception is thrown
        assertThatThrownBy(() -> deserializer.deserialize(line))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void should_throw_exception_when_line_is_null() {

        // Given a null line
        String line = null;

        // When / Then an exception is thrown
        assertThatThrownBy(() -> deserializer.deserialize(line))
                .isInstanceOf(IllegalArgumentException.class);
    }
}