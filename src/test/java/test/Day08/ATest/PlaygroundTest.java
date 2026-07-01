package test.Day08.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day08.io.TxtJunctionBoxDeserializer;
import software.ulpgc.aoc.day08.model.*;

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
    public void should_solve_full_playground_example() {

        // Given a set of junction boxes
        TxtJunctionBoxDeserializer deserializer = new TxtJunctionBoxDeserializer();

        List<JunctionBox> boxes = Arrays.stream(example.split("\n"))
                .map(deserializer::deserialize)
                .collect(Collectors.toList());

        // When generating connections and building the playground
        List<Connection> connections = ConnectionGenerator.from(boxes);
        Playground playground = Playground.from(connections);

        long result = playground.multiplyThreeLargestCircuitSizesAfterConnecting(10);

        // Then the final computed metric should match expected result
        assertThat(result).isEqualTo(40L);
    }

    @Test
    public void should_create_junction_box_correctly() {

        // Given a junction box
        JunctionBox box = new JunctionBox(1, 2, 3);

        // Then coordinates should be stored correctly
        assertThat(box.x()).isEqualTo(1);
        assertThat(box.y()).isEqualTo(2);
        assertThat(box.z()).isEqualTo(3);

        // And squared distance should be correct
        assertThat(box.squaredDistanceTo(new JunctionBox(4, 6, 8)))
                .isEqualTo(9 + 16 + 25);
    }

    @Test
    public void should_handle_disjoint_set_basic_operations() {

        // Given a disjoint set
        DisjointSet<String> ds = new DisjointSet<>();

        // Then initial state should be self-contained
        assertThat(ds.find("A")).isEqualTo("A");
        assertThat(ds.size("A")).isEqualTo(1);

        // When merging sets
        ds.union("A", "B");

        // Then they should share the same root
        assertThat(ds.find("A")).isEqualTo(ds.find("B"));
        assertThat(ds.size("A")).isEqualTo(2);
        assertThat(ds.size("B")).isEqualTo(2);

        // When merging more sets
        ds.union("C", "D");
        ds.union("B", "D");

        // Then all should belong to the same component
        assertThat(ds.find("A")).isEqualTo(ds.find("C"));
        assertThat(ds.size("A")).isEqualTo(4);
    }

    @Test
    public void should_return_correct_result_with_no_connections() {

        // Given isolated junction boxes
        JunctionBox b1 = new JunctionBox(0, 0, 0);
        JunctionBox b2 = new JunctionBox(1, 1, 1);
        JunctionBox b3 = new JunctionBox(2, 2, 2);

        List<Connection> connections =
                ConnectionGenerator.from(List.of(b1, b2, b3));

        Playground playground = Playground.from(connections);

        // When computing with zero allowed connections
        long result = playground.multiplyThreeLargestCircuitSizesAfterConnecting(0);

        // Then result should reflect isolated components
        assertThat(result).isEqualTo(1L);
    }

    @Test
    public void should_merge_components_with_one_connection() {

        // Given a small graph
        JunctionBox b1 = new JunctionBox(0, 0, 0);
        JunctionBox b2 = new JunctionBox(1, 1, 1);
        JunctionBox b3 = new JunctionBox(5, 5, 5);

        List<Connection> connections =
                ConnectionGenerator.from(List.of(b1, b2, b3));

        Playground playground = Playground.from(connections);

        // When allowing one merge
        long result = playground.multiplyThreeLargestCircuitSizesAfterConnecting(1);

        // Then components should partially merge
        assertThat(result).isEqualTo(2L);
    }

    @Test
    public void should_handle_redundant_unions() {

        // Given a disjoint set
        DisjointSet<String> ds = new DisjointSet<>();

        // When performing unions
        assertThat(ds.union("A", "B")).isTrue();
        assertThat(ds.union("A", "B")).isFalse();

        // Then size should remain consistent
        assertThat(ds.size("A")).isEqualTo(2);
    }

    @Test
    public void should_apply_path_compression_correctly() {

        // Given a disjoint set with multiple unions
        DisjointSet<String> ds = new DisjointSet<>();

        ds.union("A", "B");
        ds.union("C", "D");
        ds.union("E", "F");

        ds.union("B", "D");
        ds.union("D", "F");

        // Then all nodes should converge to same root
        assertThat(ds.find("A")).isEqualTo(ds.find("E"));
        assertThat(ds.size("A")).isEqualTo(6);
    }
}