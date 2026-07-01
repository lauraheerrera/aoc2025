package test.Day05.ATest;

import org.junit.Test;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day05.io.TxtIDDeserializer;
import software.ulpgc.aoc.day05.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day05.model.FreshnessValidator;
import software.ulpgc.aoc.day05.model.ID;
import software.ulpgc.aoc.day05.model.Range;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    private static final String input = """
            3-5
            10-14
            16-20
            12-18

            1
            5
            8
            11
            17
            32
            """;

    Deserializer<Range> rangeDeserializer = new TxtRangeDeserializer();
    Deserializer<ID> idDeserializer = new TxtIDDeserializer();

    List<Range> ranges = parseSection(input, 0, rangeDeserializer);
    List<ID> ids = parseSection(input, 1, idDeserializer);

    private static <T> List<T> parseSection(
            String content,
            int sectionIndex,
            Deserializer<T> deserializer
    ) {

        // Given a raw multi-section input
        String[] sections = content.split("\r?\n\r?\n");

        // If section does not exist
        if (sectionIndex >= sections.length) return List.of();

        // When splitting lines inside a section
        return Arrays.stream(sections[sectionIndex].split("\r?\n"))
                .filter(line -> !line.isBlank())

                // And deserializing each line
                .map(deserializer::deserialize)
                .toList();
    }

    @Test
    public void should_validate_id_value_correctly() {

        // Given an ID
        ID id = new ID(100L);

        // Then value should be stored correctly
        assertThat(id.value()).isEqualTo(100L);
    }

    @Test
    public void should_validate_range_behaviour() {

        // Given three ranges
        Range r1 = new Range(new ID(10), new ID(15));
        Range r2 = new Range(new ID(16), new ID(20));
        Range r3 = new Range(new ID(25), new ID(30));

        // Then range length should be inclusive
        assertThat(r1.length()).isEqualTo(6);

        // And containment rules should work
        assertThat(r1.contains(new ID(12))).isTrue();
        assertThat(r1.contains(new ID(9))).isFalse();
        assertThat(r1.contains(new ID(16))).isFalse();

        // And mergeability should work
        assertThat(r1.mergeableWith(r2)).isTrue();
        assertThat(r1.mergeableWith(r3)).isFalse();

        // And merging should produce correct range
        Range merged = r1.merge(r2);
        assertThat(merged.start()).isEqualTo(new ID(10));
        assertThat(merged.end()).isEqualTo(new ID(20));

        // And ordering should be consistent
        assertThat(r1.compareTo(r2)).isLessThan(0);
        assertThat(r2.compareTo(r1)).isGreaterThan(0);
    }

    @Test
    public void should_return_empty_when_sections_are_missing() {

        // Given empty input
        assertThat(parseSection("", 0, rangeDeserializer)).isEmpty();
        assertThat(parseSection("", 1, idDeserializer)).isEmpty();
    }

    @Test
    public void should_count_fresh_ids_correctly() {

        // Given ranges and ids from input
        assertThat(
                FreshnessValidator.fromRanges(ranges).countFresh(ids)
        ).isEqualTo(3);

        // And a second scenario
        assertThat(
                FreshnessValidator.fromRanges(
                        toRanges("1-5", "8-10")
                ).countFresh(
                        toIds("0, 1, 3, 6, 8, 11")
                )
        ).isEqualTo(3);
    }

    private List<Range> toRanges(String... lines) {
        return Arrays.stream(lines)
                .map(rangeDeserializer::deserialize)
                .toList();
    }

    private List<ID> toIds(String... lines) {
        return Arrays.stream(lines)
                .map(line -> line.split(","))
                .flatMap(Arrays::stream)
                .map(String::trim)
                .map(idDeserializer::deserialize)
                .toList();
    }
}