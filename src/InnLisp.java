import interpreter.Operand;
import parser.Parser;

public class InnLisp {
    public static void main(String[] args) {
        String code = "(+ 1 1 1 1 1 1 1 1 1 1)";
        System.out.println("Testing interpreting '" + code + "'");

        Operand result = Parser.parse(code).interpret(null);

        System.out.println("Result: " + result.getValue());

    }
}
