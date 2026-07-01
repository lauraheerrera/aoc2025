package test.Day08.BTest;

import org.junit.Test;
import software.ulpgc.aoc.day08.io.TxtJunctionBoxDeserializer;
import software.ulpgc.aoc.day08.model.Connection;
import software.ulpgc.aoc.day08.model.ConnectionGenerator;
import software.ulpgc.aoc.day08.model.JunctionBox;
import software.ulpgc.aoc.day08.model.Playground;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PlaygroundTest {

    private static final String example = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
            """;

    @Test
    public void should_compute_last_connection_coordinates_product() {

        // Given a set of junction boxes
        TxtJunctionBoxDeserializer deserializer = new TxtJunctionBoxDeserializer();

        List<JunctionBox> boxes = Arrays.stream(example.split("\n"))
                .map(deserializer::deserialize)
                .collect(Collectors.toList());

        // When generating connections
        List<Connection> connections = ConnectionGenerator.from(boxes);

        // And building the playground graph
        Playground playground = Playground.from(connections);

        long result = playground.lastConnectionCoordinatesProduct();

        // Then the final metric should match expected value
        assertThat(result).isEqualTo(25272L);
    }
}