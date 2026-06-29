package software.ulpgc.aoc.day06.b;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day06.io.TxtMathProblemDeserializer;
import software.ulpgc.aoc.day06.model.Operation;
import software.ulpgc.aoc.day06.model.Worksheet;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day06/input.txt");
        Deserializer<Operation> deserializer = new TxtMathProblemDeserializer(Worksheet.View.COLUMNS_R2L);

        List<String> lines = LoaderFactory
                .txt(file, line -> line)
                .load();

        List<Operation> problems = new Worksheet(lines).parse(deserializer);

        long total = problems.stream()
                .mapToLong(Operation::solve)
                .sum();

        System.out.println("El resultado es: " + total);
    }
}
