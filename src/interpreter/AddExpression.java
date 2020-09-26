package interpreter;

import java.util.Iterator;

public class AddExpression extends InnLispExpression {
    @Override
    public Operand interpret(Context ctx) {
        Iterator<InnLispExpression> childIterator = children.iterator();
        int result = 0;
        while (childIterator.hasNext()) {
            result += childIterator.next().interpret(ctx).getValue();
        }

        return new Operand(result);
    }
}
