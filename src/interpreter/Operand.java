package interpreter;

public class Operand extends InnLispExpression {
    private final int value;

    public Operand(int value) {
        this.value = value;
    }

    public int getValue() {         // eases adding different types later
        return value;
    }

    @Override
    public Operand interpret(Context ctx) {
        return this;
    }

    @Override
    public void add(InnLispExpression expression) {
    }
}
