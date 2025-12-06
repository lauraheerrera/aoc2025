package software.ulpgc.aoc.day02.b;

import software.ulpgc.aoc.day02.io.RangeDeserializer;
import software.ulpgc.aoc.day02.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day02.io.TxtRangeLoader;
import software.ulpgc.aoc.day02.model.GiftShop;
import software.ulpgc.aoc.day02.model.IdRange;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = new File("src/main/resources/day02/input.txt");
        RangeDeserializer<software.ulpgc.aoc.day02.b.model.Id> deserializer = new TxtRangeDeserializer<>();
        TxtRangeLoader<software.ulpgc.aoc.day02.b.model.Id> loader =
                new TxtRangeLoader<>(file, deserializer, software.ulpgc.aoc.day02.b.model.Id::create);

        List<IdRange<software.ulpgc.aoc.day02.b.model.Id>> ranges = loader.load();

        GiftShop<software.ulpgc.aoc.day02.b.model.Id> giftShop = new GiftShop<>(ranges);
        System.out.println("Total ranges: " + ranges.size());
        System.out.println("Suma IDs invalidos: " + giftShop.sumAllInvalidIds());
    }
}
