import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculationOperator {
    //math notation
    private String sign;

    //how many operands needed for the operator
    private int operandsCount;

    private Class<? extends AbstractCalculatorCommand> cmdClass;

}
