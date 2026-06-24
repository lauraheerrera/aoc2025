package software.ulpgc.aoc.day02.a;

import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day02.io.RangeLoader;
import software.ulpgc.aoc.day02.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day02.a.model.Id;
import software.ulpgc.aoc.day02.model.GiftShop;
import software.ulpgc.aoc.day02.model.IdRange;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = new File("src/main/resources/day02/input.txt");
        Deserializer<IdRange<Id>> deserializer = new TxtRangeDeserializer<>(Id::create);

        List<String> lines = LoaderFactory.txt(file, line -> line).load();

        RangeLoader<Id> loader = () -> Arrays.stream(lines.getFirst().split(","))
                .map(String::trim)
                .map(deserializer::deserialize)
                .toList();

        List<IdRange<Id>> ranges = loader.load();

        GiftShop<Id> giftShop = new GiftShop<>(ranges);
        System.out.println("Suma IDs invalidos: " + giftShop.sumAllInvalidIds());
    }
}
