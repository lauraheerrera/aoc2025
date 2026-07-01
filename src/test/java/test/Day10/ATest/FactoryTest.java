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
        public void should_solve_example_factory_problem() {

                // Given a textual representation of a set of machines
                String input = EXAMPLE;

                // And a deserializer that converts text into Machine objects
                TxtMachineDeserializer deserializer = new TxtMachineDeserializer();

                // And a list of machines parsed from input
                List<Machine> machines = Arrays.stream(input.split("\n"))
                        .map(deserializer::deserialize)
                        .collect(Collectors.toList());

                // And a factory configured with a solver
                Factory<Machine> factory = new Factory<>(new Solver());

                // When calculating the total minimum presses
                long result = factory.totalMinPresses(machines);

                // Then the result should match the expected value
                assertThat(result).isEqualTo(7);
        }

        @Test
        public void should_solve_machine_edge_cases() {

                // Given a solver for machines
                Solver solver = new Solver();

                // And a machine that should resolve to zero cost
                Machine machine = new Machine(
                        0L,
                        List.of(
                                new Button(List.of(0)),
                                new Button(List.of(1))
                        )
                );

                // When executing the solver
                long result1 = solver.execute(machine);

                // Then it should return 0
                assertThat(result1).isEqualTo(0);

                // Given a machine that is unreachable
                Machine unreachable = new Machine(
                        15L,
                        List.of(new Button(List.of(3)))
                );

                // When executing the solver
                long result2 = solver.execute(unreachable);

                // Then it should return infinity-like penalty
                assertThat(result2).isEqualTo(999999);
        }
}