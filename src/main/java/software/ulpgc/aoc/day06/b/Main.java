package software.ulpgc.aoc.day06.b;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.io.ProblemLoader;
import software.ulpgc.aoc.day06.io.TxtMathProblemDeserializer;
import software.ulpgc.aoc.day06.io.TxtMathWorksheetLoader;
import software.ulpgc.aoc.day06.model.Problem;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day06/input.txt");
        Deserializer<Problem> deserializer = new TxtMathProblemDeserializer(TxtMathProblemDeserializer.View.ColumnsR2L);
        ProblemLoader loader = new TxtMathWorksheetLoader(file, deserializer);

        List<Problem> problems = loader.load();

        long total = problems.stream()
                .mapToLong(Problem::solve)
                .sum();

        System.out.println("El resultado es: " + total);
    }
}
