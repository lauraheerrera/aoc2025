package software.ulpgc.aoc.day03.io;

import software.ulpgc.aoc.day03.model.BatteryBank;
import software.ulpgc.aoc.common.io.Deserializer;

public class TxtBatteryBankDeserializer implements Deserializer<BatteryBank> {

    @Override
    public BatteryBank deserialize(String line) {
        return BatteryBank.create(line.trim());
    }
}