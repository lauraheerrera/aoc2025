package test.Day07.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day07.io.TxtManifoldDeserializer;
import software.ulpgc.aoc.day07.model.Manifold;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtManifoldDeserializerTest {

    private final TxtManifoldDeserializer deserializer =
            new TxtManifoldDeserializer();

    @Test
    public void should_deserialize_manifold_correctly() {

        // Given a valid text representation of a manifold
        String content = """
                S...
                ....
                ....
                """;

        // When deserializing
        Manifold grid = deserializer.deserialize(content);

        // Then grid should be correctly built
        assertThat(grid).isNotNull();
        assertThat(grid.size()).isEqualTo(3);
    }

    @Test
    public void should_throw_exception_when_content_is_null_or_empty() {

        // Given invalid inputs

        // When / Then null should fail
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Content cannot be null or empty");

        // When / Then empty should fail
        assertThatThrownBy(() -> deserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Content cannot be null or empty");
    }
}