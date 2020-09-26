import interpreter.Operand;
import parser.Parser;

public class InnLisp {
    public static void main(String[] args) {
        String code = "(+ (- 20 1 1 1 1 1 1 1 1 1 1) 5)";
        System.out.println("Testing interpreting '" + code + "'");

        Operand result = Parser.parse(code).interpret(null);

        System.out.println("Result: " + result.getValue());

    }
}
