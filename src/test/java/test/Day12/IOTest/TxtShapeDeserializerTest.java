package test.Day12.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day12.io.TxtShapeDeserializer;
import software.ulpgc.aoc.day12.model.Shape;

import static org.assertj.core.api.Assertions.assertThat;

public class TxtShapeDeserializerTest {

    @Test
    public void should_deserialize_shape_block_correctly() {
        // Given a valid shape block from the input
        TxtShapeDeserializer deserializer = new TxtShapeDeserializer();

        // When the block is deserialized into a Shape
        Shape shape0 = deserializer.deserialize("""
                0:
                ##.
                .##
                ..#
                """);

        // Then the shape size should match the number of occupied cells
        assertThat(shape0.size()).isEqualTo(5);
    }

    @Test
    public void should_deserialize_second_shape_correctly() {
        // Given a second valid shape block from the input
        TxtShapeDeserializer deserializer = new TxtShapeDeserializer();

        // When the block is deserialized into a Shape
        Shape shape1 = deserializer.deserialize("""
                1:
                ###
                ##.
                #..
                """);

        // Then the shape size should match the number of occupied cells
        assertThat(shape1.size()).isEqualTo(6);
    }
}