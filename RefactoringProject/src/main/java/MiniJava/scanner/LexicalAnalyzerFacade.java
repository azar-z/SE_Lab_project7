package MiniJava.scanner;

import MiniJava.scanner.token.Token;

public class LexicalAnalyzerFacade {
    private final lexicalAnalyzer lexicalAnalyzer;

    public LexicalAnalyzerFacade(java.util.Scanner sc) {
        lexicalAnalyzer = new lexicalAnalyzer(sc);
    }

    public Token getNextToken() {
        return lexicalAnalyzer.getNextToken();
    }
}
