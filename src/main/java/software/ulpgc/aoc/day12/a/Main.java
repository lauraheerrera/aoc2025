package software.ulpgc.aoc.day12.a;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day12.io.TxtRegionDeserializer;
import software.ulpgc.aoc.day12.io.TxtShapeDeserializer;
import software.ulpgc.aoc.day12.model.Farm;
import software.ulpgc.aoc.day12.model.Shape;
import software.ulpgc.aoc.day12.model.Region;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day12/input.txt");
        List<List<String>> sections = LoaderFactory.sections(file);

        List<Shape> shapes = load(TxtShapeDeserializer.toBlocks(sections), new TxtShapeDeserializer());
        List<Region> regions = load(sections.getLast(), new TxtRegionDeserializer());

        System.out.println("Regiones que caben: " + Farm.of(regions).countRegionsThatFit(shapes));
    }

    private static <T> List<T> load(List<String> lines, Deserializer<T> deserializer) {
        return lines.stream().map(deserializer::deserialize).toList();
    }
}
