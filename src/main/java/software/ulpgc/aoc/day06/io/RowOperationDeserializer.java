package software.ulpgc.aoc.day06.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.model.Operation;
import software.ulpgc.aoc.day06.model.Operand;
import software.ulpgc.aoc.day06.model.Operator;

import java.util.List;
import java.util.regex.Pattern;

public class RowOperationDeserializer implements Deserializer<Operation> {

    @Override
    public Operation deserialize(String blockText) {
        List<String> lines = blockText.lines().toList();

        List<Operand> operands = lines.subList(0, lines.size() - 1).stream()
                .flatMap(line -> Pattern.compile("\\d+").matcher(line).results()
                        .map(mr -> new Operand(Long.parseLong(mr.group()))))
                .toList();

        Operator operator = Operator.from(lines.get(lines.size() - 1).trim().charAt(0));

        return new Operation(operands, operator);
    }
}