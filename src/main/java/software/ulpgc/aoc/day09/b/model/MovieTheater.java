package software.ulpgc.aoc.day09.b.model;

import software.ulpgc.aoc.day09.model.Tile;

import java.util.List;
import java.util.stream.IntStream;

public record MovieTheater(List<Tile> redTiles) {
    public long maxRectangleArea() {
        return maxRectangleArea(IntStream.range(0, redTiles.size())
                .mapToObj(i -> new Segment(redTiles.get(i), redTiles.get((i + 1) % redTiles.size())))
                .toList());
    }

    private long maxRectangleArea(List<Segment> segments) {
        return IntStream.range(0, redTiles.size())
                .boxed()
                .flatMapToLong(i -> IntStream.range(i + 1, redTiles.size())
                        .filter(j -> isValidRectangle(redTiles.get(i), redTiles.get(j), segments))
                        .mapToLong(j -> redTiles.get(i).rectangleAreaWith(redTiles.get(j))))
                .max()
                .orElse(0L);
    }

    private boolean isValidRectangle(Tile p1, Tile p2, List<Segment> segments) {
        return isValidRectangle(new Segment(p1, p2), segments);
    }

    private boolean isValidRectangle(Segment r, List<Segment> segments) {
        return segments.stream().noneMatch(s -> s.isHorizontal()
                ? (s.minY() > r.minY() && s.minY() < r.maxY() && s.minX() < r.maxX() && s.maxX() > r.minX())
                : (s.minX() > r.minX() && s.minX() < r.maxX() && s.minY() < r.maxY() && s.maxY() > r.minY()));
    }

}
