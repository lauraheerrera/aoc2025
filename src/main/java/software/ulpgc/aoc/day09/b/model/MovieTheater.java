package software.ulpgc.aoc.day09.b.model;

import software.ulpgc.aoc.day09.model.Segment;
import software.ulpgc.aoc.day09.model.Point;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record MovieTheater(List<Point> redTiles) {
    public long maxRectangleArea() {
        return maxRectangleAreaWithSegments(segments(redTiles));
    }

    private long maxRectangleAreaWithSegments(List<Segment> segments) {
        return IntStream.range(0, redTiles.size())
                .boxed()
                .flatMapToLong(i -> IntStream.range(i + 1, redTiles.size())
                        .filter(j -> isValidRectangle(redTiles.get(i), redTiles.get(j), segments))
                        .mapToLong(j -> redTiles.get(i).rectangleAreaWith(redTiles.get(j))))
                .max()
                .orElse(0L);
    }

    private List<Segment> segments(List<Point> tiles) {
        return IntStream.range(0, tiles.size())
                .mapToObj(i -> new Segment(tiles.get(i), tiles.get((i + 1) % tiles.size())))
                .collect(Collectors.toList());
    }

    private boolean isValidRectangle(Point p1, Point p2, List<Segment> segments) {
        return noSegmentIntersectsInterior(segments,
                Math.min(p1.x(), p2.x()), Math.max(p1.x(), p2.x()),
                Math.min(p1.y(), p2.y()), Math.max(p1.y(), p2.y()))
                && isInsidePolygon(p1, p2, segments);
    }

    private boolean noSegmentIntersectsInterior(List<Segment> segments, int xMin, int xMax, int yMin, int yMax) {
        return segments.stream().noneMatch(s -> intersectsInterior(s, xMin, xMax, yMin, yMax));
    }

    private boolean intersectsInterior(Segment s, int xMin, int xMax, int yMin, int yMax) {
        return s.isHorizontal()
                ? (s.minY() > yMin && s.minY() < yMax && s.minX() < xMax && s.maxX() > xMin)
                : (s.minX() > xMin && s.minX() < xMax && s.minY() < yMax && s.maxY() > yMin);
    }

    private boolean isInsidePolygon(Point p1, Point p2, List<Segment> segments) {
        return isValidPoint(p1.x(), p2.y(), segments)
                && isValidPoint(p2.x(), p1.y(), segments);
    }

    private boolean isValidPoint(int x, int y, List<Segment> segments) {
        return isOnBoundary(x, y, segments) || isPointInside(x + 0.1, y + 0.1, segments);
    }

    private boolean isOnBoundary(int x, int y, List<Segment> segments) {
        return segments.stream().anyMatch(s -> s.isHorizontal()
                ? (y == s.minY() && x >= s.minX() && x <= s.maxX())
                : (x == s.minX() && y >= s.minY() && y <= s.maxY()));
    }

    private boolean isPointInside(double x, double y, List<Segment> segments) {
        return segments.stream()
                .filter(Segment::isVertical)
                .filter(s -> s.minX() > x)
                .filter(s -> s.minY() < y && y < s.maxY())
                .count() % 2 != 0;
    }
}
