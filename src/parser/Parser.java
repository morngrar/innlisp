package parser;

import interpreter.*;
import iterator.StringIterator;

import java.util.Arrays;
import java.util.List;

public class Parser {
    private static final List<Character> whiteSpace = Arrays.asList(
            ' ', '\t', '\n', '\r'
    );


    public static InnLispExpression parse(String code) {

        InnLispExpression expression = null;

        if (code.length() < 2) {
            throw new IllegalArgumentException("A complete expression must be passed");
        }

        StringIterator iterator = new StringIterator(code);

        // checking if parentheses match up
        int cnt = 0;
        while (iterator.hasNext()) {
            char ch = iterator.next();
            if (ch == '(') cnt++;
            else if (ch == ')') cnt--;
        }
        if (cnt != 0) {
            throw new IllegalArgumentException("Number of parentheses must match");
        }
        iterator.reset();

        if (iterator.next() != '(' && code.charAt(code.length()-1) != ')') {
            throw new IllegalArgumentException(
                    "InnLisp code must be enclosed in parentheses"
            );
        }

        if (iterator.hasNext()) {
            char ch = iterator.next();
            switch (ch) {
                case '+' -> {
                    expression = new AddExpression();
                    parseArithmeticExpression(iterator, expression);
                }
                case '-' -> {
                    expression = new SubtractExpression();
                    parseArithmeticExpression(iterator, expression);
                }
                case '*' -> {
                    expression = new MultiplicationExpression();
                    parseArithmeticExpression(iterator, expression);
                }
                case '/' -> {
                    expression = new DivisionExpression();
                    parseArithmeticExpression(iterator, expression);
                }
                default -> {
                    if (!Character.isLetter(ch)) {
                        throw new IllegalArgumentException("Unrecognised operator");
                    }

                    // accumulate symbol
                    String symbol = "";
                    while (ch != ')' && !whiteSpace.contains(ch)) {
                        symbol += ch;
                        ch = iterator.next();
                    }

                    switch (symbol) {
                        case "set" -> {
                            //TODO
                        }
                        case "var" -> {
                            //TODO
                        }
                        default -> throw new IllegalArgumentException("Unknown symbol");
                    }

                }
            }
        }

        return expression;
    }

    private static void parseArithmeticExpression(
            StringIterator iterator, InnLispExpression expression
    ) {
        if (!whiteSpace.contains(iterator.next())) {
            throw new IllegalArgumentException("Invalid expression");
        }
        while (iterator.hasNext()) {
            expression.add(getNextToken(iterator));
        }
    }

    private static String extractSubExpression(StringIterator iterator) {
        int cnt = 1;
        StringBuilder subexpression = new StringBuilder("(");
        while (cnt > 0) {
            if (!iterator.hasNext()) {
                throw new IllegalArgumentException("Invalid expression");
            }

            char next = iterator.next();
            if (next == '(') cnt++;
            if (next == ')') cnt--;
            subexpression.append(next);
        }

        return subexpression.toString();
    }

    private static InnLispExpression getNextToken(StringIterator iterator) {
        String tmp = "";

        // the next operand is an expression
        char next = iterator.next();
        if (next == '(') {
            return parse(extractSubExpression(iterator));
        }

        // regular integer
        char last = '\0';
        while (!whiteSpace.contains(next) && next != ')') {
            if (validateCurrentChar(last, next)) {
                tmp += next;
            } else {
                throw new IllegalArgumentException("Invalid expression");
            }
            next = iterator.next();
        }

        int value;
        if (tmp.equals("")) {
            value = 0;
        } else {
            value = Integer.parseInt(tmp);
        }

        return new Operand(value);
    }

    private static boolean validateCurrentChar(char last, char current) {
        List<Character> validChars = Arrays.asList(
                ' ',
                '\t',
                '\n',
                '\r',
                '(',
                ')'
        );

        // if a number preceded, only numbers or whitespace may follow
        if (Character.isDigit(last) &&
                (!Character.isDigit(current) || whiteSpace.contains(current))
        ) {
           return false;
        }

        return Character.isDigit(current) || validChars.contains(current);
    }
}
