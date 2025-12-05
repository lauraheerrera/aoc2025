package software.ulpgc.aoc.day01.io;

import software.ulpgc.aoc.day01.model.Order;

import java.io.IOException;
import java.util.List;

public interface OrderLoader {
    List<Order> load() throws IOException;
}