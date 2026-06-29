package software.ulpgc.aoc.day07.b;

import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day07.b.model.PathCounter;
import software.ulpgc.aoc.day07.model.Manifold;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day07/input.txt");

        List<String> lines = LoaderFactory
                .txt(file, line -> line)
                .load();

        PathCounter pathCounter = PathCounter.of(Manifold.from(lines));
        System.out.println("El resultado es: " + pathCounter.countPaths());
    }
}
