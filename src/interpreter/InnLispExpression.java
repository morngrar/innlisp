package interpreter;

import java.util.LinkedList;

public abstract class InnLispExpression {

    protected LinkedList<InnLispExpression> children = new LinkedList<>();

    public void add(InnLispExpression expression) {
        children.add(expression);
    }

    public abstract Operand interpret(Context ctx);
}
