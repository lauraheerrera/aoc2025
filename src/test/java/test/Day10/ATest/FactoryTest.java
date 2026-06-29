package test.Day10.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day10.a.io.TxtMachineDeserializer;
import software.ulpgc.aoc.day10.model.Factory;
import software.ulpgc.aoc.day10.a.model.Machine;
import software.ulpgc.aoc.day10.a.model.Solver;
import software.ulpgc.aoc.day10.model.Button;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                                .collect(Collectors.toList());
                Factory<Machine> factory = new Factory<>(new Solver());
                assertThat(factory.totalMinPresses(machines)).isEqualTo(7);
        }

        @Test
        public void test_machine_edge_cases() {
                Solver solver = new Solver();

                Machine machine = new Machine(0L, List.of(new Button(List.of(0)), new Button(List.of(1))));
                assertThat(solver.execute(machine)).isEqualTo(0);

                Machine unreachable = new Machine(15L, List.of(new Button(List.of(3))));
                assertThat(solver.execute(unreachable)).isEqualTo(999999);
        }
}
