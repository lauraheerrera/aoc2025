package software.ulpgc.aoc.day05.a;

import java.io.File;
import java.io.IOException;
import java.util.List;

import software.ulpgc.aoc.day05.io.TxtDatabaseLoader;
import software.ulpgc.aoc.day05.io.TxtIDDeserializer;
import software.ulpgc.aoc.day05.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day05.model.FreshnessValidator;
import software.ulpgc.aoc.day05.model.ID;
import software.ulpgc.aoc.day05.model.Range;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day05/input.txt");
        TxtRangeDeserializer rangeDeserializer = new TxtRangeDeserializer();
        TxtIDDeserializer idDeserializer = new TxtIDDeserializer();
        TxtDatabaseLoader loader = TxtDatabaseLoader
                .fromFile(file.getAbsolutePath(), rangeDeserializer, idDeserializer);
        List<Range> ranges = loader.loadRanges();
        List<ID> ids = loader.loadIds();

        System.out.println("Hay " + FreshnessValidator.fromRanges(ranges).countFresh(ids) + " IDs frescos");
    }
}
