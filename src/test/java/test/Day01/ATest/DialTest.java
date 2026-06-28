package test.Day01.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day01.model.Dial;
import software.ulpgc.aoc.day01.model.DialCalculator;
import software.ulpgc.aoc.day01.model.DialStatus;

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
        Dial dial = Dial.create();
        assertThat(DialStatus.initial(dial).position()).isEqualTo(50);
    }

    @Test
    public void given_orders_should_account_the_final_position() {
        Dial dial = Dial.create();
        assertThat(DialStatus.initial(dial).execute(toOrders("L1")).position()).isEqualTo(49);
        assertThat(DialStatus.initial(dial).execute(toOrders("L1", "R1", "R50")).position()).isEqualTo(0);
        assertThat(DialStatus.initial(dial).execute(toOrders("L68")).position()).isEqualTo(82);
        assertThat(DialStatus.initial(dial).execute(toOrders("R68", "L68")).position()).isEqualTo(50);
        assertThat(DialStatus.initial(dial).execute(toOrders("L51", "L500")).position()).isEqualTo(99);
        assertThat(DialStatus.initial(dial).execute(toOrders(orders.split("\n"))).position()).isEqualTo(32);
    }

    @Test
    public void given_orders_should_account_the_times_that_position_is_zero() {
        Dial dial = Dial.create();
        assertThat(
                DialCalculator.of(DialStatus.initial(dial).execute(toOrders(orders.split("\n")))).countEndingInZero())
                .isEqualTo(3);
        assertThat(DialCalculator.of(DialStatus.initial(dial).execute(toOrders("L1"))).countEndingInZero())
                .isEqualTo(0);
        assertThat(DialCalculator.of(DialStatus.initial(dial).execute(toOrders("L1", "R1", "R50"))).countEndingInZero())
                .isEqualTo(1);
        assertThat(DialCalculator.of(DialStatus.initial(dial).execute(toOrders("L51", "L500"))).countEndingInZero())
                .isEqualTo(0);
    }

    @Test
    public void given_empty_orders_should_remain_at_50_and_count_zero_zeros() {
        Dial dial = Dial.create();
        DialStatus status = DialStatus.initial(dial).execute(toOrders());
        assertThat(status.position()).isEqualTo(50);
        assertThat(DialCalculator.of(status).countEndingInZero()).isEqualTo(0);
    }

    @Test
    public void given_exact_rotations_should_work_on_boundaries() {
        Dial dial = Dial.create();
        DialStatus dialToZero = DialStatus.initial(dial).execute(toOrders("L50"));
        assertThat(dialToZero.position()).isEqualTo(0);
        assertThat(DialCalculator.of(dialToZero).countEndingInZero()).isEqualTo(1);

        DialStatus dialWrapToZero = DialStatus.initial(dial).execute(toOrders("L150"));
        assertThat(dialWrapToZero.position()).isEqualTo(0);
        assertThat(DialCalculator.of(dialWrapToZero).countEndingInZero()).isEqualTo(1);
    }

    @Test
    public void given_exact_full_turnaround_should_return_to_50() {
        Dial dial = Dial.create();
        DialStatus dialFullRight = DialStatus.initial(dial).execute(toOrders("R100"));
        assertThat(dialFullRight.position()).isEqualTo(50);

        DialStatus dialFullLeft = DialStatus.initial(dial).execute(toOrders("L100"));
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
