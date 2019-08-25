import Operation.Operation;
import Parser.Parser;
import java.util.ArrayList;

public class Main {

    private static String path = "data\\movementList.csv";

    public static void main(String[] args) {

        ArrayList<Operation> staff = Parser.loadOperationsFromFile(path);

        staff.forEach(System.out::println);

    }

}
