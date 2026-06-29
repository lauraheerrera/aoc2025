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
    public void solve_example() {
        TxtJunctionBoxDeserializer deserializer = new TxtJunctionBoxDeserializer();
        List<JunctionBox> boxes = Arrays.stream(example.split("\n"))
                .map(deserializer::deserialize)
                .collect(Collectors.toList());

        long result = new Playground(boxes).multiplyThreeLargestCircuitSizesAfterConnecting(10);
        assertThat(result).isEqualTo(40L);
    }

    @Test
    public void test_junction_box() {
        JunctionBox box = new JunctionBox(1, 2, 3);
        assertThat(box.x()).isEqualTo(1);
        assertThat(box.y()).isEqualTo(2);
        assertThat(box.z()).isEqualTo(3);
        assertThat(box.squaredDistanceTo(new JunctionBox(4, 6, 8))).isEqualTo(9 + 16 + 25);
    }

    @Test
    public void test_disjoint_set() {
        DisjointSet<String> ds = new DisjointSet<>();
        assertThat(ds.find("A")).isEqualTo("A");
        assertThat(ds.size("A")).isEqualTo(1);

        ds.union("A", "B");
        assertThat(ds.find("A")).isEqualTo(ds.find("B"));
        assertThat(ds.size("A")).isEqualTo(2);
        assertThat(ds.size("B")).isEqualTo(2);

        ds.union("C", "D");
        ds.union("B", "D");
        assertThat(ds.find("A")).isEqualTo(ds.find("C"));
        assertThat(ds.size("A")).isEqualTo(4);
    }

    @Test
    public void test_playground_part_a_no_connections() {
        JunctionBox b1 = new JunctionBox(0, 0, 0);
        JunctionBox b2 = new JunctionBox(1, 1, 1);
        JunctionBox b3 = new JunctionBox(2, 2, 2);
        Playground playground = new Playground(List.of(b1, b2, b3));

        assertThat(playground.multiplyThreeLargestCircuitSizesAfterConnecting(0)).isEqualTo(1L);
    }

    @Test
    public void test_playground_part_a_one_connection() {
        JunctionBox b1 = new JunctionBox(0, 0, 0);
        JunctionBox b2 = new JunctionBox(1, 1, 1);
        JunctionBox b3 = new JunctionBox(5, 5, 5);
        Playground playground = new Playground(List.of(b1, b2, b3));

        assertThat(playground.multiplyThreeLargestCircuitSizesAfterConnecting(1)).isEqualTo(2L);
    }

    @Test
    public void test_disjoint_set_redundant_union() {
        DisjointSet<String> ds = new DisjointSet<>();
        assertThat(ds.union("A", "B")).isTrue();
        assertThat(ds.union("A", "B")).isFalse();
        assertThat(ds.size("A")).isEqualTo(2);
    }

    @Test
    public void test_disjoint_set_path_compression() {
        DisjointSet<String> ds = new DisjointSet<>();
        ds.union("A", "B");
        ds.union("C", "D");
        ds.union("E", "F");

        ds.union("B", "D");
        ds.union("D", "F");

        assertThat(ds.find("A")).isEqualTo(ds.find("E"));
        assertThat(ds.size("A")).isEqualTo(6);
    }
}
