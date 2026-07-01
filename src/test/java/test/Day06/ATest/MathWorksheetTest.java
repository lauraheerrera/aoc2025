package test.Day06.ATest;

import org.junit.Test;
import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.io.RowOperationDeserializer;
import software.ulpgc.aoc.day06.io.WorksheetSplitter;
import software.ulpgc.aoc.day06.model.Operation;
import software.ulpgc.aoc.day06.model.Operator;

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
            new RowOperationDeserializer();

    private List<Operation> toProblems(String input) {

        // Given raw worksheet text
        List<String> lines = input.lines().toList();

        // When splitting into logical blocks
        List<String> blocks = WorksheetSplitter.splitIntoBlocks(lines);

        // And parsing each block into an operation
        return blocks.stream()
                .map(deserializer::deserialize)
                .toList();
    }

    @Test
    public void should_solve_full_worksheet_example() {

        // Given a full worksheet input
        List<Operation> problems = toProblems(input);

        // Then all operations should be parsed correctly
        assertThat(problems).hasSize(4);

        // And each operation should produce expected results
        assertThat(problems.get(0).solve()).isEqualTo(33210L);
        assertThat(problems.get(1).solve()).isEqualTo(490L);
        assertThat(problems.get(2).solve()).isEqualTo(4243455L);
        assertThat(problems.get(3).solve()).isEqualTo(401L);

        // And aggregated result should match expected total
        long total = problems.stream()
                .mapToLong(Operation::solve)
                .sum();

        assertThat(total).isEqualTo(4277556L);
    }

    @Test
    public void should_solve_simple_addition() {

        // Given simple addition input
        String customInput = """
                10
                20
                +
                """;

        List<Operation> ops = toProblems(customInput);

        // Then result should be sum
        assertThat(ops.getFirst().solve()).isEqualTo(30L);
    }

    @Test
    public void should_solve_simple_multiplication() {

        // Given simple multiplication input
        String customInput = """
                10
                20
                *
                """;

        List<Operation> ops = toProblems(customInput);

        // Then result should be product
        assertThat(ops.get(0).solve()).isEqualTo(200L);
    }

    @Test
    public void should_solve_empty_operation_as_zero() {

        // Given an empty operation
        Operation empty = new Operation(List.of(), Operator.ADD);

        // Then solving should return zero
        assertThat(empty.solve()).isEqualTo(0L);
    }
}