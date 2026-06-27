package software.ulpgc.aoc.day12.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day12.model.Shape;

import java.util.List;

public class TxtShapeDeserializer implements Deserializer<Shape> {

    @Override
    public Shape deserialize(String block) {
        String[] lines = block.split("\n");
        int index = Integer.parseInt(lines[0].replace(":", "").trim());
        int area = (int) java.util.Arrays.stream(lines, 1, lines.length)
                .flatMapToInt(String::chars)
                .filter(c -> c == '#')
                .count();
        return new Shape(index, area);
    }

    public static List<String> toBlocks(List<List<String>> sections) {
        return sections.subList(0, sections.size() - 1).stream()
                .map(lines -> String.join("\n", lines))
                .toList();
    }

}
