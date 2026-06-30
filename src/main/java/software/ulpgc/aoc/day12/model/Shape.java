package software.ulpgc.aoc.day12.model;

import java.util.List;

public record Shape(List<Point> points) {
    public int size() {
        return points.size();
    }
}