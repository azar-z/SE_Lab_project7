package MiniJava.codeGenerator;

import java.util.ArrayList;

/**
 * Created by mohammad hosein on 6/27/2015.
 */
public class Memory {
    private ArrayList<_3AddressCode> codeBlock;
    private int lastTempIndex;
    private int lastDataAddress;
    private final int stratTempMemoryAddress = 500;
    private final int stratDataMemoryAddress = 200;
    private final int dataSize = 4;
    private final int tempSize = 4;

    public Memory() {
        codeBlock = new ArrayList<_3AddressCode>();
        lastTempIndex = stratTempMemoryAddress;
        lastDataAddress = stratDataMemoryAddress;
    }

    // getters and setters for every field:
    public ArrayList<_3AddressCode> getCodeBlock() {
        return codeBlock;
    }

    public void setCodeBlock(ArrayList<_3AddressCode> codeBlock) {
        this.codeBlock = codeBlock;
    }

    public int getLastTempIndex() {
        return lastTempIndex;
    }

    public void setLastTempIndex(int lastTempIndex) {
        this.lastTempIndex = lastTempIndex;
    }

    public int getLastDataAddress() {
        return lastDataAddress;
    }

    public void setLastDataAddress(int lastDataAddress) {
        this.lastDataAddress = lastDataAddress;
    }

    public int getStartTempMemoryAddress() {
        return stratTempMemoryAddress;
    }

    public int getStartDataMemoryAddress() {
        return stratDataMemoryAddress;
    }

    public int getDataSize() {
        return dataSize;
    }

    public int getTempSize() {
        return tempSize;
    }

    public int getTemp() {
        setLastTempIndex(getLastTempIndex() + getTempSize());
        return getLastTempIndex() - getTempSize();
    }

    public int getDateAddress() {
        setLastDataAddress(getLastDataAddress() + getDataSize());
        return getLastDataAddress() - getDataSize();
    }

    public void saveMemory() {
        getCodeBlock().add(new _3AddressCode());
    }

    public int getMemorySize() {
        return getCodeBlock().size() - 1;
    }

    public void add3AddressCode(Operation op, Address opr1, Address opr2, Address opr3) {
        getCodeBlock().add(new _3AddressCode(op, opr1, opr2, opr3));
    }

    public void add3AddressCode(int i, Operation op, Address opr1, Address opr2, Address opr3) {
        getCodeBlock().remove(i);
        getCodeBlock().add(i, new _3AddressCode(op, opr1, opr2, opr3));
    }

    public int getCurrentCodeBlockAddress() {
        return getCodeBlock().size();
    }

    public void pintCodeBlock() {
        System.out.println("Code Block");
        for (int i = 0; i < getCodeBlock().size(); i++) {
            System.out.println(i + " : " + getCodeBlock().get(i).toString());
        }
    }
}

class _3AddressCode {
    public Operation operation;
    public Address Operand1;
    public Address Operand2;
    public Address Operand3;

    public _3AddressCode() {

    }

    public _3AddressCode(Operation op, Address opr1, Address opr2, Address opr3) {
        operation = op;
        Operand1 = opr1;
        Operand2 = opr2;
        Operand3 = opr3;
    }

    public String toString() {
        if (operation == null)
            return "";
        StringBuffer res = new StringBuffer("(");
        res.append(operation.toString()).append(",");
        if (Operand1 != null)
            res.append(Operand1.toString());
        res.append(",");
        if (Operand2 != null)
            res.append(Operand2.toString());
        res.append(",");
        if (Operand3 != null)
            res.append(Operand3.toString());
        res.append(")");

        return res.toString();
    }
}
