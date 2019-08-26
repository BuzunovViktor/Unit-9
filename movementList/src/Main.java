import Operation.Operation;
import Parser.Parser;
import java.util.ArrayList;

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
        staff.stream().filter(op->op.getExpense() > 0.0).forEach(operation -> {
            System.out.println(Parser.getDetails(operation) + " - сумма: " + operation.getExpense());
        });

        System.out.println("------------------------------------------------------------------------------------");

        /*My CSV parser*/
        ArrayList<Operation> manualStaff = Parser.manualLoadOperationsFromFile(path);
        System.out.println("Самописный парсер");
        System.out.println("Общее кол-во операций: " + manualStaff.size());
        income = manualStaff.stream().mapToDouble(Operation::getIncome).sum();
        System.out.println("Общий приход: " + income);
        expense = manualStaff.stream().mapToDouble(Operation::getExpense).sum();
        System.out.println("Общий расход: " + expense);
        manualStaff.stream().filter(op->op.getExpense() > 0.0).forEach(operation -> {
            System.out.println(Parser.getDetails(operation) + " - сумма: " + operation.getExpense());
        });
    }

}
