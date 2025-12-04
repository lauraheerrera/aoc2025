package software.ulpgc.aoc.day01.a;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        String input = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(Main.class.getResourceAsStream("/day01/input.txt"))
                )
        ).lines().collect(Collectors.joining("\n"));

        Dial dial = Dial.create().execute(input);

        System.out.println("Posición final: " + dial.position());
        System.out.println("Veces que pasa por 0: " + dial.count());
    }
}
