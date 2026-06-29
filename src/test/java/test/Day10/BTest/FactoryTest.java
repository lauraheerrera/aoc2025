package test.Day10.BTest;

import org.junit.Test;
import software.ulpgc.aoc.day10.b.io.TxtMachineDeserializer;
import software.ulpgc.aoc.day10.b.model.Solver;
import software.ulpgc.aoc.day10.b.model.Machine;
import software.ulpgc.aoc.day10.model.Factory;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FactoryTest {
    private static final String EXAMPLE = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            """;

    @Test
    public void solve_example() {
        List<Machine> machines = Arrays.stream(EXAMPLE.split("\n"))
                .map(new TxtMachineDeserializer()::deserialize)
                .toList();
        assertThat(
                new Factory<>(new Solver()).totalMinPresses(machines)).isEqualTo(33);
    }
}
