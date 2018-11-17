import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class DivideCommand extends AbstractCalculatorCommand {
    @Override
    protected String doExecute() {
        BigDecimal param1 = new BigDecimal(opArray[0]);
        BigDecimal param2 = new BigDecimal(opArray[1]);
        if (param2.compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("NaN");
        }

        BigDecimal ret = param1.divide(param2, AbstractCalculatorCommand.PRECISION, BigDecimal.ROUND_DOWN);
        return ret.toPlainString();
    }
}
