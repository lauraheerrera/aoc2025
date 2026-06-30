package software.ulpgc.aoc.day12.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day12.model.Region;

import java.util.Arrays;
import java.util.List;

public class TxtRegionDeserializer implements Deserializer<Region> {

    @Override
    public Region deserialize(String line) {

        line = line.trim();
        if (line.isEmpty())
            return null;

        String[] parts = line.split(":");

        String[] dims = parts[0].trim().split("x");
        int width = Integer.parseInt(dims[0]);
        int height = Integer.parseInt(dims[1]);

        List<Integer> quantities = Arrays.stream(parts[1].trim().split("\\s+"))
                .map(Integer::parseInt)
                .toList();

        return new Region(width, height, quantities);
    }
}