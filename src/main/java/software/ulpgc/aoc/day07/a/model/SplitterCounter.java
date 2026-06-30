package software.ulpgc.aoc.day07.a.model;


import software.ulpgc.aoc.day07.model.Manifold;

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


}