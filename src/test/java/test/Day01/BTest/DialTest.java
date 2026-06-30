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
    public void initial_position_should_be_50_using_create() {
        Dial dial = Dial.create();
        assertThat(DialStatus.initial(dial).position()).isEqualTo(50);
    }

    @Test
    public void given_orders_should_account_the_times_that_position_is_zero() {
        Dial dial = Dial.create();
        assertThat(
                DialCalculator.of(execute(dial, orders.split("\n"))).countEndingInZero())
                .isEqualTo(3);
        assertThat(DialCalculator.of(execute(dial, "R1000")).countCrossingZero())
                .isEqualTo(10);
        assertThat(DialCalculator.of(execute(dial, "L68", "L30", "R48", "L5")).countCrossingZero()).isEqualTo(2);
        assertThat(
                DialCalculator.of(execute(dial, orders.split("\n"))).countCrossingZero())
                .isEqualTo(6);
    }

    @Test
    public void given_empty_orders_should_remain_at_50_and_count_zero_total_zeros() {
        Dial dial = Dial.create();
        DialStatus dialStatus = execute(dial);
        assertThat(dialStatus.position()).isEqualTo(50);
        assertThat(DialCalculator.of(dialStatus).countCrossingZero()).isEqualTo(0);
    }

    @Test
    public void given_exact_rotations_should_work_on_boundaries_crossing_zero() {
        Dial dial = Dial.create();
        DialStatus dialToZero = execute(dial, "L50");
        assertThat(dialToZero.position()).isEqualTo(0);
        assertThat(DialCalculator.of(dialToZero).countCrossingZero()).isEqualTo(1);

        DialStatus dialWrapToZero = execute(dial, "L150");
        assertThat(dialWrapToZero.position()).isEqualTo(0);
        assertThat(DialCalculator.of(dialWrapToZero).countCrossingZero()).isEqualTo(2);
    }

    @Test
    public void given_exact_full_turnaround_should_return_to_50_and_cross_zero_correctly() {
        Dial dial = Dial.create();
        DialStatus dialFullRight = execute(dial, "R100");
        assertThat(dialFullRight.position()).isEqualTo(50);
        assertThat(DialCalculator.of(dialFullRight).countCrossingZero()).isEqualTo(1);

        DialStatus dialFullLeft = execute(dial, "L100");
        assertThat(dialFullLeft.position()).isEqualTo(50);
        assertThat(DialCalculator.of(dialFullLeft).countCrossingZero()).isEqualTo(1);
    }

}
