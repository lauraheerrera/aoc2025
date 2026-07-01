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

    private final Deserializer<Operation> deserializer =
            new ColumnR2LOperationDeserializer();

    private List<Operation> toProblems(String input) {

        // Given raw worksheet input
        List<String> lines = input.lines().toList();

        // When splitting into logical blocks
        List<String> blocks = WorksheetSplitter.splitIntoBlocks(lines);

        // And deserializing each block into an operation
        return blocks.stream()
                .map(deserializer::deserialize)
                .toList();
    }

    @Test
    public void should_solve_full_worksheet_example() {

        // Given a full worksheet with multiple operations
        List<Operation> problems = toProblems(input);

        // Then all operations should be parsed correctly
        assertThat(problems).hasSize(4);

        // And each operation should produce correct results
        assertThat(problems.get(0).solve()).isEqualTo(8544L);
        assertThat(problems.get(1).solve()).isEqualTo(625L);
        assertThat(problems.get(2).solve()).isEqualTo(3253600L);
        assertThat(problems.get(3).solve()).isEqualTo(1058L);

        // And total aggregation should be correct
        long total = problems.stream()
                .mapToLong(Operation::solve)
                .sum();

        assertThat(total).isEqualTo(3263827L);
    }

    @Test
    public void should_solve_simple_addition_operation() {

        // Given a simple addition worksheet block
        String customInput = """
                12
                00
                +
                """;

        List<Operation> ops = toProblems(customInput);

        // Then result should match expected sum
        assertThat(ops.getFirst().solve()).isEqualTo(30L);
    }

    @Test
    public void should_solve_simple_multiplication_operation() {

        // Given a simple multiplication worksheet block
        String customInput = """
                12
                00
                *
                """;

        List<Operation> ops = toProblems(customInput);

        // Then result should match expected product
        assertThat(ops.getFirst().solve()).isEqualTo(200L);
    }
}