package software.ulpgc.aoc.day10.a.model;

public record MachineStatus(Machine machine, long currentLights) {

    public boolean isSolved() {
        return currentLights == machine.targetMask();
    }

    public MachineStatus nextStatus(long buttonMask) {
        return new MachineStatus(machine, currentLights ^ buttonMask);
    }
}
