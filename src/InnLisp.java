import interpreter.Operand;
import parser.Parser;

public class InnLisp {
    public static void main(String[] args) {
        //String code = "(+ \n\t(- 20 1 1 1 1 1 1 1 1 1 1) (* 5 3))";
        String code = "(/ 16 2 2)";

        System.out.println("\nTesting interpreting:\n\n" + code + "\n");

        Operand result = Parser.parse(code).interpret(null);

        System.out.println("Result: " + result.getValue());

    }
}
