package software.ulpgc.aoc.day10.b.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public record MachineStatus(Machine machine, List<Integer> targets) {

    public static MachineStatus initial(Machine machine) {
        return new MachineStatus(machine, Arrays.stream(machine.targets()).boxed().toList());
    }

    public boolean isFullyConfigured() {
        return targets.stream().allMatch(voltage -> voltage == 0);
    }

    public boolean isConfigurationFeasible(int mask) {
        return IntStream.range(0, targets.size()).allMatch(i -> {
            int impact = calculateButtonImpact(mask, i);
            return targets.get(i) >= impact && (targets.get(i) - impact) % 2 == 0;
        });
    }

    public int calculateButtonImpact(int mask, int counterIndex) {
        return (int) IntStream.range(0, machine.buttons().size())
                .filter(btnIdx -> ((mask >> btnIdx) & 1) == 1
                        && machine.buttons().get(btnIdx).targets().contains(counterIndex))
                .count();
    }

    public MachineStatus nextJoltageState(int mask) {
        return new MachineStatus(
                machine,
                IntStream.range(0, targets.size())
                        .map(i -> (targets.get(i) - calculateButtonImpact(mask, i)) / 2)
                        .boxed()
                        .toList());
    }
}
