package software.ulpgc.aoc.day12.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day12.model.Point;
import software.ulpgc.aoc.day12.model.Shape;

import java.util.*;
import java.util.stream.*;

public class TxtShapeDeserializer implements Deserializer<Shape> {

    @Override
    public Shape deserialize(String block) {

        String[] lines = block.split("\n");

        List<Point> points = IntStream.range(1, lines.length)
                .boxed()
                .flatMap(y -> rowPoints(lines[y], y - 1))
                .toList();

        return new Shape(points);
    }

    private Stream<Point> rowPoints(String row, int y) {
        return IntStream.range(0, row.length())
                .filter(x -> row.charAt(x) == '#')
                .mapToObj(x -> new Point(x, y));
    }
}