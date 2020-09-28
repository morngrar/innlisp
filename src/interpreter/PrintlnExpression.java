package interpreter;

public class PrintlnExpression extends InnLispExpression {
    @Override
    public Operand interpret(Context ctx) {
        for (InnLispExpression child : children)
            System.out.println(child.interpret(ctx).getValue());
        return new Operand(0);
    }
}
