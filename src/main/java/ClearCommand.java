import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;

@NoArgsConstructor
@Data
public class ClearCommand extends AbstractCalculatorCommand {
    private Deque<String> clearedOperands = new ArrayDeque<String>();

    @Override
    public void undo() {
        int size = clearedOperands.size();
        while (size-- > 0) {
            calculatorStateMaintainer.push(clearedOperands.pop());
        }
    }

    @Override
    protected String doExecute() {
        int size = calculatorStateMaintainer.getOperandsSize();
        while (size-- > 0) {
            clearedOperands.push(calculatorStateMaintainer.pop());
        }
        return null;
    }
}
