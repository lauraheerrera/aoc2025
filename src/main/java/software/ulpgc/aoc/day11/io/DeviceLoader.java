package software.ulpgc.aoc.day11.io;

import software.ulpgc.aoc.day11.model.Device;
import java.io.IOException;
import java.util.List;

public interface DeviceLoader {
    List<Device> load() throws IOException;
}
