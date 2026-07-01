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

        // Given a textual definition of a network
        String input = EXAMPLE;

        // And a deserializer that converts text into domain devices
        TxtDeviceDeserializer deserializer = new TxtDeviceDeserializer();

        // And a list of devices built from the input
        List<Device> devices = Arrays.stream(input.split("\n"))
                .filter(line -> !line.isBlank())
                .map(deserializer::deserialize)
                .toList();

        // And a network built from those devices
        Network network = Network.from(devices);

        // When calculating the number of paths from "you" to "out"
        long result = network.countPaths("you", "out");

        // Then the number of paths should match the expected value
        assertThat(result).isEqualTo(5L);
    }
}