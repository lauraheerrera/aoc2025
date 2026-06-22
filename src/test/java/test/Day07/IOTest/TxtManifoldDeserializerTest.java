package test.Day07.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day07.io.TxtManifoldDeserializer;
import software.ulpgc.aoc.day07.model.Manifold;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtManifoldDeserializerTest {

    private final TxtManifoldDeserializer deserializer = new TxtManifoldDeserializer();

    @Test
    public void should_deserialize_manifold_correctly() {
        String content = """
                S...
                .#..
                ....
                """;
        Manifold manifold = deserializer.deserialize(content);
        assertThat(manifold).isNotNull();
        assertThat(manifold.grid()).isNotNull();
        assertThat(manifold.grid().size()).isEqualTo(3);
    }

    @Test
    public void should_throw_exception_when_content_is_null() {
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Content cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_content_is_empty() {
        assertThatThrownBy(() -> deserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Content cannot be null or empty");
    }
}
