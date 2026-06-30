package software.ulpgc.aoc.day08.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Playground {

    private final List<Connection> connections;

    private Playground(List<Connection> connections) {
        this.connections = connections;
    }

    public static Playground from(List<Connection> connections) {
        return new Playground(connections);
    }

    public long multiplyThreeLargestCircuitSizesAfterConnecting(int limit) {

        DisjointSet<JunctionBox> ds = new DisjointSet<>();

        connections.stream()
                .sorted()
                .limit(limit)
                .forEach(c -> ds.union(c.first(), c.second()));

        return dsSizes(ds);
    }

    public long lastConnectionCoordinatesProduct() {

        DisjointSet<JunctionBox> ds = new DisjointSet<>();

        return connections.stream()
                .sorted()
                .map(c -> {
                    ds.union(c.first(), c.second());
                    return c;
                })
                .filter(c -> ds.size(c.first()) == sizeOfAllNodes())
                .findFirst()
                .map(this::productOfXCoordinates)
                .orElseThrow();
    }

    private long dsSizes(DisjointSet<JunctionBox> ds) {
        return connections.stream()
                .map(c -> ds.find(c.first()))
                .distinct()
                .map(ds::size)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(Integer::longValue)
                .reduce(1, (a, b) -> a * b);
    }

    private int sizeOfAllNodes() {
        return connections.stream()
                .flatMap(c -> Stream.of(c.first(), c.second()))
                .collect(Collectors.toSet())
                .size();
    }

    private long productOfXCoordinates(Connection connection) {
        return (long) connection.first().x() * connection.second().x();
    }
}