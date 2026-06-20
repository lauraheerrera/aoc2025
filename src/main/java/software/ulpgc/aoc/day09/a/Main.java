package software.ulpgc.aoc.day09.a;

import software.ulpgc.aoc.day09.io.PointLoader;
import software.ulpgc.aoc.day09.io.TxtPointDeserializer;
import software.ulpgc.aoc.day09.io.TxtPointLoader;
import software.ulpgc.aoc.day09.a.model.MovieTheater;
import software.ulpgc.aoc.day09.model.Point;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day09/input.txt");
        PointLoader loader = new TxtPointLoader(file, new TxtPointDeserializer());
        List<Point> points = loader.load();
        MovieTheater theater = new MovieTheater(points);
        System.out.println("El resultado es: " + theater.maxRectangleArea());
    }
}
