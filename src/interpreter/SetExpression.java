package interpreter;

public class SetExpression extends InnLispExpression {
    @Override
    public Operand interpret(Context ctx) {

        if (children.size() != 2) {
            System.out.println(children.size());
            throw new IllegalArgumentException("Illegal variable set: no value given");
        }

        int value = children.get(1).interpret(ctx).getValue();

        try {
            VariableOperand var = (VariableOperand) children.get(0);
            try {
                ctx.valueOfVariable(var.getSymbol());

                // variable is already defined, proceed
                ctx.setVariable(var, value);

            } catch (NullPointerException e) {
                // if execution arrives here, variable doesn't exist. Throw exception
                throw new IllegalArgumentException("Trying to set non-existing variable");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Illegal variable set: " + e);
        }

        return new Operand(value);
    }
}
