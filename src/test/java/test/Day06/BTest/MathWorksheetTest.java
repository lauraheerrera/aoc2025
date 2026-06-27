package test.Day06.BTest;

import org.junit.Test;
import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.io.TxtMathProblemDeserializer;
import software.ulpgc.aoc.day06.model.Problem;
import software.ulpgc.aoc.day06.model.Worksheet;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class MathWorksheetTest {

    private static final String input = """
            123 328  51 64\s
             45 64  387 23\s
              6 98  215 314
            *   +   *   +
            """;

    Deserializer<Problem> deserializer = new TxtMathProblemDeserializer(Worksheet.View.COLUMNS_R2L);
    List<Problem> problems = new Worksheet(input.lines().toList()).parse(deserializer);

    @Test
    public void solve_example_problems() {
        assertThat(problems).hasSize(4);
        assertThat(problems.get(0).solve()).isEqualTo(8544L);
        assertThat(problems.get(1).solve()).isEqualTo(625L);
        assertThat(problems.get(2).solve()).isEqualTo(3253600L);
        assertThat(problems.get(3).solve()).isEqualTo(1058L);

        long total = problems.stream().mapToLong(Problem::solve).sum();
        assertThat(total).isEqualTo(3263827L);
    }

    @Test
    public void solve_simple_addition() {
        String customInput = """
                12
                00
                +
                """;
        assertThat(toProblems(customInput).get(0).solve()).isEqualTo(30L);
    }

    @Test
    public void solve_simple_multiplication() {
        String customInput = """
                12
                00
                *
                """;
        assertThat(toProblems(customInput).get(0).solve()).isEqualTo(200L);
    }

    private List<Problem> toProblems(String input) {
        return new Worksheet(input.lines().toList()).parse(deserializer);
    }
}
