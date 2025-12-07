package test.Day01.BTest;

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
        assertThat(Dial.create().add("L1").position()).isEqualTo(49);
        assertThat(Dial.create().add("L1","R1","R50").position()).isEqualTo(0);
        assertThat(Dial.create().add("L68").position()).isEqualTo(82);
        assertThat(Dial.create().add("R68", "L68").position()).isEqualTo(50);
        assertThat(Dial.create().add("L51","L500").position()).isEqualTo(99);
    }

    @Test
    public void given_orders_should_account_the_times_that_position_is_zero() {
        assertThat(Dial.create().execute(orders).count()).isEqualTo(3);
        assertThat(Dial.create().add("R1000").countTotalZeros()).isEqualTo(10);
        assertThat(Dial.create().add("L68", "L30", "R48", "L5").countTotalZeros()).isEqualTo(2);
        assertThat(Dial.create().execute(orders).countTotalZeros()).isEqualTo(6);
    }
}
