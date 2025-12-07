package software.ulpgc.aoc.day03.io;

import software.ulpgc.aoc.day03.model.BatteryBank;

public interface BatteryBankDeserializer {
    BatteryBank deserialize(String line);
}
