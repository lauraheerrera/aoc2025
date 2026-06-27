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

    private static <T> List<T> parseSection(String content, int sectionIndex, Deserializer<T> deserializer) {
        String[] sections = content.split("\r?\n\r?\n");
        if (sectionIndex >= sections.length) return List.of();
        return Arrays.stream(sections[sectionIndex].split("\r?\n"))
                .filter(line -> !line.isBlank())
                .map(deserializer::deserialize)
                .toList();
    }

    @Test
    public void should_validate_id_value() {
        ID id = new ID(100L);
        assertThat(id.value()).isEqualTo(100L);
    }

    @Test
    public void should_validate_range_methods() {
        Range r1 = new Range(new ID(10), new ID(15));
        Range r2 = new Range(new ID(16), new ID(20));
        Range r3 = new Range(new ID(25), new ID(30));

        assertThat(r1.length()).isEqualTo(6);
        assertThat(r1.contains(new ID(12))).isTrue();
        assertThat(r1.contains(new ID(9))).isFalse();
        assertThat(r1.contains(new ID(16))).isFalse();

        assertThat(r1.mergeableWith(r2)).isTrue();
        assertThat(r1.mergeableWith(r3)).isFalse();

        Range merged = r1.merge(r2);
        assertThat(merged.start()).isEqualTo(new ID(10));
        assertThat(merged.end()).isEqualTo(new ID(20));

        assertThat(r1.compareTo(r2)).isLessThan(0);
        assertThat(r2.compareTo(r1)).isGreaterThan(0);
    }

    @Test
    public void should_handle_database_loader_empty_or_missing_sections() {
        assertThat(parseSection("", 0, rangeDeserializer)).isEmpty();
        assertThat(parseSection("", 1, idDeserializer)).isEmpty();
    }

    @Test
    public void count_fresh_ids() {
        assertThat(FreshnessValidator.fromRanges(ranges).countFresh(ids)).isEqualTo(3);
        assertThat(FreshnessValidator.fromRanges(toRanges("1-5", "8-10"))
                .countFresh(toIds("0, 1, 3, 6, 8, 11"))).isEqualTo(3);
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
