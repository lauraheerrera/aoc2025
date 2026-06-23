package test.Day06.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day06.io.TxtMathProblemDeserializer;
import software.ulpgc.aoc.day06.io.TxtMathWorksheetLoader;
import software.ulpgc.aoc.day06.model.Operator;
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

    TxtMathWorksheetLoader loader = new TxtMathWorksheetLoader(
            input,
            new TxtMathProblemDeserializer(Worksheet.View.ROWS));

    List<Problem> problems = loader.load();

    @Test
    public void solve_example_problems() {
        assertThat(problems).hasSize(4);
        assertThat(problems.get(0).solve()).isEqualTo(33210L);
        assertThat(problems.get(1).solve()).isEqualTo(490L);
        assertThat(problems.get(2).solve()).isEqualTo(4243455L);
        assertThat(problems.get(3).solve()).isEqualTo(401L);

        long total = problems.stream().mapToLong(Problem::solve).sum();
        assertThat(total).isEqualTo(4277556L);
    }

    @Test
    public void solve_simple_addition() {
        String customInput = """
                10
                20
                +
                """;
        assertThat(toProblems(customInput).get(0).solve()).isEqualTo(30L);
    }

    @Test
    public void solve_simple_multiplication() {
        String customInput = """
                10
                20
                *
                """;
        assertThat(toProblems(customInput).get(0).solve()).isEqualTo(200L);
    }

    @Test
    public void solve_empty_problem_should_return_zero() {
        Problem empty = new Problem(List.of(), Operator.ADD);
        assertThat(empty.solve()).isEqualTo(0L);
    }

    private List<Problem> toProblems(String input) {
        return new TxtMathWorksheetLoader(
                input,
                new TxtMathProblemDeserializer(Worksheet.View.ROWS)).load();
    }
}
