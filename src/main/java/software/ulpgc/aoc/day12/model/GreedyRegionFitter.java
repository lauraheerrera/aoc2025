package software.ulpgc.aoc.day12.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GreedyRegionFitter implements RegionFitter {

    @Override
    public boolean canFit(Region region, List<Shape> shapes) {

        List<Shape> needed = expand(region, shapes);

        int totalArea = needed.stream()
                .mapToInt(Shape::size)
                .sum();

        if (totalArea > region.area())
            return false;

        boolean[][] board = new boolean[region.height()][region.width()];

        needed = needed.stream()
                .sorted(Comparator.comparingInt(Shape::size).reversed())
                .toList();

        return needed.stream()
                .allMatch(shape -> placeFirstFit(board, shape, region));
    }

    private boolean placeFirstFit(boolean[][] board, Shape shape, Region region) {

        return shape.orientations().stream()
                .anyMatch(orientation -> positions(region).anyMatch(pos -> {

                    if (!canPlace(board, orientation, pos.x(), pos.y()))
                        return false;

                    place(board, orientation, pos.x(), pos.y());
                    return true;
                }));
    }

    private Stream<Point> positions(Region region) {
        return IntStream.range(0, region.width())
                .boxed()
                .flatMap(x -> IntStream.range(0, region.height())
                        .mapToObj(y -> new Point(x, y)));
    }

    private List<Shape> expand(Region region, List<Shape> shapes) {
        List<Integer> q = region.quantities();

        return IntStream.range(0, q.size())
                .boxed()
                .flatMap(i -> IntStream.range(0, q.get(i))
                        .mapToObj(j -> shapes.get(i)))
                .toList();
    }

    private boolean canPlace(boolean[][] board, Shape shape, int x, int y) {
        return shape.points().stream()
                .allMatch(p -> {
                    int nx = x + p.x();
                    int ny = y + p.y();

                    return nx >= 0 &&
                            ny >= 0 &&
                            ny < board.length &&
                            nx < board[0].length &&
                            !board[ny][nx];
                });
    }

    private void place(boolean[][] board, Shape shape, int x, int y) {
        shape.points().forEach(p -> board[y + p.y()][x + p.x()] = true);
    }
}