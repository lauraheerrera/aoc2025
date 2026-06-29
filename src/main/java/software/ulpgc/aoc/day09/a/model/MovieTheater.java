package software.ulpgc.aoc.day09.a.model;

import java.util.List;
import java.util.stream.IntStream;
import software.ulpgc.aoc.day09.model.Tile;

public record MovieTheater(List<Tile> redTiles) implements software.ulpgc.aoc.day09.model.MovieTheaterInterface {

    @Override
    public long maxRectangleArea() {
        return IntStream.range(0, redTiles.size())
                .boxed()
                .flatMapToLong(i -> IntStream.range(i + 1, redTiles.size())
                        .mapToLong(j -> redTiles.get(i).rectangleAreaWith(redTiles.get(j))))
                .max()
                .orElse(0L);
    }

}
