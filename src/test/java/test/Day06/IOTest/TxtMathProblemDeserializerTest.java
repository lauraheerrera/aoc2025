package test.Day06.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day06.io.TxtMathProblemDeserializer;
import software.ulpgc.aoc.day06.model.Operation;
import software.ulpgc.aoc.day06.model.ProblemBlock;
import software.ulpgc.aoc.day06.model.Worksheet;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtMathProblemDeserializerTest {

    @Test
    public void should_deserialize_row_problem_correctly() {
        String input = """
                10 20
                +
                """;
        ProblemBlock block = new ProblemBlock(List.of(input.split("\n")));
        TxtMathProblemDeserializer deserializer = new TxtMathProblemDeserializer(Worksheet.View.ROWS);
        Operation problem = deserializer.deserialize(block.text());

        assertThat(problem).isNotNull();
        assertThat(problem.operands()).extracting("value").containsExactly(10L, 20L);
        assertThat(problem.operator().symbol()).isEqualTo('+');
    }

    @Test
    public void should_deserialize_column_problem_correctly() {
        String input = """
                11
                00
                +
                """;
        ProblemBlock block = new ProblemBlock(List.of(input.split("\n")));
        TxtMathProblemDeserializer deserializer = new TxtMathProblemDeserializer(Worksheet.View.COLUMNS_R2L);
        Operation problem = deserializer.deserialize(block.text());

        assertThat(problem).isNotNull();
        assertThat(problem.operator().symbol()).isEqualTo('+');
        assertThat(problem.operands()).extracting("value").containsExactly(10L, 10L);
    }

    @Test
    public void should_throw_exception_when_block_is_null() {
        assertThatThrownBy(() -> new ProblemBlock(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Block cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_block_is_empty() {
        assertThatThrownBy(() -> new ProblemBlock(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Block cannot be null or empty");

        assertThatThrownBy(() -> new ProblemBlock(List.of("   ", "")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Block cannot be null or empty");
    }
}
