package software.ulpgc.aoc.day12.model;

import java.util.List;

public record Region(int width, int height, List<Integer> quantities) {
    public int area() {
        return width * height;
    }
}