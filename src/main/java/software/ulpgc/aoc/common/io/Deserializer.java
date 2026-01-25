package software.ulpgc.aoc.common.io;

public interface Deserializer<T> {
    T deserialize(String line);
}
