package software.ulpgc.aoc.day07.a;

import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day07.a.model.SplitterCounter;
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

        SplitterCounter splitterCounter = SplitterCounter.of(Manifold.from(lines));
        System.out.println("El resultado es: " + splitterCounter.countSplits());
    }
}
