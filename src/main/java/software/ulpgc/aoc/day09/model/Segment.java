package software.ulpgc.aoc.day09.model;

public record Segment(Point start, Point end) {
    public boolean isHorizontal() {
        return start.y() == end.y();
    }

    public boolean isVertical() {
        return start.x() == end.x();
    }

    public int minX() {
        return Math.min(start.x(), end.x());
    }

    public int maxX() {
        return Math.max(start.x(), end.x());
    }

    public int minY() {
        return Math.min(start.y(), end.y());
    }

    public int maxY() {
        return Math.max(start.y(), end.y());
    }
}
