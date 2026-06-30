package software.ulpgc.aoc.day06.a;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day06.io.RowOperationDeserializer;
import software.ulpgc.aoc.day06.io.WorksheetSplitter;
import software.ulpgc.aoc.day06.model.Operation;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
        public static void main(String[] args) throws IOException {

                File file = new File("src/main/resources/day06/input.txt");
                List<String> lines = LoaderFactory
                                .txt(file, line -> line)
                                .load();

                List<String> blocks = WorksheetSplitter.splitIntoBlocks(lines);

                Deserializer<Operation> deserializer = new RowOperationDeserializer();
                long total = blocks.stream()
                                .map(deserializer::deserialize)
                                .mapToLong(Operation::solve)
                                .sum();

                System.out.println("El resultado es: " + total);
        }
}