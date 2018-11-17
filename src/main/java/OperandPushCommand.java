import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OperandPushCommand extends AbstractCalculatorCommand {
    public OperandPushCommand(CalculatorStateMaintainer cal, String... operands) {
        super(cal, operands);
    }
    public OperandPushCommand(String... operands) {
        super(operands);
    }

    @Override
    protected String doExecute() {
        calculatorStateMaintainer.push(opArray[0]);
        return null;
    }

    @Override
    public void undo() {
        calculatorStateMaintainer.pop();
    }
}
