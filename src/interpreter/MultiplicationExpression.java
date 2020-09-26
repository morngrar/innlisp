package interpreter;

import java.util.Iterator;

public class MultiplicationExpression extends InnLispExpression {
    @Override
    public Operand interpret(Context ctx) {
        Iterator<InnLispExpression> childIterator = children.iterator();
        if (!childIterator.hasNext()) {
            throw new IllegalArgumentException("Invalid expression");
        }

        int result = childIterator.next().interpret(ctx).getValue();

        while (childIterator.hasNext()) {
            result *= childIterator.next().interpret(ctx).getValue();
        }

        return new Operand(result);
    }
}
