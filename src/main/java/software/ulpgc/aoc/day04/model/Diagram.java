package software.ulpgc.aoc.day04.model;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.List;

public class Diagram {
    private final Tile[][] grid;
    private final int rows;
    private final int cols;

    private enum Direction {
        NORTH_WEST(-1, -1), NORTH(-1, 0), NORTH_EAST(-1, 1),
        WEST(0, -1), EAST(0, 1),
        SOUTH_WEST(1, -1), SOUTH(1, 0), SOUTH_EAST(1, 1);

        final int rowOffset;
        final int colOffset;

        Direction(int rowOffset, int colOffset) {
            this.rowOffset = rowOffset;
            this.colOffset = colOffset;
        }
    }

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

    public List<Coordinate> coordinates() {
        return IntStream.range(0, rows)
                .boxed()
                .flatMap(r -> IntStream.range(0, cols).mapToObj(c -> new Coordinate(r, c)))
                .toList();
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

    public int countAdjacent(Coordinate coordinate, Tile target) {
        return (int) Arrays.stream(Direction.values())
                .filter(dir -> {
                    Coordinate neighbor = coordinate.offset(dir.rowOffset, dir.colOffset);
                    return isInBounds(neighbor) && get(neighbor) == target;
                })
                .count();
    }

}
