import interpreter.Context;
import interpreter.Operand;
import iterator.StringIterator;
import parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InnLisp {
    public static void main(String[] args) throws IOException {
        /*String code = "(+ \n\t(- 20 1 1 1 1 1 1 1 1 1 1) (* 5 3))";
        //String code = "(/ 16 2 2)";

        System.out.println("\nTesting interpreting:\n\n" + code + "\n");

        Operand result = Parser.parse(code).interpret(null);

        System.out.println("Result: " + result.getValue());*/

        repl();
    }

    public static void repl() throws IOException {
        String tmp;
        Context ctx = new Context();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        StringBuilder expression;
        Operand result;
        StringIterator iterator;
        while (true) {
            try {
                expression = new StringBuilder();
                do {
                    System.out.print("> ");
                    tmp = reader.readLine();
                    if (tmp == null) System.exit(1);
                    if (tmp.equals("") || tmp.equals("q") || tmp.equals("quit")) break;
                    expression.append(tmp);
                    iterator = new StringIterator(expression.toString());
                } while (Parser.parensNotMatching(iterator));

                if (tmp.equals("q") || tmp.equals("quit")) break;
                result = Parser.parse(expression.toString()).interpret(ctx);
                System.out.println(": " + result.getValue() + "\n");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid expression: " + e);
            }
        }
    }
}
