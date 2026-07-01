package test.Day05.BTest;

import org.junit.Test;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day05.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day05.model.FreshnessValidator;
import software.ulpgc.aoc.day05.model.Range;
import software.ulpgc.aoc.day05.model.ID;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    private static final String input = """
            3-5
            10-14
            16-20
            12-18
            """;

    Deserializer<Range> rangeDeserializer =
            new TxtRangeDeserializer();

    List<Range> ranges = parseSection(input, 0, rangeDeserializer);

    List<ID> ids = List.of(); // no used in this BTest

    private static <T> List<T> parseSection(
            String content,
            int sectionIndex,
            Deserializer<T> deserializer
    ) {

        // Given raw range input
        String[] sections = content.split("\r?\n\r?\n");

        if (sectionIndex >= sections.length) return List.of();

        // When parsing each line into domain objects
        return Arrays.stream(sections[sectionIndex].split("\r?\n"))
                .filter(line -> !line.isBlank())
                .map(deserializer::deserialize)
                .toList();
    }

    @Test
    public void should_count_total_fresh_ids_correctly() {

        // Given a set of ranges
        FreshnessValidator validator =
                FreshnessValidator.fromRanges(ranges);

        // Then total fresh count should match expected value
        assertThat(validator.countTotalFresh()).isEqualTo(14);

        // And a second scenario
        assertThat(
                FreshnessValidator.fromRanges(
                        toRanges("1-5", "8-10")
                ).countTotalFresh()
        ).isEqualTo(8);
    }

    private List<Range> toRanges(String... lines) {
        return Arrays.stream(lines)
                .map(rangeDeserializer::deserialize)
                .toList();
    }
}