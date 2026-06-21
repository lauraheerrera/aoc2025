package test.Day04.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day04.model.RollsCount;
import static org.assertj.core.api.Assertions.assertThat;

public class RollsCountTest {

    @Test
    public void should_return_correct_string_representation() {
        RollsCount rollsCount = new RollsCount(42);
        assertThat(rollsCount.toString()).isEqualTo("42");
    }
}
