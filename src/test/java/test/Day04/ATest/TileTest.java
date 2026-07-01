package test.Day04.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day04.model.Tile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TileTest {

    @Test
    public void should_map_valid_symbols_to_tiles_correctly() {

        // Given valid tile symbols

        // Then each symbol should map to the correct Tile enum
        assertThat(Tile.from('@')).isEqualTo(Tile.ROLL);
        assertThat(Tile.from('.')).isEqualTo(Tile.EMPTY);
        assertThat(Tile.from('x')).isEqualTo(Tile.CLEARED);
    }

    @Test
    public void should_throw_exception_for_unknown_symbols() {

        // Given an invalid tile symbol

        // Then an exception should be thrown
        assertThatThrownBy(() -> Tile.from('?'))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown tile symbol: ?");
    }
}