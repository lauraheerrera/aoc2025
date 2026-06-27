package software.ulpgc.aoc.day12.model;

import java.util.List;
import java.util.stream.IntStream;

public record Region(int width, int height, List<Integer> presentQuantities) {
    public int area() {
        return width * height;
    }

    public boolean fits(List<Shape> shapes) {
        int requiredArea = IntStream.range(0, Math.min(shapes.size(), presentQuantities.size()))
                .map(i -> presentQuantities.get(i) * shapes.get(i).area())
                .sum();
        return requiredArea <= area();
    }

}
