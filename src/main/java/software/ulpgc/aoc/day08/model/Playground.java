package software.ulpgc.aoc.day08.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Playground(List<JunctionBox> boxes) {

    public long multiplyThreeLargestCircuitSizesAfterConnecting(int limit) {
        return multiplyThreeLargestSizes(
                applyConnections(
                        new DisjointSet<>(),
                        allConnections().stream().limit(limit)));
    }

    public List<Connection> allConnections() {
        return IntStream.range(0, boxes.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, boxes.size())
                        .mapToObj(j -> new Connection(boxes.get(i), boxes.get(j))))
                .sorted()
                .collect(Collectors.toList());
    }

    private DisjointSet<JunctionBox> applyConnections(DisjointSet<JunctionBox> ds, Stream<Connection> connections) {
        connections.forEach(c -> ds.union(c.first(), c.second()));
        return ds;
    }

    private long multiplyThreeLargestSizes(DisjointSet<JunctionBox> ds) {
        return boxes.stream()
                .map(ds::find)
                .distinct()
                .map(ds::size)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(Integer::longValue)
                .reduce(1, (a, b) -> a * b);
    }

    public long lastConnectionCoordinatesProduct() {
        return productOfXCoordinates(findLastConnection(allConnections()));
    }

    private Connection findLastConnection(List<Connection> connections) {
        return findLastConnectionWithState(new DisjointSet<>(), connections, boxes.size());
    }

    private Connection findLastConnectionWithState(DisjointSet<JunctionBox> ds, List<Connection> connections,
            int totalBoxes) {
        return connections.stream()
                .peek(c -> ds.union(c.first(), c.second()))
                .filter(c -> ds.size(c.first()) == totalBoxes)
                .findFirst()
                .orElseThrow();
    }

    private long productOfXCoordinates(Connection connection) {
        return (long) connection.first().x() * connection.second().x();
    }
}
