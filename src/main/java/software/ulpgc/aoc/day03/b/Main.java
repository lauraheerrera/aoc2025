package software.ulpgc.aoc.day03.b;

import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day03.io.BatteryBankLoader;
import software.ulpgc.aoc.day03.io.TxtBatteryBankDeserializer;
import software.ulpgc.aoc.day03.model.BatteryBank;
import software.ulpgc.aoc.day03.model.BatteryBankMaxJoltageCalculator;
import software.ulpgc.aoc.day03.model.TotalBatteryJoltageCalculator;
import software.ulpgc.aoc.day03.model.Length;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = new File("src/main/resources/day03/input.txt");
        Deserializer<BatteryBank> deserializer = new TxtBatteryBankDeserializer();
        BatteryBankLoader loader = () -> LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();

        List<BatteryBank> batteryBanks = loader.load();
        BatteryBankMaxJoltageCalculator batteryMaxJoltageCalculator = new BatteryBankMaxJoltageCalculator();
        Length length = new Length(12);
        TotalBatteryJoltageCalculator totalCalculator = new TotalBatteryJoltageCalculator(batteryMaxJoltageCalculator,
                length);
        System.out.println(
                "El total de voltaje máximo es: " + totalCalculator.sumAllMaxJoltageFrom(batteryBanks));
    }
}