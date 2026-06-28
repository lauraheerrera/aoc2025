package software.ulpgc.aoc.day10.a.model;

import software.ulpgc.aoc.day10.model.MachineCommand;

public class Solver implements MachineCommand<Machine> {

    @Override
    public long execute(Machine machine) {
        long[] masks = machine.buttons().stream()
                .mapToLong(b -> b.targets().stream().mapToLong(i -> 1L << i).sum())
                .toArray();
        return findMinPresses(0, new MachineStatus(machine, 0L), 0, 999999, masks);
    }

    private long findMinPresses(int idx, MachineStatus status, long presses, long best, long[] masks) {
        if (status.isSolved()) return Math.min(presses, best);
        if (idx >= masks.length || presses >= best) return best;

        return findMinPresses(idx + 1, status.nextStatus(masks[idx]), presses + 1,
                findMinPresses(idx + 1, status, presses, best, masks), masks);
    }
}
