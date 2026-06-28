package test.Day11.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day11.io.TxtDeviceDeserializer;
import software.ulpgc.aoc.day11.model.Device;
import software.ulpgc.aoc.day11.model.Network;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NetworkTest {
    private static final String EXAMPLE = """
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out
            """;

    @Test
    public void should_calculate_example_paths_correctly() {
        TxtDeviceDeserializer deserializer = new TxtDeviceDeserializer();
        List<Device> devices = Arrays.stream(EXAMPLE.split("\n"))
                .filter(s -> !s.isBlank())
                .map(deserializer::deserialize)
                .toList();

        assertThat(Network.from(devices).countPaths("you", "out")).isEqualTo(5L);
    }
}
