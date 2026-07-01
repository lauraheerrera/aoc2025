package test.Day01.ATest;

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

        // When creating the initial dial status
        DialStatus status = DialStatus.initial(dial);

        // Then the starting position should be 50
        assertThat(status.position()).isEqualTo(50);
    }

    @Test
    public void should_update_position_correctly_after_orders() {

        // Given a dial
        Dial dial = Dial.create();

        // When executing different sequences of orders
        DialStatus s1 = execute(dial, "L1");
        DialStatus s2 = execute(dial, "L1", "R1", "R50");
        DialStatus s3 = execute(dial, "L68");
        DialStatus s4 = execute(dial, "R68", "L68");
        DialStatus s5 = execute(dial, "L51", "L500");
        DialStatus s6 = execute(dial, orders.split("\n"));

        // Then the final positions should match expected values
        assertThat(s1.position()).isEqualTo(49);
        assertThat(s2.position()).isEqualTo(0);
        assertThat(s3.position()).isEqualTo(82);
        assertThat(s4.position()).isEqualTo(50);
        assertThat(s5.position()).isEqualTo(99);
        assertThat(s6.position()).isEqualTo(32);
    }

    @Test
    public void should_count_times_position_ends_in_zero() {

        // Given a dial
        Dial dial = Dial.create();

        // When calculating dial statistics
        DialCalculator calc1 = DialCalculator.of(execute(dial, orders.split("\n")));
        DialCalculator calc2 = DialCalculator.of(execute(dial, "L1"));
        DialCalculator calc3 = DialCalculator.of(execute(dial, "L1", "R1", "R50"));
        DialCalculator calc4 = DialCalculator.of(execute(dial, "L51", "L500"));

        // Then the number of zero-ending positions should match expectations
        assertThat(calc1.countEndingInZero()).isEqualTo(3);
        assertThat(calc2.countEndingInZero()).isEqualTo(0);
        assertThat(calc3.countEndingInZero()).isEqualTo(1);
        assertThat(calc4.countEndingInZero()).isEqualTo(0);
    }

    @Test
    public void should_remain_at_50_when_no_orders() {

        // Given a dial with no orders
        Dial dial = Dial.create();

        // When executing empty input
        DialStatus status = execute(dial);

        // Then position should remain initial
        assertThat(status.position()).isEqualTo(50);

        // And zero counter should be 0
        assertThat(DialCalculator.of(status).countEndingInZero()).isEqualTo(0);
    }

    @Test
    public void should_handle_boundary_rotations_correctly() {

        // Given a dial

        Dial dial = Dial.create();

        // When rotating exactly to boundaries
        DialStatus zero1 = execute(dial, "L50");
        DialStatus zero2 = execute(dial, "L150");

        // Then position should wrap correctly
        assertThat(zero1.position()).isEqualTo(0);
        assertThat(zero2.position()).isEqualTo(0);

        // And each should count as ending in zero
        assertThat(DialCalculator.of(zero1).countEndingInZero()).isEqualTo(1);
        assertThat(DialCalculator.of(zero2).countEndingInZero()).isEqualTo(1);
    }

    @Test
    public void should_return_to_50_after_full_rotation() {

        // Given a dial

        Dial dial = Dial.create();

        // When rotating full cycles
        DialStatus right = execute(dial, "R100");
        DialStatus left = execute(dial, "L100");

        // Then both should return to initial position
        assertThat(right.position()).isEqualTo(50);
        assertThat(left.position()).isEqualTo(50);
    }
}