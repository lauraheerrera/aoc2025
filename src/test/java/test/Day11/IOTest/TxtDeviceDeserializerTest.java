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
        Device device = deserializer.deserialize("you: bbb ccc");
        assertThat(device.name()).isEqualTo("you");
        assertThat(device.outputs()).containsExactly("bbb", "ccc");
    }

    @Test
    public void should_deserialize_device_with_single_output() {
        Device device = deserializer.deserialize("zqk: svb");
        assertThat(device.name()).isEqualTo("zqk");
        assertThat(device.outputs()).containsExactly("svb");
    }

    @Test
    public void should_deserialize_device_with_no_outputs() {
        Device device = deserializer.deserialize("iii:");
        assertThat(device.name()).isEqualTo("iii");
        assertThat(device.outputs()).isEmpty();
    }

    @Test
    public void should_throw_exception_when_line_is_empty() {
        assertThatThrownBy(() -> deserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void should_throw_exception_when_line_is_null() {
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
