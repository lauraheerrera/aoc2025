package software.ulpgc.aoc.day08.b;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day08.io.JunctionBoxLoader;
import software.ulpgc.aoc.day08.io.TxtJunctionBoxDeserializer;
import software.ulpgc.aoc.day08.model.JunctionBox;
import software.ulpgc.aoc.day08.model.Playground;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day08/input.txt");
        Deserializer<JunctionBox> deserializer = new TxtJunctionBoxDeserializer();
        JunctionBoxLoader loader = () -> LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();
        Playground playground = new Playground(loader.load());
        System.out.println("El resultado es: " + playground.lastConnectionCoordinatesProduct());
    }
}
