package MiniJava.codeGenerator;

import MiniJava.scanner.token.Token;

public class CodeGeneratorFacade {
    private CodeGenerator cg;

    public CodeGeneratorFacade() {
        cg = new CodeGenerator();
    }

    public void printMemory() {
        cg.printMemory();
    }

    public void semanticFunction(int func, Token next) {
        cg.semanticFunction(func, next);
    }
}
