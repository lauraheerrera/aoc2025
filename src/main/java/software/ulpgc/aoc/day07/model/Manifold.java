package software.ulpgc.aoc.day07.model;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Manifold(Grid grid) {
    public Manifold(List<String> gridLines) {
        this(Grid.from(gridLines));
    }

    public long countSplits() {
        return grid.rows().stream()
                .reduce(new Splits(Set.of(findS()), 0L), this::next, (a, b) -> a)
                .count();
    }

    private Splits next(Splits state, Row row) {
        return new Splits(nextBeams(state.beams(), row), state.count() + hits(state.beams(), row));
    }

    private Set<Column> nextBeams(Set<Column> beams, Row row) {
        return beams.stream()
                .flatMap(c -> nextCols(c, row).stream())
                .collect(Collectors.toSet());
    }

    private List<Column> nextCols(Column c, Row row) {
        return row.isSplitterAt(c) ? List.of(c.left(), c.right()) : List.of(c);
    }

    private long hits(Set<Column> beams, Row row) {
        return beams.stream().filter(row::isSplitterAt).count();
    }

    public BigInteger countPaths() {
        return reversedRows().stream()
                .reduce(initialPaths(), this::next, (a, b) -> a)
                .get(findS());
    }

    private Paths initialPaths() {
        return Paths.initial(grid.getRow(0).size());
    }

    private Paths next(Paths paths, Row row) {
        return Paths.next(paths, row);
    }

    private Column findS() {
        return grid.getRow(0).findStartColumn();
    }

    private List<Row> reversedRows() {
        return IntStream.range(0, grid.size())
                .mapToObj(i -> grid.getRow(grid.size() - 1 - i))
                .toList();
    }

    private record Splits(Set<Column> beams, long count) {
    }
}
