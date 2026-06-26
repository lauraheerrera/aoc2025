package software.ulpgc.aoc.day10.io;

import software.ulpgc.aoc.day10.model.Machine;
import java.io.IOException;
import java.util.List;

public interface MachineLoader {
    List<? extends Machine> load() throws IOException;
}
