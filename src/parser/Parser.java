package parser;

import interpreter.*;
import iterator.StringIterator;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class Parser {
    private static final List<Character> whiteSpace = Arrays.asList(
            ' ', '\t', '\n', '\r'
    );

    public static boolean parensMatch(StringIterator iterator) {
        int cnt = 0;
        while (iterator.hasNext()) {
            char ch = iterator.next();
            if (ch == '(') cnt++;
            else if (ch == ')') cnt--;
        }
        return cnt == 0;
    }

    public static InnLispExpression parse(String code) {

        InnLispExpression expression = null;

        if (code.length() < 2) {
            throw new IllegalArgumentException("A complete expression must be passed");
        }

        StringIterator iterator = new StringIterator(code);

        // checking if parentheses match up
        if (!parensMatch(iterator)) {
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

                    String symbol = accumulateSymbol(ch, iterator);
                    switch (symbol) {
                        case "set" -> {
                            expression = new SetExpression();
                            expression.add(getNextToken(iterator));
                            try {
                                expression.add(getNextToken(iterator));
                            } catch (NoSuchElementException ignored){ } // there is given no value
                        }
                        case "var" -> {
                            expression = new VarExpression();
                            expression.add(getNextToken(iterator));
                            try {
                                expression.add(getNextToken(iterator));
                            } catch (NoSuchElementException ignored){ } // there is given no value
                        }
                        case "println" -> {
                            expression = new PrintlnExpression();
                            InnLispExpression toPrint = getNextToken(iterator);
                            if (toPrint == null) throw new IllegalArgumentException("Nothing to print");
                            do {
                                expression.add(toPrint);
                                toPrint = getNextToken(iterator);
                            } while (toPrint != null);
                        }
                        default -> throw new IllegalArgumentException("Unknown symbol");
                    }
                }
            }
        }

        return expression;
    }

    private static String accumulateSymbol(char ch, StringIterator iterator) {
        StringBuilder symbol = new StringBuilder();
        while (ch != ')' && !whiteSpace.contains(ch)) {
            symbol.append(ch);
            ch = iterator.next();
        }
        return symbol.toString();
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

        // Handling end of string
        char next;
        try {
            next = iterator.next();
        } catch (NoSuchElementException ignored) {
            return null;
        }

        // skip whitespace
        if (whiteSpace.contains(next))
            while(whiteSpace.contains(next)) next = iterator.next();

        // the next operand is an expression
        if (next == '(') {
            return parse(extractSubExpression(iterator));
        }

        // variable symbol
        if (Character.isLetter(next)) {
            String symbol = accumulateSymbol(next, iterator);
            return new VariableOperand(symbol);
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
            return null;
        } else {
            value = Integer.parseInt(tmp);
        }

        return new Operand(value);
    }

    private static boolean validateCurrentChar(char last, char current) {
        List<Character> validChars = Arrays.asList(
                '(',
                ')'
        );

        // if a number preceded, only numbers or whitespace may follow
        if (Character.isDigit(last) &&
                (!Character.isDigit(current) || whiteSpace.contains(current))
        ) {
           return false;
        }

        // a number shouldn't be directly followed by anything not valid
        return Character.isDigit(current) || validChars.contains(current);
    }
}
