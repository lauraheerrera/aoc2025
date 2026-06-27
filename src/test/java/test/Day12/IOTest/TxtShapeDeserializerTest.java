package test.Day12.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day12.io.TxtShapeDeserializer;
import software.ulpgc.aoc.day12.model.Shape;

import static org.assertj.core.api.Assertions.assertThat;

public class TxtShapeDeserializerTest {

    @Test
    public void should_deserialize_shape_block_correctly() {
        TxtShapeDeserializer shapeDeserializer = new TxtShapeDeserializer();

        Shape shape0 = shapeDeserializer.deserialize("""
                0:
                ##.
                .##
                ..#
                """);
        assertThat(shape0.index()).isEqualTo(0);
        assertThat(shape0.area()).isEqualTo(5);

        Shape shape1 = shapeDeserializer.deserialize("""
                1:
                ###
                ##.
                #..
                """);
        assertThat(shape1.index()).isEqualTo(1);
        assertThat(shape1.area()).isEqualTo(6);
    }
}
