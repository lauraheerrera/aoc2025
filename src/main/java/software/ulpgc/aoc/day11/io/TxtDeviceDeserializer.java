package software.ulpgc.aoc.day11.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day11.model.Device;

import java.util.Arrays;

public class TxtDeviceDeserializer implements Deserializer<Device> {
    @Override
    public Device deserialize(String line) {
        if (line == null || line.isBlank())
            throw new IllegalArgumentException("Invalid line: " + line);
        return new Device(
                line.substring(0, line.indexOf(':')).trim(),
                Arrays.stream(line.substring(line.indexOf(':') + 1).trim().split("\\s+"))
                        .filter(s -> !s.isBlank())
                        .toList());
    }
}
