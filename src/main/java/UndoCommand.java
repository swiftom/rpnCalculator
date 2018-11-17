import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UndoCommand extends AbstractCalculatorCommand {

    @Override
    public String doExecute() {
        calculatorStateMaintainer.undoLastCmd();
        return null;
    }

    @Override
    public void undo() {
        // do nothing
    }
}
