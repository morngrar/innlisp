package interpreter;

import java.util.HashMap;

public class Context {
    private final HashMap<String, Integer> symbols = new HashMap<>();
    public int valueOfVariable(String symbol) {
        return symbols.get(symbol);
    }
    public void setVariable(VariableOperand variable, int value) {
        symbols.put(variable.getSymbol(), value);
    }
}
