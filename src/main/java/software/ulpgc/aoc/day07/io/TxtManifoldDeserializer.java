package software.ulpgc.aoc.day07.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day07.model.Manifold;

import java.util.Arrays;

public class TxtManifoldDeserializer implements Deserializer<Manifold> {
    @Override
    public Manifold deserialize(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        return new Manifold(Arrays.asList(content.split("\n")));
    }
}
