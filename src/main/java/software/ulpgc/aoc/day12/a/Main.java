package software.ulpgc.aoc.day12.a;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day12.io.TxtRegionDeserializer;
import software.ulpgc.aoc.day12.io.TxtShapeDeserializer;
import software.ulpgc.aoc.day12.model.Shape;
import software.ulpgc.aoc.day12.model.GreedyRegionFitter;
import software.ulpgc.aoc.day12.model.Farm;
import software.ulpgc.aoc.day12.model.Region;
import software.ulpgc.aoc.day12.model.RegionFitter;

import java.io.File;
import java.util.List;

public class Main {
        public static void main(String[] args) throws Exception {

                File file = new File("src/main/resources/day12/input.txt");

                List<List<String>> sections = LoaderFactory.sections(file);

                Deserializer<Shape> shapeDeserializer = new TxtShapeDeserializer();
                Deserializer<Region> regionDeserializer = new TxtRegionDeserializer();

                List<Shape> shapes = LoaderFactory.load(
                                sections.subList(0, sections.size() - 1)
                                                .stream()
                                                .map(block -> String.join("\n", block))
                                                .toList(),
                                shapeDeserializer);

                List<Region> regions = LoaderFactory.load(
                                sections.getLast(),
                                regionDeserializer);

                RegionFitter fitter = new GreedyRegionFitter();

                long result = new Farm(regions, fitter).count(shapes);

                System.out.println("Regiones que caben: " + result);
        }
}