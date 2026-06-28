package software.ulpgc.aoc.day10.model;

import java.util.List;

public record Factory<T extends Machine>(List<T> machines, MachineCommand<T> command) {
    public long totalMinPresses() {
        return machines.stream()
            .mapToLong(command::execute)
            .sum();
    }
}
