package test.Day12.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day12.io.TxtRegionDeserializer;
import software.ulpgc.aoc.day12.model.Region;

import static org.assertj.core.api.Assertions.assertThat;

public class TxtRegionDeserializerTest {

    @Test
    public void should_deserialize_region_correctly() {
        // Given a region line in the expected format
        TxtRegionDeserializer regionDeserializer = new TxtRegionDeserializer();

        // When the line is deserialized
        Region region = regionDeserializer.deserialize("49x36: 50 58 46 32 38 51");

        // Then the region dimensions and quantities are parsed correctly
        assertThat(region.width()).isEqualTo(49);
        assertThat(region.height()).isEqualTo(36);
        assertThat(region.area()).isEqualTo(49 * 36);
        assertThat(region.quantities()).containsExactly(50, 58, 46, 32, 38, 51);
    }

    @Test
    public void should_deserialize_regions_with_irregular_spaces() {
        // Given a region line with irregular spaces and tabs
        TxtRegionDeserializer regionDeserializer = new TxtRegionDeserializer();

        // When the line is deserialized
        Region region = regionDeserializer.deserialize("  12x5: \t 1   0 \t 1  0   2   2   ");

        // Then the parser still extracts width, height and quantities correctly
        assertThat(region.width()).isEqualTo(12);
        assertThat(region.height()).isEqualTo(5);
        assertThat(region.quantities()).containsExactly(1, 0, 1, 0, 2, 2);
    }
}
