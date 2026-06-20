package test.Day01.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day01.io.TxtOrderDeserializer;
import software.ulpgc.aoc.day01.model.Order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TxtOrderDeserializerTest {

    private final TxtOrderDeserializer deserializer = new TxtOrderDeserializer();

    @Test
    public void should_deserialize_left_orders_correctly() {
        Order order = deserializer.deserialize("L5");
        assertThat(order).isNotNull();
        assertThat(order.step()).isEqualTo(-5);

        Order orderLarge = deserializer.deserialize("L1000");
        assertThat(orderLarge.step()).isEqualTo(-1000);
    }

    @Test
    public void should_deserialize_right_orders_correctly() {
        Order order = deserializer.deserialize("R10");
        assertThat(order).isNotNull();
        assertThat(order.step()).isEqualTo(10);

        Order orderLarge = deserializer.deserialize("R999");
        assertThat(orderLarge.step()).isEqualTo(999);
    }

    @Test
    public void should_throw_exception_when_line_is_null() {
        assertThatThrownBy(() -> deserializer.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_line_is_empty() {
        assertThatThrownBy(() -> deserializer.deserialize(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_line_is_blank() {
        assertThatThrownBy(() -> deserializer.deserialize("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Line cannot be null or empty");
    }

    @Test
    public void should_throw_exception_when_number_is_invalid() {
        assertThatThrownBy(() -> deserializer.deserialize("L"))
                .isInstanceOf(NumberFormatException.class);

        assertThatThrownBy(() -> deserializer.deserialize("Rabc"))
                .isInstanceOf(NumberFormatException.class);
    }
}
