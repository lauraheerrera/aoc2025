package test.Day06.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.io.ColumnR2LOperationDeserializer;
import software.ulpgc.aoc.day06.io.RowOperationDeserializer;
import software.ulpgc.aoc.day06.io.WorksheetSplitter;
import software.ulpgc.aoc.day06.model.Operation;

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

        Deserializer<Operation> deserializer = new RowOperationDeserializer();
        Operation problem = deserializer.deserialize(input);

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

        Deserializer<Operation> deserializer = new ColumnR2LOperationDeserializer();
        Operation problem = deserializer.deserialize(input);

        assertThat(problem).isNotNull();
        assertThat(problem.operator().symbol()).isEqualTo('+');
        assertThat(problem.operands()).extracting("value").containsExactly(10L, 10L);
    }

    @Test
    public void should_throw_exception_when_worksheet_is_null() {
        assertThatThrownBy(() -> WorksheetSplitter.splitIntoBlocks(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Worksheet lines cannot be null, empty or blank");
    }

    @Test
    public void should_throw_exception_when_worksheet_is_empty() {
        assertThatThrownBy(() -> WorksheetSplitter.splitIntoBlocks(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Worksheet lines cannot be null, empty or blank");

        assertThatThrownBy(() -> WorksheetSplitter.splitIntoBlocks(List.of("   ", "")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Worksheet lines cannot be null, empty or blank");
    }
}