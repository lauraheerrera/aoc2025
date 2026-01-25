package software.ulpgc.aoc.day01.io;

import software.ulpgc.aoc.day01.model.Order;
import software.ulpgc.aoc.common.io.Deserializer;

public class TxtOrderDeserializer implements Deserializer<Order> {
    @Override
    public Order deserialize(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Line cannot be null or empty");
        }

        return parse(line);
    }

    private Order parse(String order) {
        return new Order(signOf(order) * valueOf(order));
    }

    private int signOf(String order) {
        return order.charAt(0) == 'L' ? -1 : 1;
    }

    private int valueOf(String order) {
        return Integer.parseInt(order.substring(1));
    }

}
