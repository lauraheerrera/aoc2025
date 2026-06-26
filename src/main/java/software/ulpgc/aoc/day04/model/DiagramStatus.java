package software.ulpgc.aoc.day04.model;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record DiagramStatus(Instant ts, Diagram diagram, Tile[][] grid) {
    public DiagramStatus {
        grid = grid == null || grid.length == 0
                ? new Tile[0][0]
                : Stream.of(grid).map(Tile[]::clone).toArray(Tile[][]::new);
    }

    public static DiagramStatus initial(Diagram diagram) {
        return new DiagramStatus(Instant.now(), diagram, diagram.initialGrid());
    }

    public Tile get(Coordinate coordinate) {
        if (!isInBounds(coordinate))
            throw new IndexOutOfBoundsException();
        return grid[coordinate.row()][coordinate.col()];
    }

    public boolean isInBounds(Coordinate coordinate) {
        return coordinate.row() >= 0
                && coordinate.row() < diagram.rows()
                && coordinate.col() >= 0
                && coordinate.col() < diagram.cols();
    }

    public Stream<Coordinate> coordinates() {
        return IntStream.range(0, diagram.rows())
                .boxed()
                .flatMap(r -> IntStream.range(0, diagram.cols()).mapToObj(c -> new Coordinate(r, c)));
    }

    public DiagramStatus withClearedCoordinates(List<Coordinate> coordinates) {
        Tile[][] cloned = Stream.of(grid).map(Tile[]::clone).toArray(Tile[][]::new);
        coordinates.stream()
                .filter(this::isInBounds)
                .forEach(c -> cloned[c.row()][c.col()] = Tile.CLEARED);
        return new DiagramStatus(Instant.now(), diagram, cloned);
    }
}
