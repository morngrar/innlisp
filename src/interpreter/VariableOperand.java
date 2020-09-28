package interpreter;


public class VariableOperand extends InnLispExpression {
    private final String symbol;

    public VariableOperand(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public Operand interpret(Context ctx) {
        return new Operand(ctx.valueOfVariable(symbol));
    }
}
