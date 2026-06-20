package test.Day07.ATest;

import org.junit.Test;
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
    public void solve_example() {
        Manifold manifold = new TxtManifoldDeserializer().deserialize(example);
        assertThat(manifold.countSplits()).isEqualTo(21L);
    }

    @Test
    public void test_column_behavior() {
        Column col = new Column(5);
        assertThat(col.index()).isEqualTo(5);
        assertThat(col.left()).isEqualTo(new Column(4));
        assertThat(col.right()).isEqualTo(new Column(6));
    }

    @Test
    public void test_tile_enum() {
        assertThat(Tile.from('.')).isEqualTo(Tile.EMPTY);
        assertThat(Tile.from('^')).isEqualTo(Tile.SPLITTER);
        assertThat(Tile.from('S')).isEqualTo(Tile.START);

        assertThat(Tile.EMPTY.isSplitter()).isFalse();
        assertThat(Tile.EMPTY.isStart()).isFalse();

        assertThat(Tile.SPLITTER.isSplitter()).isTrue();
        assertThat(Tile.SPLITTER.isStart()).isFalse();

        assertThat(Tile.START.isSplitter()).isFalse();
        assertThat(Tile.START.isStart()).isTrue();

        assertThatThrownBy(() -> Tile.from('X'))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown tile: X");
    }

    @Test
    public void test_row_behavior() {
        Row row = Row.from("..S^..");
        assertThat(row.size()).isEqualTo(6);

        assertThat(row.tileAt(new Column(0))).isEqualTo(Tile.EMPTY);
        assertThat(row.tileAt(new Column(2))).isEqualTo(Tile.START);
        assertThat(row.tileAt(new Column(3))).isEqualTo(Tile.SPLITTER);

        assertThat(row.tileAt(new Column(-1))).isEqualTo(Tile.EMPTY);
        assertThat(row.tileAt(new Column(6))).isEqualTo(Tile.EMPTY);

        assertThat(row.isSplitterAt(new Column(3))).isTrue();
        assertThat(row.isSplitterAt(new Column(2))).isFalse();
        assertThat(row.isSplitterAt(new Column(-1))).isFalse();

        assertThat(row.findStartColumn()).isEqualTo(new Column(2));

        Row rowNoStart = Row.from("......");
        assertThat(rowNoStart.findStartColumn()).isEqualTo(new Column(-1));
    }

    @Test
    public void test_grid_behavior() {
        Grid grid = Grid.from(List.of(
                "S..",
                ".^."));
        assertThat(grid.size()).isEqualTo(2);
        assertThat(grid.getRow(0).tileAt(new Column(0))).isEqualTo(Tile.START);
        assertThat(grid.getRow(1).tileAt(new Column(1))).isEqualTo(Tile.SPLITTER);
    }

    @Test
    public void test_manifold_with_no_splitters() {
        Manifold manifold = new Manifold(List.of(
                "S..",
                "...",
                "..."));
        assertThat(manifold.countSplits()).isEqualTo(0L);
    }

    @Test
    public void test_manifold_with_one_splitter() {
        Manifold manifold = new Manifold(List.of(
                ".S.",
                ".^.",
                "..."));
        assertThat(manifold.countSplits()).isEqualTo(1L);
    }

    @Test
    public void test_manifold_with_multiple_splits() {
        Manifold manifold = new Manifold(List.of(
                "..S..",
                "..^..",
                ".^.^.",
                "....."));
        assertThat(manifold.countSplits()).isEqualTo(3L);
    }
}
