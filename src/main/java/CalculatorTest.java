import java.util.*;
import java.util.function.Predicate;

public class CalculatorTest {
    public static void main(String[] args) {
        CalculatorStateMaintainer maintainer = new CalculaterMaintainerImpl();
        Predicate<String> normalPredicate = s -> s.matches("^(\\-)?[0-9]+(.[0-9]+)?$");
        Calculator calculator = new CalculatorDefaultImpl(maintainer, normalPredicate);

        calculator.addOperator(new CalculationOperator("+", 2, AddCommand.class));
        calculator.addOperator(new CalculationOperator("-", 2, SubstractCommand.class));
        calculator.addOperator(new CalculationOperator("*", 2, MultiplicationCmd.class));
        calculator.addOperator(new CalculationOperator("/", 2, DivideCommand.class));
        calculator.addOperator(new CalculationOperator("sqrt", 1, SqrtCommand.class));
        calculator.addOperator(new CalculationOperator("undo", 0, UndoCommand.class));
        calculator.addOperator(new CalculationOperator("clear", 0, ClearCommand.class));

        calculator.start();
    }

    static void test(Calculator calculator) {
        for (int c = 0; c < 10; c++) {
            List<Object> input = new ArrayList<>();
            String[] operators = {"+", "-", "*", "/", "sqrt", "clear", "undo", "undo"};

            Random random = new Random();
            List<Object> operandsStart = new ArrayList<>();
            operandsStart.add(random.nextInt());
            operandsStart.add(random.nextDouble());
            operandsStart.add(random.nextLong());
            Collections.shuffle(operandsStart);

            List<Object> operatorList = new ArrayList<>();
            int len = random.nextInt(8);
            if (len == 0) {
                len = 8;
            }
            for (int i = 0; i < len; i++) {
                operatorList.add(operators[random.nextInt(8)]);
            }


            List<Object> operandsEnd = new ArrayList<>();
            operandsEnd.add(random.nextInt());
            operandsEnd.add(random.nextDouble());
            operandsEnd.add(random.nextLong());
            operandsEnd.add(0.0);
            operandsEnd.add(Long.MAX_VALUE);
            operandsEnd.add(Long.MIN_VALUE);

            operatorList.addAll(operandsEnd);
            operatorList.addAll(operatorList);

            Collections.shuffle(operatorList);

            operandsStart.addAll(operatorList);

            System.out.println(String.valueOf(c + 1) + "=================================");

            StringBuffer sb = new StringBuffer();
            for (Object o : operandsStart) {
                sb.append(o.toString() + " ");
            }

            System.out.println(sb.toString());

            calculator.execute(sb.toString().trim());

            System.out.println();
            calculator.reset();
        }


    }
}
