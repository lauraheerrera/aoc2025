package software.ulpgc.aoc.day01.a;

import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day01.io.OrderDeserializer;
import software.ulpgc.aoc.day01.io.OrderLoader;
import software.ulpgc.aoc.day01.io.TxtOrderDeserializer;
import software.ulpgc.aoc.day01.model.Dial;
import software.ulpgc.aoc.day01.model.Order;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = new File("src/main/resources/day01/input.txt");
        OrderDeserializer deserializer = new TxtOrderDeserializer();
        OrderLoader loader = () -> LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();
        List<Order> orders = loader.load();

        Dial dial = Dial.create().execute(orders);

        System.out.println("Posición final: " + dial.position());
        System.out.println("Veces que pasa por 0: " + dial.count());
    }
}
