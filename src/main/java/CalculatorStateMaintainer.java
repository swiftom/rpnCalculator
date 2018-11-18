import java.util.*;

public class CalculatorStateMaintainer {
    private Deque<String> operandStack;

    private Deque<CalculatorCommand> cmdStack;

    public CalculatorStateMaintainer() {
        cmdStack = new ArrayDeque<>();
        operandStack = new ArrayDeque<>();
    }


    public void push(String op) {
        operandStack.push(op);
    }

    public String pop() {
        return operandStack.pop();
    }

    public void pushCmd(CalculatorCommand cmd) {
        cmdStack.push(cmd);
    }

    public CalculatorCommand popCmd() {
        return cmdStack.pop();
    }

    public void undoLastCmd() {
        if (cmdStack.size() > 0) {
            CalculatorCommand cmd = cmdStack.pop();
            cmd.undo();
        }
    }


    public int getOperandsSize() {
        return operandStack.size();
    }

    public String[] getAllOperands() {
        int size = getOperandsSize();
        if (size > 0) {
            int i = 0;
            String[] leftOps = new String[size];
            Iterator<String> iterator = operandStack.descendingIterator();
            while (iterator.hasNext()) {
                leftOps[i++] = iterator.next();
            }

            return leftOps;
        }

        return new String[0];
    }

    public void clear() {
        operandStack.clear();
    }

    public void clearAllCmds() {
        cmdStack.clear();
    }

}
