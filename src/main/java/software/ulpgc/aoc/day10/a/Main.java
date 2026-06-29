package software.ulpgc.aoc.day10.a;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day10.a.io.TxtMachineDeserializer;
import software.ulpgc.aoc.day10.model.Factory;
import software.ulpgc.aoc.day10.a.model.Machine;
import software.ulpgc.aoc.day10.a.model.Solver;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day10/input.txt");
        Deserializer<Machine> deserializer = new TxtMachineDeserializer();
        List<Machine> machines = LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();
        Factory<Machine> factory = new Factory<>(new Solver());
        System.out.println("El resultado es: " + factory.totalMinPresses(machines));
    }
}
