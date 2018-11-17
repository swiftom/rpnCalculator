import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class AbstractCalculatorCommand implements CalculatorCommand {
    //math notation
    @Getter
    @Setter
    private String sign;

    //how many operands needed for the operator
    @Getter
    @Setter
    private int operandsCount;

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

    protected AbstractCalculatorCommand(CalculatorStateMaintainer cal, String sign, int count) {
        this.calculatorStateMaintainer = cal;
        this.sign = sign;
        this.operandsCount = count;
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

    @Override
    public void setOperands(String... ops) {
        opArray = ops;
    }

    @Override
    public void setCalculatorStateMaintainer(CalculatorStateMaintainer maintainer) {
        this.calculatorStateMaintainer = maintainer;
    }

    protected abstract String doExecute();
}
