package software.ulpgc.aoc.day04.model;

import java.util.stream.Stream;

public record Diagram(Tile[][] initialGrid) {
    public Diagram {
        initialGrid = initialGrid == null || initialGrid.length == 0
                ? new Tile[0][0]
                : Stream.of(initialGrid).map(Tile[]::clone).toArray(Tile[][]::new);
    }

    public static Diagram create(Tile[][] tiles) {
        return new Diagram(tiles);
    }

    public int rows() {
        return initialGrid.length;
    }

    public int cols() {
        return initialGrid.length > 0 ? initialGrid[0].length : 0;
    }
}