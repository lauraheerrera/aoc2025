package software.ulpgc.aoc.day08.model;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConnectionGenerator {

    public Stream<Connection> generate(List<JunctionBox> boxes) {
        return IntStream.range(0, boxes.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, boxes.size())
                        .mapToObj(j -> new Connection(
                                boxes.get(i),
                                boxes.get(j),
                                boxes.get(i).squaredDistanceTo(boxes.get(j)))))
                .sorted();
    }
}