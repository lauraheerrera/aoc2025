package test.Day01.IOTest;

import org.junit.Test;
import software.ulpgc.aoc.day01.io.TxtOrderDeserializer;
import software.ulpgc.aoc.day01.model.Order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtOrderDeserializerTest {

    private final TxtOrderDeserializer deserializer = new TxtOrderDeserializer();

    @Test
    public void should_deserialize_left_and_right_orders_correctly() {

        // Given a left order
        String left = "L5";

        // When deserializing
        Order order1 = deserializer.deserialize(left);

        // Then it should become a negative step
        assertThat(order1).isNotNull();
        assertThat(order1.step()).isEqualTo(-5);

        // Given a larger left order
        Order order2 = deserializer.deserialize("L1000");

        // Then it should also be negative
        assertThat(order2.step()).isEqualTo(-1000);

        // Given a right order
        Order order3 = deserializer.deserialize("R10");

        // Then it should be positive
        assertThat(order3.step()).isEqualTo(10);

        // Given a larger right order
        Order order4 = deserializer.deserialize("R999");

        // Then it should also be positive
        assertThat(order4.step()).isEqualTo(999);
    }

    @Test
    public void should_throw_exception_when_input_is_invalid() {

        // Given invalid inputs

        // When / Then null should fail
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");

        // When / Then empty string should fail
        assertThatThrownBy(() -> deserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");

        // When / Then blank string should fail
        assertThatThrownBy(() -> deserializer.deserialize("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");

        // When / Then malformed number should fail
        assertThatThrownBy(() -> deserializer.deserialize("L"))
                .isInstanceOf(NumberFormatException.class);

        assertThatThrownBy(() -> deserializer.deserialize("Rabc"))
                .isInstanceOf(NumberFormatException.class);
    }
}