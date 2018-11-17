public interface CalculatorStateMaintainer {
    //push operand
    void push(String op);

    //pop operand
    String pop();

    //push command
    void pushCmd(CalculatorCommand cmd);

    //pop command
    CalculatorCommand popCmd();

    void undoLastCmd();

    String[] getAllOperands();

    int getOperandsSize();

    //clear all operands
    void clear();

    void clearAllCmds();
}
