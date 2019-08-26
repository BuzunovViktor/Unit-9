package Parser;

import Operation.Operation;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static ArrayList<Operation> loadOperationsFromFile(String path) {
        ArrayList<Operation> operations = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(path), ',' , '"' , '\0', 1);
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
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return operations;
    }

    public static ArrayList<Operation> manualLoadOperationsFromFile(String path) {
        ArrayList<Operation> operations = new ArrayList<>();
        ArrayList<ArrayList<String>> parseResults = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            char separator = ',';
            char quote = '"';

            for (String line : lines) {
                boolean quotePhrase = false;
                boolean fragment = false;
                boolean firstFragment = true;
                int[] fragmentIndexes = null;
                int lineLength = line.length();
                for (int i = 0; i < lineLength; i++) {
                    int currentLine = lines.indexOf(line);
                    if (fragmentIndexes == null) {
                        fragmentIndexes = new int[]{i, i};
                        parseResults.add(currentLine, new ArrayList<>());
                    }
                    if (fragmentIndexes[0] == fragmentIndexes[1]) {
                        fragment = true;
                        if (line.charAt(i) == quote) {
                            fragmentIndexes[1] = i;
                            quotePhrase = !quotePhrase;
                            continue;
                        }
                    }
                    if (fragment) {
                        if (quotePhrase) {
                            if (line.charAt(i) == quote) {
                                fragment = false;
                                quotePhrase = false;
                                fragmentIndexes[0] = fragmentIndexes[1];
                                fragmentIndexes[1] = i + 1;
                                String quoteString = line.substring(fragmentIndexes[0], fragmentIndexes[1]);
                                quoteString = quoteString.replaceAll(String.valueOf(quote), "");
                                //System.out.println(quoteString);
                                parseResults.get(currentLine).add(quoteString);
                            }
                            continue;
                        } else {
                            if (line.charAt(i) == separator) {
                                if (firstFragment) {
                                    fragmentIndexes[0] = fragmentIndexes[1];
                                    firstFragment = false;
                                } else {
                                    fragmentIndexes[0] = fragmentIndexes[1] + 1;
                                }
                                fragmentIndexes[1] = i;
                                String string = line.substring(fragmentIndexes[0], fragmentIndexes[1]);
                                //System.out.println(string);
                                parseResults.get(currentLine).add(string);
                                fragmentIndexes[0] = fragmentIndexes[1];
                                fragment = false;
                            }
                            if (i == lineLength - 1) {
                                if (firstFragment) {
                                    fragmentIndexes[0] = fragmentIndexes[1];
                                    firstFragment = false;
                                } else {
                                    fragmentIndexes[0] = fragmentIndexes[1] + 1;
                                }
                                fragmentIndexes[1] = i + 1;
                                String string = line.substring(fragmentIndexes[0], fragmentIndexes[1]);
                                //System.out.println(string);
                                parseResults.get(currentLine).add(string);
                                fragmentIndexes[0] = fragmentIndexes[1];
                                fragment = false;
                            }
                        }
                    }
                }
            }

            for (ArrayList<String> result : parseResults) {
                if (result.size() != 8) {
                    System.out.println("Wrong line: " + result.toString());
                    continue;
                }
                try {
                    Operation operation = new Operation();
                    operation.setType(result.get(0));
                    operation.setAccountNumber(result.get(1));
                    operation.setCurrency(result.get(2));
                    operation.setOperationDate(new SimpleDateFormat("dd.MM.yyyy").parse(result.get(3)));
                    operation.setTransactionReference(result.get(4));
                    operation.setDescription(result.get(5));
                    operation.setIncome(Double.parseDouble(result.get(6).replace(",", ".")));
                    operation.setExpense(Double.parseDouble(result.get(7).replace(",", ".")));
                    operations.add(operation);
                } catch (Exception e) {
                    if (parseResults.indexOf(result) != 0) {
                        System.out.println("Wrong line: " + result.toString());
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return operations;
    }

    public static String getDetails(Operation operation) {
        String details = "";
        String[] fragments = operation.getDescription().split("\\s{4}",-1);
        int from, to;
        int lastIndexOfBackDash = fragments[1].lastIndexOf('\\' );
        int lastIndexOfDash = fragments[1].lastIndexOf('/' );
        int lastIndexOfRightAngle = fragments[1].lastIndexOf('>');
        from = lastIndexOfBackDash > lastIndexOfDash ? lastIndexOfBackDash + 1 : lastIndexOfDash + 1;
        to = lastIndexOfRightAngle > 0 ? lastIndexOfRightAngle : fragments[1].length();
        details = fragments[1].substring(from,to).trim();
        return details;
    }

}
