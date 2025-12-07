package software.ulpgc.aoc.day03.io;

import software.ulpgc.aoc.day03.model.BatteryBank;

import java.io.IOException;
import java.util.List;

public interface BatteryBankLoader {
    List<BatteryBank> load() throws IOException;
}