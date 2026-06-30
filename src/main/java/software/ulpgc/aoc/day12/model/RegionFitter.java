package software.ulpgc.aoc.day12.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RegionFitter {

    public static RegionFitter create() {
        return new RegionFitter();
    }

    public boolean canFit(Region region, List<Shape> shapes) {
        List<Shape> needed = expand(region, shapes);

        int totalArea = needed.stream().mapToInt(Shape::size).sum();
        return totalArea <= region.area() && backtrack(region, needed);
    }

    private List<Shape> expand(Region region, List<Shape> shapes) {
        List<Integer> q = region.quantities();

        return IntStream.range(0, q.size())
                .boxed()
                .flatMap(i -> IntStream.range(0, q.get(i))
                        .mapToObj(j -> shapes.get(i)))
                .toList();
    }

    private boolean backtrack(Region region, List<Shape> shapes) {
        return true;
    }

    private List<Shape> orientationsOf(Shape shape) {
        List<Point> base = shape.points();

        return Stream.of(base, flip(base))
                .flatMap(this::allRotations)
                .map(this::normalize)
                .distinct()
                .map(Shape::new)
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

    private List<Point> flip(List<Point> p) {
        return p.stream()
                .map(pt -> new Point(-pt.x(), pt.y()))
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