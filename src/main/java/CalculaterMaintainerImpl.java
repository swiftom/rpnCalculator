import java.util.*;

public class CalculaterMaintainerImpl implements CalculatorStateMaintainer {
    private Deque<String> operandStack;

    private Deque<CalculatorCommand> cmdStack;

    public CalculaterMaintainerImpl() {
        cmdStack = new ArrayDeque<>();
        operandStack = new ArrayDeque<>();
    }


    @Override
    public void push(String op) {
        operandStack.push(op);
    }

    @Override
    public String pop() {
        return operandStack.pop();
    }

    @Override
    public void pushCmd(CalculatorCommand cmd) {
        cmdStack.push(cmd);
    }

    @Override
    public CalculatorCommand popCmd() {
        return cmdStack.pop();
    }

    @Override
    public void undoLastCmd() {
        if (cmdStack.size() > 0) {
            CalculatorCommand cmd = cmdStack.pop();
            cmd.undo();
        }
    }


    @Override
    public int getOperandsSize() {
        return operandStack.size();
    }

    @Override
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

    @Override
    public void clear() {
        operandStack.clear();
    }

    @Override
    public void clearAllCmds() {
        cmdStack.clear();
    }

}
