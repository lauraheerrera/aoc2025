package test.Day05.BTest;

import org.junit.Test;

import software.ulpgc.aoc.day05.io.RangeDeserializer;
import software.ulpgc.aoc.day05.io.TxtDatabaseLoader;
import software.ulpgc.aoc.day05.io.TxtIDDeserializer;
import software.ulpgc.aoc.day05.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day05.model.FreshnessValidator;
import software.ulpgc.aoc.day05.model.ID;
import software.ulpgc.aoc.day05.model.Range;
import software.ulpgc.aoc.day05.io.IDDeserializer;
import java.util.List;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    private static final String input = """
            3-5
            10-14
            16-20
            12-18
            """;

    RangeDeserializer rangeDeserializer = new TxtRangeDeserializer();
    IDDeserializer idDeserializer = new TxtIDDeserializer();
    TxtDatabaseLoader loader = new TxtDatabaseLoader(input, rangeDeserializer, idDeserializer);

    List<Range> ranges = loader.loadRanges();
    List<ID> ids = loader.loadIds();

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
