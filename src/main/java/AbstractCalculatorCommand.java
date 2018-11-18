import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractCalculatorCommand implements CalculatorCommand {
    protected CalculatorStateMaintainer calculatorStateMaintainer;

    //all operands for calculation
    protected String[] opArray;

    //calculation result
    /*
    1,null: not generate result for special operators
    2,not null
    2.a: normal result: xx.xx
    2.b: NaN:not a number
    * */
    protected String result;

    //precision for result
    public static final int PRECISION = 18;

    public static final String NaN_RESULT = "NaN";

    protected AbstractCalculatorCommand(CalculatorStateMaintainer cal) {
        this.calculatorStateMaintainer = cal;
    }

    protected AbstractCalculatorCommand(CalculatorStateMaintainer cal, String... opArray) {
        this.calculatorStateMaintainer = cal;
        this.opArray = opArray;
    }

    protected AbstractCalculatorCommand(String... opArray) {
        this.opArray = opArray;
    }

    @Override
    public String execute() {
        String result;
        try {
            //do calculation
            result = doExecute();
        } catch (Exception e) {
            result = NaN_RESULT;
        }

        this.result = result;

        return result;
    }

    @Override
    public void undo() {
        //pop result
        if (result != null) {
            calculatorStateMaintainer.pop();
        }

        if (opArray != null) {
            //re-push operand
            for (String op : opArray) {
                calculatorStateMaintainer.push(op);
            }
        }

    }

    protected abstract String doExecute();
}
