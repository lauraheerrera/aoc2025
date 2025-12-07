package software.ulpgc.aoc.day02.b;

import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.common.io.TxtLoader;
import software.ulpgc.aoc.day02.io.RangeDeserializer;
import software.ulpgc.aoc.day02.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day02.model.GiftShop;
import software.ulpgc.aoc.day02.model.IdRange;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = new File("src/main/resources/day02/input.txt");
        RangeDeserializer<software.ulpgc.aoc.day02.b.model.Id> deserializer = new TxtRangeDeserializer<>();

        TxtLoader<String> loader = LoaderFactory.txt(file, line -> line);
        List<String> lines = loader.load();

        List<IdRange<software.ulpgc.aoc.day02.b.model.Id>> ranges =
                Arrays.stream(lines.getFirst().split(","))
                        .map(String::trim)
                        .map(line -> deserializer.deserialize(line, software.ulpgc.aoc.day02.b.model.Id::create))
                        .toList();

        GiftShop<software.ulpgc.aoc.day02.b.model.Id> giftShop = new GiftShop<>(ranges);
        System.out.println("Total ranges: " + ranges.size());
        System.out.println("Suma IDs invalidos: " + giftShop.sumAllInvalidIds());
    }
}
