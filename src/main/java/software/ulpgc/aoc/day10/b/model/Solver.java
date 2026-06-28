package software.ulpgc.aoc.day10.b.model;

import software.ulpgc.aoc.day10.model.MachineCommand;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Solver implements MachineCommand<Machine> {

    private static final long INF = 999999999999L;

    @Override
    public long execute(Machine machine) {
        return findMinimumPresses(MachineStatus.initial(machine), new HashMap<>(), machine);
    }

    private long findMinimumPresses(MachineStatus status, Map<MachineStatus, Long> memo, Machine machine) {
        if (status.isFullyConfigured())
            return 0;
        if (memo.containsKey(status))
            return memo.get(status);

        long minPresses = IntStream.range(0, 1 << machine.buttons().size())
                .filter(status::isConfigurationFeasible)
                .mapToLong(mask -> Integer.bitCount(mask) + 2 * findMinimumPresses(status.nextJoltageState(mask), memo, machine))
                .min()
                .orElse(INF);

        memo.put(status, minPresses);
        return minPresses;
    }
}
