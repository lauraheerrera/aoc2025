package test.Day05.BTest;

import org.junit.Test;

import software.ulpgc.aoc.common.io.Deserializer;
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
            """;

    Deserializer<Range> rangeDeserializer = new TxtRangeDeserializer();

    List<Range> ranges = parseSection(input, 0, rangeDeserializer);
    List<ID> ids = List.of();

    private static <T> List<T> parseSection(String content, int sectionIndex, Deserializer<T> deserializer) {
        String[] sections = content.split("\r?\n\r?\n");
        if (sectionIndex >= sections.length) return List.of();
        return Arrays.stream(sections[sectionIndex].split("\r?\n"))
                .filter(line -> !line.isBlank())
                .map(deserializer::deserialize)
                .toList();
    }

    @Test
    public void count_fresh_ids() {
        assertThat(FreshnessValidator.fromRanges(ranges).countTotalFresh()).isEqualTo(14);
        assertThat(FreshnessValidator.fromRanges(toRanges("1-5", "8-10"))
                .countTotalFresh()).isEqualTo(8);
    }

    private List<Range> toRanges(String... lines) {
        return Arrays.stream(lines)
                .map(rangeDeserializer::deserialize)
                .toList();
    }

}
