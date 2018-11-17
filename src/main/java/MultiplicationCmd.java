import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class MultiplicationCmd extends AbstractCalculatorCommand {
    @Override
    protected String doExecute() {
        BigDecimal param1 = new BigDecimal(opArray[0]);
        BigDecimal ret = param1.multiply(new BigDecimal(opArray[1])).setScale(AbstractCalculatorCommand.PRECISION, BigDecimal.ROUND_DOWN);
        return ret.toPlainString();
    }
}
