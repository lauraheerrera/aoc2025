package software.ulpgc.aoc.day04.model;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.List;

public class Diagram {
    private final Tile[][] grid;
    private final int rows;
    private final int cols;

    private Diagram(Tile[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid.length > 0 ? grid[0].length : 0;
    }

    public static Diagram create(Tile[][] tiles) {
        return new Diagram(tiles == null || tiles.length == 0
                ? new Tile[0][0]
                : Stream.of(tiles).map(Tile[]::clone).toArray(Tile[][]::new));
    }

    public Tile get(Coordinate coordinate) {
        if (!isInBounds(coordinate))
            throw new IndexOutOfBoundsException();
        return grid[coordinate.row()][coordinate.col()];
    }

    public boolean isInBounds(Coordinate coordinate) {
        return coordinate.row() >= 0
                && coordinate.row() < rows
                && coordinate.col() >= 0
                && coordinate.col() < cols;
    }

    public Stream<Coordinate> coordinates() {
        return IntStream.range(0, rows)
                .boxed()
                .flatMap(r -> IntStream.range(0, cols).mapToObj(c -> new Coordinate(r, c)));
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public Diagram withClearedCoordinates(List<Coordinate> coordinates) {
        Tile[][] cloned = Stream.of(grid).map(Tile[]::clone).toArray(Tile[][]::new);
        coordinates.stream()
                .filter(this::isInBounds)
                .forEach(c -> cloned[c.row()][c.col()] = Tile.CLEARED);
        return new Diagram(cloned);
    }
}