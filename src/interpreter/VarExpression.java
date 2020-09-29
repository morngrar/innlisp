package interpreter;

public class VarExpression extends InnLispExpression {

    @Override
    public Operand interpret(Context ctx) {
        if (children.size() > 2 || children.size() < 1) {
            throw new IllegalArgumentException("Illegal variable definition");
        }

        int value = (children.size() == 2)
                ? children.get(1).interpret(ctx).getValue()
                : 0;
        try {
            VariableOperand var = (VariableOperand) children.get(0);
            try {
                ctx.valueOfVariable(var.getSymbol());

                // if execution arrives here, variable already exists. Throw exception
                throw new IllegalArgumentException("Trying to define existing variable");

            } catch (NullPointerException e) {
                // variable isn't already defined, proceed
                ctx.setVariable(var, value);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Illegal variable definition: " + e);
        }

        return new Operand(value);
    }
}
