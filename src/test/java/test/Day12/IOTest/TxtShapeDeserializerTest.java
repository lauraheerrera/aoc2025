package test.Day12.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day12.io.TxtShapeDeserializer;
import software.ulpgc.aoc.day12.model.Shape;

import static org.assertj.core.api.Assertions.assertThat;

public class TxtShapeDeserializerTest {

    @Test
    public void should_deserialize_shape_block_correctly() {

        TxtShapeDeserializer deserializer = new TxtShapeDeserializer();

        Shape shape0 = deserializer.deserialize("""
                0:
                ##.
                .##
                ..#
                """);

        assertThat(shape0.size()).isEqualTo(5);
    }

    @Test
    public void should_deserialize_second_shape_correctly() {

        TxtShapeDeserializer deserializer = new TxtShapeDeserializer();

        Shape shape1 = deserializer.deserialize("""
                1:
                ###
                ##.
                #..
                """);

        assertThat(shape1.size()).isEqualTo(6);
    }
}