package software.ulpgc.aoc.day09.model;

public record Point(int x, int y) {
    public long rectangleAreaWith(Point other) {
        return (long) (Math.abs(x - other.x) + 1) * (Math.abs(y - other.y) + 1);
    }
}
