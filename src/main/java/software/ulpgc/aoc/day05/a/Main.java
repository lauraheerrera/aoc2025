package software.ulpgc.aoc.day05.a;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day05.io.TxtIDDeserializer;
import software.ulpgc.aoc.day05.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day05.model.FreshnessValidator;
import software.ulpgc.aoc.day05.model.ID;
import software.ulpgc.aoc.day05.model.Range;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day05/input.txt");
        List<List<String>> sections = LoaderFactory.sections(file);

        Deserializer<Range> rangeDeserializer = new TxtRangeDeserializer();
        Deserializer<ID> idDeserializer = new TxtIDDeserializer();

        List<Range> ranges = LoaderFactory.load(sections.getFirst(), rangeDeserializer);
        List<ID> ids = LoaderFactory.load(sections.getLast(), idDeserializer);

        System.out.println("Hay " + FreshnessValidator.fromRanges(ranges).countFresh(ids) + " IDs frescos");
    }

}
