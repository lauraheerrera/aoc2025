package software.ulpgc.aoc.day10.a;

import software.ulpgc.aoc.day10.io.TxtMachineDeserializer;
import software.ulpgc.aoc.day10.io.TxtMachineLoader;
import software.ulpgc.aoc.day10.model.Factory;
import software.ulpgc.aoc.day10.model.Machine;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day10/input.txt");
        TxtMachineLoader loader = new TxtMachineLoader(file, new TxtMachineDeserializer());
        List<Machine> machines = loader.load();
        Factory factory = new Factory(machines);
        System.out.println("El resultado es: " + factory.totalMinPresses());
    }
}
