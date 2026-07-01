package test.Day01.BTest;

import org.junit.Test;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day01.io.TxtOrderDeserializer;
import software.ulpgc.aoc.day01.model.Dial;
import software.ulpgc.aoc.day01.model.DialCalculator;
import software.ulpgc.aoc.day01.model.DialStatus;
import software.ulpgc.aoc.day01.model.Order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DialTest {

    private static final String orders = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
            """;

    Deserializer<Order> deserializer = new TxtOrderDeserializer();

    private List<Order> toOrders(String... lines) {
        return Arrays.stream(lines)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(deserializer::deserialize)
                .collect(Collectors.toList());
    }

    private DialStatus execute(Dial dial, String... lines) {
        return toOrders(lines).stream()
                .reduce(
                        DialStatus.initial(dial),
                        DialStatus::execute,
                        (a, b) -> b);
    }

    @Test
    public void should_start_at_position_50() {

        // Given a new dial
        Dial dial = Dial.create();

        // When initializing the dial
        DialStatus status = DialStatus.initial(dial);

        // Then position should be 50
        assertThat(status.position()).isEqualTo(50);
    }

    @Test
    public void should_count_zero_crossings_and_ending_conditions() {

        // Given a dial
        Dial dial = Dial.create();

        // When executing a full set of orders
        DialStatus full = execute(dial, orders.split("\n"));

        // Then ending-in-zero count should match expected value
        assertThat(DialCalculator.of(full).countEndingInZero())
                .isEqualTo(3);

        // And crossing-zero count should match expected value
        assertThat(DialCalculator.of(execute(dial, "R1000")).countCrossingZero())
                .isEqualTo(10);

        assertThat(DialCalculator.of(execute(dial, "L68", "L30", "R48", "L5")).countCrossingZero())
                .isEqualTo(2);

        assertThat(DialCalculator.of(full).countCrossingZero())
                .isEqualTo(6);
    }

    @Test
    public void should_remain_stable_with_no_orders() {

        // Given a dial with no operations
        Dial dial = Dial.create();

        // When executing empty input
        DialStatus status = execute(dial);

        // Then position should remain initial
        assertThat(status.position()).isEqualTo(50);

        // And no zero crossings should be recorded
        assertThat(DialCalculator.of(status).countCrossingZero()).isEqualTo(0);
    }

    @Test
    public void should_handle_boundary_rotations() {

        // Given a dial

        Dial dial = Dial.create();

        // When rotating exactly to zero
        DialStatus zero1 = execute(dial, "L50");
        DialStatus zero2 = execute(dial, "L150");

        // Then position should wrap correctly
        assertThat(zero1.position()).isEqualTo(0);
        assertThat(zero2.position()).isEqualTo(0);

        // And crossings should be counted correctly
        assertThat(DialCalculator.of(zero1).countCrossingZero()).isEqualTo(1);
        assertThat(DialCalculator.of(zero2).countCrossingZero()).isEqualTo(2);
    }

    @Test
    public void should_return_to_50_and_count_crossings_on_full_rotation() {

        // Given a dial

        Dial dial = Dial.create();

        // When rotating full cycles
        DialStatus right = execute(dial, "R100");
        DialStatus left = execute(dial, "L100");

        // Then position returns to initial
        assertThat(right.position()).isEqualTo(50);
        assertThat(left.position()).isEqualTo(50);

        // And crossings are still counted
        assertThat(DialCalculator.of(right).countCrossingZero()).isEqualTo(1);
        assertThat(DialCalculator.of(left).countCrossingZero()).isEqualTo(1);
    }
}