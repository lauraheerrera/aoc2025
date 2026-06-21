package software.ulpgc.aoc.day03.model;

public record Length(int value) {
    public Length {
        if (value <= 0) {
            throw new IllegalArgumentException("La longitud debe ser mayor que cero");
        }
    }
}
