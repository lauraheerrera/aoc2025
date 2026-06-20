package test.Day07.BTest;

import org.junit.Test;
import software.ulpgc.aoc.day07.io.TxtManifoldDeserializer;
import software.ulpgc.aoc.day07.model.*;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class ManifoldTest {
    private static final String example = """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............
            """;

    @Test
    public void solve_example() {
        Manifold manifold = new TxtManifoldDeserializer().deserialize(example);
        assertThat(manifold.countPaths()).isEqualTo(new BigInteger("40"));
    }

    @Test
    public void test_paths_initialization_and_access() {
        software.ulpgc.aoc.day07.model.Paths paths = software.ulpgc.aoc.day07.model.Paths.initial(3);

        assertThat(paths.get(new Column(0))).isEqualTo(BigInteger.ONE);
        assertThat(paths.get(new Column(1))).isEqualTo(BigInteger.ONE);
        assertThat(paths.get(new Column(2))).isEqualTo(BigInteger.ONE);

        assertThat(paths.get(new Column(-1))).isEqualTo(BigInteger.ONE);
        assertThat(paths.get(new Column(3))).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void test_paths_propagation_single_splitter() {
        software.ulpgc.aoc.day07.model.Paths paths = software.ulpgc.aoc.day07.model.Paths.initial(3);
        Row row = Row.from(".^.");
        software.ulpgc.aoc.day07.model.Paths nextPaths = software.ulpgc.aoc.day07.model.Paths.next(paths, row);

        assertThat(nextPaths.get(new Column(0))).isEqualTo(BigInteger.ONE);
        assertThat(nextPaths.get(new Column(1))).isEqualTo(new BigInteger("2"));
        assertThat(nextPaths.get(new Column(2))).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void test_manifold_paths_no_splitters() {
        Manifold manifold = new Manifold(List.of(
                "S..",
                "...",
                "..."));
        assertThat(manifold.countPaths()).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void test_manifold_paths_one_splitter() {
        Manifold manifold = new Manifold(List.of(
                ".S.",
                ".^.",
                "..."));
        assertThat(manifold.countPaths()).isEqualTo(new BigInteger("2"));
    }

    @Test
    public void test_manifold_paths_multiple_splitters() {
        Manifold manifold = new Manifold(List.of(
                "..S..",
                "..^..",
                ".^.^.",
                "....."));
        assertThat(manifold.countPaths()).isEqualTo(new BigInteger("4"));
    }
}
