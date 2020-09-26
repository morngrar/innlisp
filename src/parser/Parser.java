package parser;

import interpreter.AddExpression;
import interpreter.InnLispExpression;
import interpreter.Operand;
import interpreter.SubtractExpression;
import iterator.StringIterator;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public static InnLispExpression parse(String code) {

        InnLispExpression expression = null;

        if (code.length() < 2) {
            throw new IllegalArgumentException("A complete expression must be passed");
        }

        StringIterator iterator = new StringIterator(code);

        if (iterator.next() != '(' && code.charAt(code.length()-1) != ')') {
            throw new IllegalArgumentException(
                    "InnLisp code must be enclosed in parentheses"
            );
        }

        char last = '\0';
        char next;
        while (iterator.hasNext()) {
            next = iterator.next();

            if (next == '+') {
                if (!validateCurrentChar(next, iterator.next())) { // should be space only
                    throw new IllegalArgumentException("Invalid expression");
                }
                expression = new AddExpression();
                while (iterator.hasNext()) {
                    expression.add(getNextToken(iterator));
                }
            }

            if (next == '-') {
                if (!validateCurrentChar(next, iterator.next())) { // should be space only
                    throw new IllegalArgumentException("Invalid expression");
                }
                expression = new SubtractExpression();
                while (iterator.hasNext()) {
                    expression.add(getNextToken(iterator));
                }
            }

            if (next == '(') {
                // accumulate subexpression and parse recursively
                String subexpression = extractSubExpression(iterator);

                if (expression == null) {
                    throw new IllegalArgumentException("Every expression must begin with an operator");
                }
                expression.add(parse(subexpression));
            }

        }

        return expression;
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
        while (next != ' ' && next != ')') {
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
        List<Character> spaceOnly = Arrays.asList('+', '-');
        List<Character> validChars = Arrays.asList(' ', '(', ')');
        if (spaceOnly.contains(last) && current != ' ') return false;

        // if a number preceded, only numbers or whitespace may follow
        if (Character.isDigit(last) &&
                (!Character.isDigit(current) || current == ' '))
        { //TODO variables change this
           return false;
        }

        if (Character.isDigit(current) || validChars.contains(current)) return true;

        return false;

    }
}
