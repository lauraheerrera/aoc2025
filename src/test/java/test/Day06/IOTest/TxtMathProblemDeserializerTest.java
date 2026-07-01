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
    public void should_deserialize_row_operation_correctly() {

        // Given a row-formatted arithmetic problem
        String input = """
                10 20
                +
                """;

        Deserializer<Operation> deserializer = new RowOperationDeserializer();

        // When parsing
        Operation problem = deserializer.deserialize(input);

        // Then operands and operator should be correct
        assertThat(problem).isNotNull();
        assertThat(problem.operands())
                .extracting("value")
                .containsExactly(10L, 20L);

        assertThat(problem.operator().symbol()).isEqualTo('+');
    }

    @Test
    public void should_deserialize_column_operation_correctly() {

        // Given a column-formatted arithmetic problem (R2L parsing)
        String input = """
                11
                00
                +
                """;

        Deserializer<Operation> deserializer = new ColumnR2LOperationDeserializer();

        // When parsing
        Operation problem = deserializer.deserialize(input);

        // Then values should be interpreted correctly
        assertThat(problem).isNotNull();
        assertThat(problem.operator().symbol()).isEqualTo('+');

        assertThat(problem.operands())
                .extracting("value")
                .containsExactly(10L, 10L);
    }

    @Test
    public void should_throw_exception_when_worksheet_is_null_or_empty() {

        // Given invalid worksheet inputs

        // When / Then null should fail
        assertThatThrownBy(() -> WorksheetSplitter.splitIntoBlocks(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Worksheet lines cannot be null, empty or blank");

        // When / Then empty list should fail
        assertThatThrownBy(() -> WorksheetSplitter.splitIntoBlocks(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Worksheet lines cannot be null, empty or blank");

        // When / Then blank content should fail
        assertThatThrownBy(() -> WorksheetSplitter.splitIntoBlocks(List.of("   ", "")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Worksheet lines cannot be null, empty or blank");
    }
}