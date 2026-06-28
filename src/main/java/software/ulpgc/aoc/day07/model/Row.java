package software.ulpgc.aoc.day07.model;

import java.util.List;
import java.util.stream.IntStream;

public class Row {
    private final List<Tile> tiles;

    private Row(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public static Row from(String line) {
        return new Row(line.chars().mapToObj(c -> Tile.from((char) c)).toList());
    }

    public Tile tileAt(Column col) {
        return (col.index() >= 0 && col.index() < tiles.size()) ? tiles.get(col.index()) : Tile.EMPTY;
    }

    public boolean isSplitterAt(Column col) {
        return tileAt(col).isSplitter();
    }

    public int size() {
        return tiles.size();
    }

    public Column findStartColumn() {
        return indexes()
                .filter(i -> tiles.get(i).isStart())
                .mapToObj(Column::new)
                .findFirst()
                .orElse(new Column(-1));
    }

    private IntStream indexes() {
        return IntStream.range(0, tiles.size());
    }

}
