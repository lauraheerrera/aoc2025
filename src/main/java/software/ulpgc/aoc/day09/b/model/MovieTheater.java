package software.ulpgc.aoc.day09.b.model;

import java.util.List;

import software.ulpgc.aoc.day09.model.Tile;

public class MovieTheater implements software.ulpgc.aoc.day09.model.MovieTheaterInterface {

    private final List<Tile> redTiles;

    private MovieTheater(List<Tile> redTiles) {
        this.redTiles = redTiles;
    }

    public static MovieTheater from(List<Tile> redTiles) {
        return new MovieTheater(redTiles);
    }

    @Override
    public long maxRectangleArea() {
        return redTiles.stream()
                .flatMap(t1 -> redTiles.stream()
                        .map(t2 -> new Pair(t1, t2)))
                .filter(p -> isValidRectangle(p.tile1(), p.tile2()))
                .mapToLong(p -> p.tile1().rectangleAreaWith(p.tile2()))
                .max()
                .orElse(0);
    }

    private boolean isValidRectangle(Tile tile1, Tile tile2) {
        return !hasInternalCrossings(
                Math.min(tile1.x(), tile2.x()),
                Math.max(tile1.x(), tile2.x()),
                Math.min(tile1.y(), tile2.y()),
                Math.max(tile1.y(), tile2.y()));
    }

    private boolean hasInternalCrossings(int minX, int maxX, int minY, int maxY) {
        return redTiles.stream()
                .map(t -> new EdgePair(t, next(t)))
                .anyMatch(e -> crossesHorizontal(e.tile1(), e.tile2(), minX, maxX, minY, maxY) ||
                        crossesVertical(e.tile1(), e.tile2(), minX, maxX, minY, maxY));
    }

    private Tile next(Tile t) {
        int i = redTiles.indexOf(t);
        return redTiles.get((i + 1) % redTiles.size());
    }

    private boolean crossesHorizontal(Tile tile1, Tile tile2, int minX, int maxX, int minY, int maxY) {
        return tile1.y() == tile2.y()
                && tile1.y() > minY
                && tile1.y() < maxY
                && Math.max(Math.min(tile1.x(), tile2.x()), minX) < Math.min(Math.max(tile1.x(), tile2.x()), maxX);
    }

    private boolean crossesVertical(Tile tile1, Tile tile2, int minX, int maxX, int minY, int maxY) {
        return tile1.x() == tile2.x()
                && tile1.x() > minX
                && tile1.x() < maxX
                && Math.max(Math.min(tile1.y(), tile2.y()), minY) < Math.min(Math.max(tile1.y(), tile2.y()), maxY);
    }

    private record Pair(Tile tile1, Tile tile2) {
    }

    private record EdgePair(Tile tile1, Tile tile2) {
    }
}