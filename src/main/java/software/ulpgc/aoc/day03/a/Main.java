package software.ulpgc.aoc.day03.a;

import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.common.io.Deserializer;
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

        List<BatteryBank> batteryBanks = LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();
        Length length = new Length(2);
        BatteryBankMaxJoltageCalculator singleCalculator = BatteryBankMaxJoltageCalculator.of(length);
        TotalBatteryJoltageCalculator totalCalculator = new TotalBatteryJoltageCalculator(singleCalculator);
        System.out.println(
                "El total de voltaje máximo es: " + totalCalculator.sumAllMaxJoltageFrom(batteryBanks));
    }
}