package interpreter;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class InnLispExpression {

    protected LinkedList<InnLispExpression> children = new LinkedList<>();

    public void add(InnLispExpression expression) {
        children.add(expression);
    }

    public void removeChildNo(int listIndex) {
        children.remove(listIndex);
    }

    public abstract Operand interpret(Context ctx);
}
