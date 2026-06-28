package software.ulpgc.aoc.day10.model;

public interface MachineCommand<T extends Machine> {
    long execute(T machine);
}
