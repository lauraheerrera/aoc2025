package software.ulpgc.aoc.day07.b.model;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.IntStream;

import software.ulpgc.aoc.day07.model.Column;
import software.ulpgc.aoc.day07.model.Row;

public class Paths {
    private final List<BigInteger> values;

    private Paths(List<BigInteger> values) {
        this.values = List.copyOf(values);
    }

    public static Paths initial(int size) {
        return new Paths(
                IntStream.range(0, size)
                        .mapToObj(i -> BigInteger.ONE)
                        .toList());
    }

    public BigInteger get(Column col) {
        if (col.index() < 0 || col.index() >= values.size()) {
            return BigInteger.ONE;
        }
        return values.get(col.index());
    }

    public static Paths next(Paths current, Row row) {
        return new Paths(
                IntStream.range(0, row.size())
                        .mapToObj(i -> nextValue(current, row, new Column(i)))
                        .toList());
    }

    private static BigInteger nextValue(Paths current, Row row, Column col) {
        return row.isSplitterAt(col)
                ? current.get(col.left()).add(current.get(col.right()))
                : current.get(col);
    }
}