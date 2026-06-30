package software.ulpgc.aoc.day11.b;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day11.io.TxtDeviceDeserializer;
import software.ulpgc.aoc.day11.model.Device;
import software.ulpgc.aoc.day11.model.Network;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day11/input.txt");
        Deserializer<Device> deserializer = new TxtDeviceDeserializer();
        List<Device> devices = LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();
        System.out.println("El resultado es: " + Network.from(devices).countPaths("you", "out"));
    }
}
