package software.ulpgc.aoc.day12.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public record Shape(List<Point> points) {
    public int size() {
        return points.size();
    }

    public List<Shape> orientations() {
        List<Point> base = this.points;

        return Stream.of(base, flip(base))
                .flatMap(this::allRotations)
                .map(this::normalize)
                .distinct()
                .map(Shape::new)
                .toList();
    }

    private List<Point> flip(List<Point> p) {
        return p.stream()
                .map(pt -> new Point(-pt.x(), pt.y()))
                .toList();
    }

    private Stream<List<Point>> allRotations(List<Point> p) {
        return Stream.of(
                p,
                rot(p),
                rot(rot(p)),
                rot(rot(rot(p))));
    }

    private List<Point> rot(List<Point> p) {
        return p.stream()
                .map(pt -> new Point(-pt.y(), pt.x()))
                .toList();
    }

    private List<Point> normalize(List<Point> points) {
        int minX = points.stream().mapToInt(Point::x).min().orElse(0);
        int minY = points.stream().mapToInt(Point::y).min().orElse(0);

        return points.stream()
                .map(p -> new Point(p.x() - minX, p.y() - minY))
                .sorted(Comparator.comparingInt(Point::x)
                        .thenComparingInt(Point::y))
                .toList();
    }

}