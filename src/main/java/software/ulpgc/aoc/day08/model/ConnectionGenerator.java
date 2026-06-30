package software.ulpgc.aoc.day08.model;

import java.util.List;
import java.util.stream.IntStream;

public class ConnectionGenerator {

    private final List<JunctionBox> boxes;

    private ConnectionGenerator(List<JunctionBox> boxes) {
        this.boxes = boxes;
    }

    public static List<Connection> from(List<JunctionBox> boxes) {
        return IntStream.range(0, boxes.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, boxes.size())
                        .mapToObj(j -> new Connection(
                                boxes.get(i),
                                boxes.get(j),
                                boxes.get(i).squaredDistanceTo(boxes.get(j)))))
                .sorted()
                .toList();
    }
}