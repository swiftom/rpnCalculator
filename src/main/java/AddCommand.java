import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class AddCommand extends AbstractCalculatorCommand {

    @Override
    public String doExecute() {
        BigDecimal added = new BigDecimal(opArray[0]);
        BigDecimal ret = added.add(new BigDecimal(opArray[1]));
        return ret.toPlainString();
    }

}
