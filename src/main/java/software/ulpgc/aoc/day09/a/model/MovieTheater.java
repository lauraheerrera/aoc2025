package software.ulpgc.aoc.day09.a.model;

import java.util.List;
import java.util.stream.IntStream;
import software.ulpgc.aoc.day09.model.Tile;

public class MovieTheater implements software.ulpgc.aoc.day09.model.MovieTheaterInterface {

    private final List<Tile> redTiles;

    private MovieTheater(List<Tile> redTiles) {
        this.redTiles = redTiles;
    }

    public static MovieTheater from(List<Tile> redTiles) {
        return new MovieTheater(redTiles);
    }

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
