package test.Day04.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day04.model.Tile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TileTest {

    @Test
    public void should_parse_valid_tile_symbols() {
        assertThat(Tile.from('@')).isEqualTo(Tile.ROLL);
        assertThat(Tile.from('.')).isEqualTo(Tile.EMPTY);
        assertThat(Tile.from('x')).isEqualTo(Tile.CLEARED);
    }

    @Test
    public void should_throw_exception_on_invalid_tile_symbol() {
        assertThatThrownBy(() -> Tile.from('?'))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown tile symbol: ?");
    }
}
