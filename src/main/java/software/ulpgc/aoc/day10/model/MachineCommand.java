package software.ulpgc.aoc.day10.model;

public interface MachineCommand<T> {
    long execute(T machine);
}
