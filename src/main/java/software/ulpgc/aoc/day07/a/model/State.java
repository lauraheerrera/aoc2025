package software.ulpgc.aoc.day07.a.model;

import java.util.Set;

import software.ulpgc.aoc.day07.model.Column;
import software.ulpgc.aoc.day07.model.Manifold;
import software.ulpgc.aoc.day07.model.Row;

public record State(Set<Column> beams, long count) {

    public static State next(State state, Row row) {
        Set<Column> nextBeams = state.beams().stream()
                .flatMap(c -> row.isSplitterAt(c)
                        ? java.util.List.of(c.left(), c.right()).stream()
                        : java.util.List.of(c).stream())
                .collect(java.util.stream.Collectors.toSet());

        long hits = state.beams().stream()
                .filter(row::isSplitterAt)
                .count();

        return new State(nextBeams, state.count() + hits);
    }

    public static State initial(Manifold manifold) {
        return new State(
                Set.of(manifold.findStartColumn()),
                0L);
    }
}
