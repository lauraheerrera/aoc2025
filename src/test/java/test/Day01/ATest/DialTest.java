package test.Day01.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day01.model.Dial;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void initial_position_should_be_50_using_create() {
        assertThat(Dial.create().position()).isEqualTo(50);
    }

    @Test
    public void given_orders_should_account_the_final_position() {
        assertThat(Dial.create().execute(toOrders("L1")).position()).isEqualTo(49);
        assertThat(Dial.create().execute(toOrders("L1", "R1", "R50")).position()).isEqualTo(0);
        assertThat(Dial.create().execute(toOrders("L68")).position()).isEqualTo(82);
        assertThat(Dial.create().execute(toOrders("R68", "L68")).position()).isEqualTo(50);
        assertThat(Dial.create().execute(toOrders("L51", "L500")).position()).isEqualTo(99);
        assertThat(Dial.create().execute(toOrders(orders.split("\n"))).position()).isEqualTo(32);
    }

    @Test
    public void given_orders_should_account_the_times_that_position_is_zero() {
        assertThat(Dial.create().execute(toOrders(orders.split("\n"))).count()).isEqualTo(3);
        assertThat(Dial.create().execute(toOrders("L1")).count()).isEqualTo(0);
        assertThat(Dial.create().execute(toOrders("L1", "R1", "R50")).count()).isEqualTo(1);
        assertThat(Dial.create().execute(toOrders("L51", "L500")).count()).isEqualTo(0);
    }

    @Test
    public void given_empty_orders_should_remain_at_50_and_count_zero_zeros() {
        Dial dial = Dial.create().execute(toOrders());
        assertThat(dial.position()).isEqualTo(50);
        assertThat(dial.count()).isEqualTo(0);
    }

    @Test
    public void given_exact_rotations_should_work_on_boundaries() {
        // From 50, L50 -> 0. Lands exactly on 0.
        Dial dialToZero = Dial.create().execute(toOrders("L50"));
        assertThat(dialToZero.position()).isEqualTo(0);
        assertThat(dialToZero.count()).isEqualTo(1);

        // From 50, L150 -> 00. Lands exactly on 0 (after wrapping around).
        Dial dialWrapToZero = Dial.create().execute(toOrders("L150"));
        assertThat(dialWrapToZero.position()).isEqualTo(0);
        assertThat(dialWrapToZero.count()).isEqualTo(1); // count() only measures final positions of steps
    }

    @Test
    public void given_exact_full_turnaround_should_return_to_50() {
        Dial dialFullRight = Dial.create().execute(toOrders("R100"));
        assertThat(dialFullRight.position()).isEqualTo(50);

        Dial dialFullLeft = Dial.create().execute(toOrders("L100"));
        assertThat(dialFullLeft.position()).isEqualTo(50);
    }

    private java.util.List<software.ulpgc.aoc.day01.model.Order> toOrders(String... lines) {
        software.ulpgc.aoc.common.io.Deserializer<software.ulpgc.aoc.day01.model.Order> deserializer = new software.ulpgc.aoc.day01.io.TxtOrderDeserializer();
        return java.util.Arrays.stream(lines)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(deserializer::deserialize)
                .collect(java.util.stream.Collectors.toList());
    }
}
