package test.Day11.BTest;

import org.junit.Test;
import software.ulpgc.aoc.day11.io.TxtDeviceDeserializer;
import software.ulpgc.aoc.day11.model.Device;
import software.ulpgc.aoc.day11.model.Network;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NetworkTest {
    @Test
    public void should_calculate_part_2_restricted_paths_correctly() {
        String example = """
                svr: a b
                a: dac
                b: dac
                dac: c d e
                c: fft
                d: fft
                e: fft
                fft: out
                """;

        TxtDeviceDeserializer deserializer = new TxtDeviceDeserializer();
        List<Device> devices = Arrays.stream(example.split("\n"))
                .filter(s -> !s.isBlank())
                .map(deserializer::deserialize)
                .toList();

        Network network = new Network(devices);

        long path1 = network.countPaths("svr", "dac") * network.countPaths("dac", "fft")
                * network.countPaths("fft", "out");
        long path2 = network.countPaths("svr", "fft") * network.countPaths("fft", "dac")
                * network.countPaths("dac", "out");
        long totalPaths = path1 + path2;

        assertThat(totalPaths).isEqualTo(6L);
    }
}
