package core.basesyntax;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {
    private static final String SUPPLY = "supply";
    private static final String BUY = "buy";
    private static final String RESULT = "result";
    private static final String SEPARATOR = ",";

    public void getStatistic(String fromFileName, String toFileName) {
        int totalSupply = calculate(fromFileName, SUPPLY);
        int totalBuy = calculate(fromFileName, BUY);
        int result = totalSupply - totalBuy;
        writeAccount(toFileName, totalSupply, totalBuy, result);
    }

    private int calculate(String fileName, String operationType) {
        int total = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                total += convertLine(line, operationType);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + fileName, e);
        }
        return total;
    }

    private int convertLine(String line, String operationType) {
        String[] parts = line.split(SEPARATOR);
        if (parts.length == 2) {
            String operation = parts[0];
            if (operation.equals(operationType)) {
                try {
                    return Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Wrong format " + line, e);
                }
            }
        }
        return 0;
    }

    private void writeAccount(String fileName, int supplyTotal, int buyTotal, int result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(SUPPLY + SEPARATOR + supplyTotal);
            writer.newLine();
            writer.write(BUY + SEPARATOR + buyTotal);
            writer.newLine();
            writer.write(RESULT + SEPARATOR + result);
        } catch (IOException e) {
            throw new RuntimeException("Can't save data in file " + fileName, e);
        }
    }
}


