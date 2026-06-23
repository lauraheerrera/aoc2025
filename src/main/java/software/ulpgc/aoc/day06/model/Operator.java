package software.ulpgc.aoc.day06.model;

import java.util.Arrays;

public enum Operator {
    ADD('+') {
        @Override
        public long apply(long a, long b) {
            return a + b;
        }
    },
    MULTIPLY('*') {
        @Override
        public long apply(long a, long b) {
            return a * b;
        }
    };

    private final char symbol;

    Operator(char symbol) {
        this.symbol = symbol;
    }

    public char symbol() {
        return symbol;
    }

    public abstract long apply(long a, long b);

    public static Operator from(char symbol) {
        return Arrays.stream(values())
                .filter(op -> op.symbol == symbol)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + symbol));
    }
}
