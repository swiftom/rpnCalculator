import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class SubstractCommand extends AbstractCalculatorCommand {
    @Override
    protected String doExecute() {
        BigDecimal param1 = new BigDecimal(opArray[0]);
        BigDecimal ret = param1.subtract(new BigDecimal(opArray[1]));
        return ret.toPlainString();
    }
}
