package test.Day07.ATest;

import org.junit.Test;

import software.ulpgc.aoc.day07.a.model.SplitterCounter;
import software.ulpgc.aoc.day07.b.model.PathCounter;
import software.ulpgc.aoc.day07.io.TxtManifoldDeserializer;
import software.ulpgc.aoc.day07.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    public void should_solve_full_manifold_example() {

        // Given a manifold parsed from text input
        Manifold manifold = new TxtManifoldDeserializer().deserialize(example);

        // When counting split points (A version of algorithm)
        long splits = SplitterCounter.of(manifold).countSplits();

        // And counting paths (B version of algorithm)
        java.math.BigInteger paths =
                PathCounter.of(manifold).countPaths();

        // Then both metrics should match expected results
        assertThat(splits).isEqualTo(21L);
        assertThat(paths).isEqualTo(new java.math.BigInteger("40"));
    }

    @Test
    public void should_handle_column_navigation() {

        // Given a column index
        Column col = new Column(5);

        // Then navigation should behave correctly
        assertThat(col.index()).isEqualTo(5);
        assertThat(col.left()).isEqualTo(new Column(4));
        assertThat(col.right()).isEqualTo(new Column(6));
    }

    @Test
    public void should_map_tiles_correctly() {

        // Given tile characters

        assertThat(Tile.from('.')).isEqualTo(Tile.EMPTY);
        assertThat(Tile.from('^')).isEqualTo(Tile.SPLITTER);
        assertThat(Tile.from('S')).isEqualTo(Tile.START);

        // Then classification rules should hold
        assertThat(Tile.EMPTY.isSplitter()).isFalse();
        assertThat(Tile.EMPTY.isStart()).isFalse();

        assertThat(Tile.SPLITTER.isSplitter()).isTrue();
        assertThat(Tile.SPLITTER.isStart()).isFalse();

        assertThat(Tile.START.isSplitter()).isFalse();
        assertThat(Tile.START.isStart()).isTrue();

        // And invalid input should fail
        assertThatThrownBy(() -> Tile.from('X'))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown tile: X");
    }

    @Test
    public void should_handle_row_behavior() {

        // Given a row with mixed tiles
        Row row = Row.from("..S^..");

        // Then size should match
        assertThat(row.size()).isEqualTo(6);

        // And tile lookup should work
        assertThat(row.tileAt(new Column(0))).isEqualTo(Tile.EMPTY);
        assertThat(row.tileAt(new Column(2))).isEqualTo(Tile.START);
        assertThat(row.tileAt(new Column(3))).isEqualTo(Tile.SPLITTER);

        // And out-of-bounds should be safe
        assertThat(row.tileAt(new Column(-1))).isEqualTo(Tile.EMPTY);
        assertThat(row.tileAt(new Column(6))).isEqualTo(Tile.EMPTY);

        // And splitter detection should work
        assertThat(row.isSplitterAt(new Column(3))).isTrue();
        assertThat(row.isSplitterAt(new Column(2))).isFalse();
        assertThat(row.isSplitterAt(new Column(-1))).isFalse();

        // And start detection should work
        assertThat(row.findStartColumn()).isEqualTo(new Column(2));

        // And missing start should return sentinel
        Row rowNoStart = Row.from("......");
        assertThat(rowNoStart.findStartColumn()).isEqualTo(new Column(-1));
    }

    @Test
    public void should_build_manifold_grid_correctly() {

        // Given a small grid
        Manifold manifold = Manifold.from(List.of(
                "S..",
                ".^."
        ));

        // Then dimensions should match
        assertThat(manifold.size()).isEqualTo(2);

        // And tile access should work
        assertThat(manifold.getRow(0).tileAt(new Column(0)))
                .isEqualTo(Tile.START);

        assertThat(manifold.getRow(1).tileAt(new Column(1)))
                .isEqualTo(Tile.SPLITTER);
    }

    @Test
    public void should_count_zero_splitters() {

        // Given a grid with no splitters
        Manifold manifold = Manifold.from(List.of(
                "S..",
                "...",
                "..."
        ));

        // Then split count should be zero
        assertThat(SplitterCounter.of(manifold).countSplits()).isEqualTo(0L);
    }

    @Test
    public void should_count_single_splitter() {

        // Given a grid with one splitter
        Manifold manifold = Manifold.from(List.of(
                ".S.",
                ".^.",
                "..."
        ));

        // Then split count should be one
        assertThat(SplitterCounter.of(manifold).countSplits()).isEqualTo(1L);
    }

    @Test
    public void should_count_multiple_splitters() {

        // Given a more complex grid
        Manifold manifold = Manifold.from(List.of(
                "..S..",
                "..^..",
                ".^.^.",
                "....."
        ));

        // Then split count should match expected value
        assertThat(SplitterCounter.of(manifold).countSplits()).isEqualTo(3L);
    }
}