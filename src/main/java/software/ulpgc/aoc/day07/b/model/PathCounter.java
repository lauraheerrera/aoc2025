package software.ulpgc.aoc.day07.b.model;

import java.math.BigInteger;
import java.util.stream.IntStream;

import software.ulpgc.aoc.day07.model.Manifold;

public class PathCounter {
    private final Manifold manifold;

    private PathCounter(Manifold manifold) {
        this.manifold = manifold;
    }

    public static PathCounter of(Manifold manifold) {
        return new PathCounter(manifold);
    }

    public BigInteger countPaths() {
        return IntStream.range(0, manifold.size())
                .mapToObj(i -> manifold.getRow(manifold.size() - 1 - i))
                .reduce(Paths.initial(manifold.size()), Paths::next, (a, b) -> a)
                .get(manifold.findStartColumn());
    }
}