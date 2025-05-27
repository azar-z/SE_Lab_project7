package MiniJava.semantic.symbol;

import MiniJava.codeGenerator.varType;

public class BooleanSymbolType extends SymbolType {
    @Override
    public varType getVarType() {
        return varType.Bool;
    }
}
