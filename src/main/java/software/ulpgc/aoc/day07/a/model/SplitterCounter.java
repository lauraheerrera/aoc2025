package software.ulpgc.aoc.day07.a.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import software.ulpgc.aoc.day07.model.Column;
import software.ulpgc.aoc.day07.model.Manifold;
import software.ulpgc.aoc.day07.model.Row;

public class SplitterCounter {
    private final Manifold manifold;

    private SplitterCounter(Manifold manifold) {
        this.manifold = manifold;
    }

    public static SplitterCounter of(Manifold manifold) {
        return new SplitterCounter(manifold);
    }

    public long countSplits() {
        return manifold.rows().stream()
                .reduce(
                        State.initial(manifold),
                        State::next,
                        (a, b) -> a)
                .count();
    }

    private Set<Column> nextBeams(Set<Column> beams, Row row) {
        return beams.stream()
                .flatMap(c -> nextCols(c, row).stream())
                .collect(Collectors.toSet());
    }

    private List<Column> nextCols(Column c, Row row) {
        return row.isSplitterAt(c)
                ? List.of(c.left(), c.right())
                : List.of(c);
    }

    private long hits(Set<Column> beams, Row row) {
        return beams.stream()
                .filter(row::isSplitterAt)
                .count();
    }

}