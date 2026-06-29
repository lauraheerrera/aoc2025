package software.ulpgc.aoc.day10.model;

import java.util.List;

public record Factory<T>(MachineCommand<T> command) {
    public long totalMinPresses(List<T> machines) {
        return machines.stream().mapToLong(command::execute).sum();
    }
}
