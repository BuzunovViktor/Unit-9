package Parser;

import Operation.Operation;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import au.com.bytecode.opencsv.CSVReader;

public class Parser {

    public static ArrayList<Operation> loadOperationsFromFile(String path) {
        ArrayList<Operation> operations = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(path), ',' , '"' , 1);
            List<String[]> lines = reader.readAll();
            for (String[] line : lines) {

                if (line.length != 8) {
                    System.out.println("Wrong line: " + line.toString());
                    continue;
                }
                try {
                    Operation operation = new Operation();
                    operation.setType(line[0]);
                    operation.setAccountNumber(line[1]);
                    operation.setCurrency(line[2]);
                    operation.setOperationDate(new SimpleDateFormat("dd.MM.yyyy").parse(line[3]));
                    operation.setTransactionReference(line[4]);
                    operation.setDescription(line[5]);
                    operation.setIncome(Double.parseDouble(line[6].replace(",",".")));
                    operation.setExpense(Double.parseDouble(line[7].replace(",",".")));
                    operations.add(operation);
                } catch (Exception e) {
                    System.out.println("Wrong line: " + line.toString());
                    continue;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return operations;
    }

}
