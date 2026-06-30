package test.Day06.BTest;

import org.junit.Test;
import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.io.ColumnR2LOperationDeserializer;
import software.ulpgc.aoc.day06.io.WorksheetSplitter;
import software.ulpgc.aoc.day06.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MathWorksheetTest {

    private static final String input = """
            123 328  51 64
             45 64  387 23
              6 98  215 314
            *   +   *   +
            """;

    private final Deserializer<Operation> deserializer = new ColumnR2LOperationDeserializer();

    private List<Operation> toProblems(String input) {
        List<String> lines = input.lines().toList();
        List<String> blocks = WorksheetSplitter.splitIntoBlocks(lines);

        return blocks.stream()
                .map(deserializer::deserialize)
                .toList();
    }

    @Test
    public void solve_example_problems() {

        List<Operation> problems = toProblems(input);

        assertThat(problems).hasSize(4);

        assertThat(problems.get(0).solve()).isEqualTo(8544L);
        assertThat(problems.get(1).solve()).isEqualTo(625L);
        assertThat(problems.get(2).solve()).isEqualTo(3253600L);
        assertThat(problems.get(3).solve()).isEqualTo(1058L);

        long total = problems.stream()
                .mapToLong(Operation::solve)
                .sum();

        assertThat(total).isEqualTo(3263827L);
    }

    @Test
    public void solve_simple_addition() {
        String customInput = """
                12
                00
                +
                """;
        List<Operation> ops = toProblems(customInput);
        assertThat(ops.get(0).solve()).isEqualTo(30L);
    }

    @Test
    public void solve_simple_multiplication() {
        String customInput = """
                12
                00
                *
                """;
        List<Operation> ops = toProblems(customInput);
        assertThat(ops.get(0).solve()).isEqualTo(200L);
    }
}