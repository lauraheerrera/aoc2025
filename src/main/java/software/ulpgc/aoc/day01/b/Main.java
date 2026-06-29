package software.ulpgc.aoc.day01.b;

import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day01.io.TxtOrderDeserializer;
import software.ulpgc.aoc.day01.model.Dial;
import software.ulpgc.aoc.day01.model.DialCalculator;
import software.ulpgc.aoc.day01.model.DialStatus;
import software.ulpgc.aoc.day01.model.Order;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = new File("src/main/resources/day01/input.txt");
        Deserializer<Order> deserializer = new TxtOrderDeserializer();
        List<Order> orders = LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();

        Dial dial = Dial.create();
        DialStatus status = DialStatus.initial(dial).execute(orders);

        DialCalculator calculator = DialCalculator.of(status);

        System.out.println("Veces que pasa por 0: " + calculator.countCrossingZero());
    }
}