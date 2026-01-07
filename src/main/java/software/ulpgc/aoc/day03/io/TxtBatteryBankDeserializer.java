package software.ulpgc.aoc.day03.io;

import software.ulpgc.aoc.day03.model.BatteryBank;

public class TxtBatteryBankDeserializer implements BatteryBankDeserializer {

    @Override
    public BatteryBank deserialize(String line) {
        return BatteryBank.create(line.trim());
    }
}