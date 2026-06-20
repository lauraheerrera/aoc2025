package software.ulpgc.aoc.day07.b;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day07.io.ManifoldLoader;
import software.ulpgc.aoc.day07.io.TxtManifoldDeserializer;
import software.ulpgc.aoc.day07.io.TxtManifoldLoader;
import software.ulpgc.aoc.day07.model.Manifold;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day07/input.txt");
        Deserializer<Manifold> deserializer = new TxtManifoldDeserializer();
        ManifoldLoader loader = new TxtManifoldLoader(file, deserializer);

        Manifold manifold = loader.load();
        System.out.println("El resultado es: " + manifold.countPaths());
    }
}
