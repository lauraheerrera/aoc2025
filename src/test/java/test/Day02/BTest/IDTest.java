package test.Day02.BTest;

import org.junit.Test;
import software.ulpgc.aoc.day02.b.model.Id;
import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day02.io.TxtRangeDeserializer;
import software.ulpgc.aoc.day02.model.GiftShop;
import software.ulpgc.aoc.day02.model.IdRange;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IDTest {

    private static final String rangesText = """
            11-22,95-115,998-1012,1188511880-1188511890,
            222220-222224,1698522-1698528,446443-446449,
            38593856-38593862,565653-565659,824824821-824824827,
            2121212118-2121212124
            """;

    Deserializer<IdRange<software.ulpgc.aoc.day02.b.model.Id>> deserializer =
            new TxtRangeDeserializer<>(software.ulpgc.aoc.day02.b.model.Id::create);

    List<IdRange<software.ulpgc.aoc.day02.b.model.Id>> ranges =
            Arrays.stream(rangesText.split(","))
                    .map(String::trim)
                    .map(deserializer::deserialize)
                    .toList();

    @Test
    public void should_count_digits_correctly() {

        // Given several numeric IDs

        // When creating IDs
        Id id1 = Id.create(0);
        Id id2 = Id.create(123);
        Id id3 = Id.create(1000);
        Id id4 = Id.create(-456);

        // Then digit count should be correct
        assertThat(id1.getDigitCount()).isEqualTo(1);
        assertThat(id2.getDigitCount()).isEqualTo(3);
        assertThat(id3.getDigitCount()).isEqualTo(4);
        assertThat(id4.getDigitCount()).isEqualTo(3);
    }

    @Test
    public void should_validate_ids_against_rules() {

        // Given a set of IDs

        // When validating them
        Id valid1 = Id.create(123);
        Id valid2 = Id.create(1234);

        Id invalid1 = Id.create(1212);
        Id invalid2 = Id.create(123123);
        Id invalid3 = Id.create(111);
        Id invalid4 = Id.create(121212);
        Id invalid5 = Id.create(122122122);

        // Then only valid ones should pass validation
        assertThat(valid1.isInvalid()).isFalse();
        assertThat(valid2.isInvalid()).isFalse();

        assertThat(invalid1.isInvalid()).isTrue();
        assertThat(invalid2.isInvalid()).isTrue();
        assertThat(invalid3.isInvalid()).isTrue();
        assertThat(invalid4.isInvalid()).isTrue();
        assertThat(invalid5.isInvalid()).isTrue();
    }

    @Test
    public void should_compute_total_invalid_ids_sum() {

        // Given a gift shop with ID ranges
        GiftShop<software.ulpgc.aoc.day02.b.model.Id> giftShop =
                new GiftShop<>(ranges);

        // When calculating sum of invalid IDs
        long result = giftShop.sumAllInvalidIds();

        // Then the result should match expected value
        assertThat(result).isEqualTo(4174379265L);
    }
}