package interpreter;

import java.util.ArrayList;
import java.util.List;

abstract class Expression implements InnLispExpression{
    private List<InnLispExpression> children = new ArrayList<InnLispExpression>();

    public void add(InnLispExpression expression) {
        children.add(expression);
    }

    public void removeChildNo(int listIndex) {
        children.remove(listIndex);
    }

    @Override
    public abstract Operand interpret(Context ctx);
}
