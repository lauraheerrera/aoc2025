package test.Day01.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day01.io.TxtOrderDeserializer;
import software.ulpgc.aoc.day01.model.Dial;
import software.ulpgc.aoc.day01.model.Order;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DialTest {
    private static final String ordersText = """
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

    private final TxtOrderDeserializer deserializer = new TxtOrderDeserializer();

    private Order o(String line) {
        return deserializer.deserialize(line);
    }

    List<Order> orders = ordersText.lines()
            .map(deserializer::deserialize)
            .toList();

    @Test
    public void initial_position_should_be_50_using_create() {
        assertThat(Dial.create().position()).isEqualTo(50);
    }

    @Test
    public void given_orders_should_account_the_final_position() {
        assertThat(Dial.create().add(o("L1")).position()).isEqualTo(49);
        assertThat(Dial.create().add(o("L1"), o("R1"), o("R50")).position()).isEqualTo(0);
        assertThat(Dial.create().add(o("L68")).position()).isEqualTo(82);
        assertThat(Dial.create().add(o("R68"), o("L68")).position()).isEqualTo(50);
        assertThat(Dial.create().add(o("L51"), o("L500")).position()).isEqualTo(99);
        assertThat(Dial.create().execute(orders).position()).isEqualTo(32);
    }

    @Test
    public void given_orders_should_account_the_times_that_position_is_zero() {
        assertThat(Dial.create().execute(orders).count()).isEqualTo(3);
        assertThat(Dial.create().add(o("L1")).count()).isEqualTo(0);
        assertThat(Dial.create().add(o("L1"), o("R1"), o("R50")).count()).isEqualTo(1);
        assertThat(Dial.create().add(o("L51"), o("L500")).count()).isEqualTo(0);
    }
}
