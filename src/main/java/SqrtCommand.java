import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@NoArgsConstructor
public class SqrtCommand extends AbstractCalculatorCommand {
    @Override
    protected String doExecute() {
        double param = Double.valueOf(opArray[0]);
        if (param < 0) {
            throw new RuntimeException("NaN");
        }
        double ret = Math.sqrt(param);
        BigDecimal result = BigDecimal.valueOf(ret).setScale(AbstractCalculatorCommand.PRECISION, BigDecimal.ROUND_DOWN);
        return result.toPlainString();
    }
}
