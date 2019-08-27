import Operation.Operation;
import Parser.Parser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Main {

    private static String path = "data\\movementList.csv";

    public static void main(String[] args) {

        /*OpenCSV parser*/
        ArrayList<Operation> staff = Parser.loadOperationsFromFile(path);
        System.out.println("OpenCSV парсер");
        System.out.println("Общее кол-во операций: " + staff.size());
        Double income = staff.stream().mapToDouble(Operation::getIncome).sum();
        System.out.println("Общий приход: " + income);
        Double expense = staff.stream().mapToDouble(Operation::getExpense).sum();
        System.out.println("Общий расход: " + expense);
        System.out.println("Расходы подробно: ");

        staff.stream()
                .filter(op->op.getExpense() > 0.0)
                .collect(Collectors.groupingBy(Parser::getDetails))
                .forEach((s, operations) ->
                    System.out.println(s + "  " + operations.stream().mapToDouble(Operation::getExpense).sum())
                );

        System.out.println("------------------------------------------------------------------------------------");

        /*My CSV parser*/
        ArrayList<Operation> manualStaff = Parser.manualLoadOperationsFromFile(path);
        System.out.println("Самописный парсер");
        System.out.println("Общее кол-во операций: " + manualStaff.size());
        income = manualStaff.stream().mapToDouble(Operation::getIncome).sum();
        System.out.println("Общий приход: " + income);
        expense = manualStaff.stream().mapToDouble(Operation::getExpense).sum();
        System.out.println("Общий расход: " + expense);
        manualStaff.stream()
                .filter(op->op.getExpense() > 0.0)
                .collect(Collectors.groupingBy(Parser::getDetails))
                .forEach((s, operations) ->
                        System.out.println(s + "  " + operations.stream().mapToDouble(Operation::getExpense).sum())
                );
    }

}
