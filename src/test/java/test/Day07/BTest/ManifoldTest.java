package test.Day07.BTest;

import org.junit.Test;
import software.ulpgc.aoc.day07.io.TxtManifoldDeserializer;
import software.ulpgc.aoc.day07.b.model.PathCounter;

import software.ulpgc.aoc.day07.model.*;

import java.math.BigInteger;
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
    public void should_compute_paths_for_full_example() {

        // Given a parsed manifold
        Manifold manifold = new TxtManifoldDeserializer().deserialize(example);

        // When computing all paths (B version algorithm)
        BigInteger result = PathCounter.of(manifold).countPaths();

        // Then result should match expected value
        assertThat(result).isEqualTo(new BigInteger("40"));
    }

    @Test
    public void should_initialize_paths_correctly() {

        // Given a path grid of size 3
        software.ulpgc.aoc.day07.b.model.Paths paths =
                software.ulpgc.aoc.day07.b.model.Paths.initial(3);

        // Then all initial positions should have value 1
        assertThat(paths.get(new Column(0))).isEqualTo(BigInteger.ONE);
        assertThat(paths.get(new Column(1))).isEqualTo(BigInteger.ONE);
        assertThat(paths.get(new Column(2))).isEqualTo(BigInteger.ONE);

        // And out-of-bound access should still be safe
        assertThat(paths.get(new Column(-1))).isEqualTo(BigInteger.ONE);
        assertThat(paths.get(new Column(3))).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void should_propagate_paths_through_splitter() {

        // Given initial path state
        software.ulpgc.aoc.day07.b.model.Paths paths =
                software.ulpgc.aoc.day07.b.model.Paths.initial(3);

        // And a row with a splitter
        Row row = Row.from(".^.");

        // When propagating
        software.ulpgc.aoc.day07.b.model.Paths next =
                software.ulpgc.aoc.day07.b.model.Paths.next(paths, row);

        // Then splitter should increase path count
        assertThat(next.get(new Column(0))).isEqualTo(BigInteger.ONE);
        assertThat(next.get(new Column(1))).isEqualTo(new BigInteger("2"));
        assertThat(next.get(new Column(2))).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void should_count_paths_with_no_splitters() {

        // Given empty grid
        Manifold manifold = Manifold.from(List.of(
                "S..",
                "...",
                "..."
        ));

        // Then only one path exists
        assertThat(PathCounter.of(manifold).countPaths())
                .isEqualTo(BigInteger.ONE);
    }

    @Test
    public void should_count_paths_with_one_splitter() {

        // Given simple split scenario
        Manifold manifold = Manifold.from(List.of(
                ".S.",
                ".^.",
                "..."
        ));

        // Then two paths should exist
        assertThat(PathCounter.of(manifold).countPaths())
                .isEqualTo(new BigInteger("2"));
    }

    @Test
    public void should_count_paths_with_multiple_splitters() {

        // Given more complex grid
        Manifold manifold = Manifold.from(List.of(
                "..S..",
                "..^..",
                ".^.^.",
                "....."
        ));

        // Then path explosion should be computed correctly
        assertThat(PathCounter.of(manifold).countPaths())
                .isEqualTo(new BigInteger("4"));
    }
}