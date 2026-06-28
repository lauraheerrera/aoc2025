package software.ulpgc.aoc.day11.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Network {

    private final Map<String, List<String>> adjacentList;

    private Network(Map<String, List<String>> adjacentList) {
        this.adjacentList = adjacentList;
    }

    public static Network from(List<Device> devices) {
        return new Network(toAdjMap(devices));
    }

    private static Map<String, List<String>> toAdjMap(List<Device> devices) {
        return devices.stream()
                .collect(Collectors.toMap(Device::name, Device::outputs));
    }

    public long countPaths(String start, String end) {
        return countPaths(start, end, new HashMap<>());
    }

    private long countPaths(String current, String end, Map<String, Long> memo) {
        return current.equals(end) ? 1L
                : !adjacentList.containsKey(current) ? 0L
                        : memo.containsKey(current) ? memo.get(current)
                                : computeAndMemoize(current, end, memo);
    }

    private long computeAndMemoize(String current, String end, Map<String, Long> memo) {
        memo.put(current, adjacentList.get(current).stream()
                .mapToLong(next -> countPaths(next, end, memo))
                .sum());
        return memo.get(current);
    }
}
