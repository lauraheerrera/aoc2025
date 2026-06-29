package software.ulpgc.aoc.day06.model;

import java.util.List;

public record Operation(List<Operand> operands, Operator operator) {
    public Operation {
        operands = List.copyOf(operands);
    }

    public long solve() {
        return (operands.isEmpty()) ? 0L
                : operands.stream()
                        .mapToLong(Operand::value)
                        .reduce(operator::apply)
                        .orElse(0L);
    }
}
