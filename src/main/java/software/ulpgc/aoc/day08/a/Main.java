package software.ulpgc.aoc.day08.a;

import software.ulpgc.aoc.day08.io.TxtJunctionBoxDeserializer;
import software.ulpgc.aoc.day08.io.TxtJunctionBoxLoader;
import software.ulpgc.aoc.day08.model.Playground;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day08/input.txt");
        TxtJunctionBoxLoader loader = new TxtJunctionBoxLoader(file, new TxtJunctionBoxDeserializer());
        Playground playground = new Playground(loader.load());
        System.out.println("El resultado es: " + playground.multiplyThreeLargestCircuitSizesAfterConnecting(1000));
    }
}
