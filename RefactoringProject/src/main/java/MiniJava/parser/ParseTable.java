package MiniJava.parser;

import MiniJava.errorHandler.ErrorHandler;
import MiniJava.scanner.token.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohammad hosein on 6/25/2015.
 */

public class ParseTable {
    private ArrayList<Map<Token, Action>> actionTable;
    private ArrayList<Map<NonTerminal, Integer>> gotoTable;

    public ParseTable(String jsonTable) throws Exception {
        // 1) split the raw JSON into an array of rows
        String[] rows = splitRows(jsonTable);

        // 2) parse header row into terminals & non-terminals
        Map<Integer, Token> terminals    = new HashMap<Integer, Token>();
        Map<Integer, NonTerminal> nonTerms = new HashMap<Integer, NonTerminal>();
        parseHeader(rows[0], terminals, nonTerms);

        // 3) create empty tables
        actionTable = new ArrayList<Map<Token, Action>>();
        gotoTable   = new ArrayList<Map<NonTerminal, Integer>>();

        // 4) fill tables row by row
        for (int i = 1; i < rows.length; i++) {
            parseRow(rows[i], i, terminals, nonTerms);
        }
    }

    private String[] splitRows(String raw) {
        String trimmed = raw.substring(2, raw.length() - 2);
        return trimmed.split("\\],\\[");
    }

    private void parseHeader(String headerRow,
                             Map<Integer, Token> terminals,
                             Map<Integer, NonTerminal> nonTerms)
    {
        String row = headerRow.substring(1, headerRow.length() - 1);
        String[] cols = row.split("\",\"");
        for (int j = 1; j < cols.length; j++) {
            if (cols[j].startsWith("Goto")) {
                String label = cols[j].substring(5);
                try {
                    nonTerms.put(j, NonTerminal.valueOf(label));
                } catch (Exception e) {
                    ErrorHandler.printError(e.getMessage());
                }
            } else {
                terminals.put(j,
                        new Token(Token.getTyepFormString(cols[j]), cols[j]));
            }
        }
    }

    private void parseRow(String rowData,
                          int rowIndex,
                          Map<Integer, Token> terminals,
                          Map<Integer, NonTerminal> nonTerms)
            throws Exception
    {
        if (rowIndex == 100) {
            int a = 1; a++;
        }

        String row = rowData.substring(1, rowData.length() - 1);
        String[] cols = row.split("\",\"");

        actionTable.add(new HashMap<Token, Action>());
        gotoTable.add(new HashMap<>());

        for (int j = 1; j < cols.length; j++) {
            String cell = cols[j];
            if (!cell.equals("")) {
                parseCell(cell, j, terminals, nonTerms);
            }
        }
    }

    private void parseCell(String cell,
                           int colIndex,
                           Map<Integer, Token> terminals,
                           Map<Integer, NonTerminal> nonTerms)
            throws Exception
    {
        if (cell.equals("acc")) {
            actionTable.get(actionTable.size() - 1)
                    .put(terminals.get(colIndex),
                            new Action(act.accept, 0));
        }
        else if (terminals.containsKey(colIndex)) {
            Token t = terminals.get(colIndex);
            Action a = new Action(
                    cell.charAt(0) == 'r' ? act.reduce : act.shift,
                    Integer.parseInt(cell.substring(1))
            );
            actionTable.get(actionTable.size() - 1).put(t, a);
        }
        else if (nonTerms.containsKey(colIndex)) {
            gotoTable.get(gotoTable.size() - 1)
                    .put(nonTerms.get(colIndex),
                            Integer.parseInt(cell));
        }
        else {
            throw new Exception();
        }
    }
    public int getGotoTable(int currentState, NonTerminal variable) {
//        try {
        return gotoTable.get(currentState).get(variable);
//        }catch (NullPointerException dd)
//        {
//            dd.printStackTrace();
//        }
//        return 0;
    }

    public Action getActionTable(int currentState, Token terminal) {
        return actionTable.get(currentState).get(terminal);
    }
}
