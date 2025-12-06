package test.Day02.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day02.model.GiftShop;
import software.ulpgc.aoc.day02.a.model.Id;
import software.ulpgc.aoc.day02.model.IdRange;
import software.ulpgc.aoc.day02.io.TxtRangeDeserializer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IDTest {
    private static final String rangesText = """
            11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862
            """;

    TxtRangeDeserializer deserializer = new TxtRangeDeserializer();
    List<IdRange> ranges = deserializer.deserialize(rangesText);


    @Test
    public void should_count_digits_correctly() {
        assertThat(Id.create(0).getDigitCount()).isEqualTo(1);
        assertThat(Id.create(123).getDigitCount()).isEqualTo(3);
        assertThat(Id.create(1000).getDigitCount()).isEqualTo(4);
        assertThat(Id.create(-456).getDigitCount()).isEqualTo(3);
    }

    @Test
    public void should_validate_ids() {
        assertThat((Id.create(123).isInvalid())).isFalse();
        assertThat((Id.create(1234).isInvalid())).isFalse();
        assertThat((Id.create(1122).isInvalid())).isFalse();
        assertThat((Id.create(1212).isInvalid())).isTrue();
        assertThat((Id.create(446446).isInvalid())).isTrue();
    }

    @Test
    public void should_count_validate_ids_correctly() {
        GiftShop giftShop = new GiftShop(ranges);
        assertThat(giftShop.sumAllInvalidIds()).isEqualTo(1227775554);
    }
}
