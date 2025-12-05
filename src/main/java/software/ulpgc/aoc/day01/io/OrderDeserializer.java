package software.ulpgc.aoc.day01.io;

import software.ulpgc.aoc.day01.model.Order;


public interface OrderDeserializer {
    Order deserialize(String line);
}