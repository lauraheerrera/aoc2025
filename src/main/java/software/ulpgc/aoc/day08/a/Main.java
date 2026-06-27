package software.ulpgc.aoc.day08.a;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day08.io.TxtJunctionBoxDeserializer;
import software.ulpgc.aoc.day08.model.JunctionBox;
import software.ulpgc.aoc.day08.model.Playground;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day08/input.txt");
        Deserializer<JunctionBox> deserializer = new TxtJunctionBoxDeserializer();
        List<JunctionBox> junctionBoxes = LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();
        Playground playground = new Playground(junctionBoxes);
        System.out.println("El resultado es: " + playground.multiplyThreeLargestCircuitSizesAfterConnecting(1000));
    }
}
