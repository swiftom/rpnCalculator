import java.util.*;

public class CalculatorTest {
    public static void main(String[] args) {
        Calculator calculator = new CalculatorDefaultImpl();

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
