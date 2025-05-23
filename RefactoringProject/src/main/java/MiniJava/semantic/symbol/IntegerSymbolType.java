package MiniJava.semantic.symbol;

import MiniJava.codeGenerator.varType;

public class IntegerSymbolType extends SymbolType{
    @Override
    public varType getVarType() {
        return varType.Int;
    }
}
