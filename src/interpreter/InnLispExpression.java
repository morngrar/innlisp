package interpreter;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;

public abstract class InnLispExpression {

    protected ArrayList<InnLispExpression> children = new ArrayList<InnLispExpression>();

    public void add(InnLispExpression expression) {
        children.add(expression);
    }

    public void removeChildNo(int listIndex) {
        children.remove(listIndex);
    }

    public abstract Operand interpret(Context ctx);
}
