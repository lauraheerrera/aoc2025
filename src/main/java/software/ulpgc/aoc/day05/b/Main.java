package software.ulpgc.aoc.day05.b;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day05.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day05.model.FreshnessValidator;
import software.ulpgc.aoc.day05.model.Range;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day05/input.txt");
        List<List<String>> sections = LoaderFactory.sections(file);

        List<Range> ranges = load(sections.getFirst(), new TxtRangeDeserializer());

        System.out.println("Hay un total de " + FreshnessValidator.fromRanges(ranges).countTotalFresh() + " IDs frescos");
    }

    private static <T> List<T> load(List<String> lines, Deserializer<T> deserializer) {
        return lines.stream().map(deserializer::deserialize).toList();
    }
}
