package software.ulpgc.aoc.day09.a;

import software.ulpgc.aoc.day09.io.TxtPointDeserializer;
import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day09.a.model.MovieTheater;
import software.ulpgc.aoc.day09.model.Tile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day09/input.txt");
        Deserializer<Tile> deserializer = new TxtPointDeserializer();
        List<Tile> tiles = LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();
        MovieTheater theater = MovieTheater.from(tiles);
        System.out.println("Area máxima: " + theater.maxRectangleArea());
    }
}
