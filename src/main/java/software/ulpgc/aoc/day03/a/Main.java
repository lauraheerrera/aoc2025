package software.ulpgc.aoc.day03.a;

import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day03.io.BatteryBankDeserializer;
import software.ulpgc.aoc.day03.io.BatteryBankLoader;
import software.ulpgc.aoc.day03.io.TxtBatteryBankDeserializer;
import software.ulpgc.aoc.day03.model.BatteryBank;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = new File("src/main/resources/day03/input.txt");
        BatteryBankDeserializer deserializer = new TxtBatteryBankDeserializer();
        BatteryBankLoader loader = () -> LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();

        List<BatteryBank> batteryBanks = loader.load();
        for (BatteryBank batteryBank : batteryBanks) {
            System.out.println(batteryBank);
        }
    }
}