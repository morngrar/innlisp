package interpreter;

enum OperandType {
    INTEGER,
}

public class Operand implements InnLispExpression {
    private int value;

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
}
