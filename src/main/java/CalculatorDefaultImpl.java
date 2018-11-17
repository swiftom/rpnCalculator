import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Predicate;

public class CalculatorDefaultImpl implements Calculator {
    //blank space as delimiter
    @Getter
    @Setter
    private String delimiter = " ";

    @Getter
    @Setter
    private int precisionForDisplay = 10;

    private boolean isRunning;

    //
    private CalculatorStateMaintainer maintainer;

    private Map<String, CalculationOperator> supportedOperators;

    @Getter
    @Setter
    private Predicate<String> operandPredicate;


    public CalculatorDefaultImpl() {
        this.supportedOperators = new HashMap<>();
        this.addOperator("+", 2, AddCommand.class);
        this.addOperator("-", 2, SubstractCommand.class);
        this.addOperator("*", 2, MultiplicationCmd.class);
        this.addOperator("/", 2, DivideCommand.class);
        this.addOperator("sqrt", 1, SqrtCommand.class);
        this.addOperator("undo", 0, UndoCommand.class);
        this.addOperator("clear", 0, ClearCommand.class);

        this.operandPredicate = s -> s.matches("^(\\-)?[0-9]+(.[0-9]+)?$");

        this.maintainer = new CalculaterMaintainerImpl();
    }

    public CalculatorDefaultImpl(CalculatorStateMaintainer maintainer, Predicate<String> predicate) {
        this.maintainer = maintainer;
        this.operandPredicate = predicate;

        this.supportedOperators = new HashMap<>();
        this.addOperator("+", 2, AddCommand.class);
        this.addOperator("-", 2, SubstractCommand.class);
        this.addOperator("*", 2, MultiplicationCmd.class);
        this.addOperator("/", 2, DivideCommand.class);
        this.addOperator("sqrt", 1, SqrtCommand.class);
        this.addOperator("undo", 0, UndoCommand.class);
        this.addOperator("clear", 0, ClearCommand.class);
    }

    @Override
    public void start() {
        assert (maintainer != null &&operandPredicate != null && supportedOperators != null && supportedOperators.size() > 0);
        isRunning = true;

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {

            while (isRunning) {
                String input = bufferedReader.readLine();
                execute(input);
            }
        } catch (IOException e) {
        } finally {
            try {
                bufferedReader.close();
                inputStreamReader.close();
                System.in.close();
            } catch (IOException e) {
            }
        }

    }

    @Override
    public void end() {
        isRunning = false;
    }

    @Override
    public void reset() {
        maintainer.clear();
        maintainer.clearAllCmds();
    }

    @Override
    public void execute(String input) {
        assert (maintainer != null && operandPredicate != null && supportedOperators != null && supportedOperators.size() > 0);

        List<OpAndPos> output = parseInput(input);

        for (OpAndPos opAndPos : output) {
            CalculatorCommand command = constructCommand(opAndPos);
            if (command == null) {
                break;
            }
            String result = command.execute();

            boolean isSuccess = handleCalcResut(command, result);
            if (!isSuccess) {
                break;
            }
        }

        displayCurrentState();
    }

    //for test
    String executeAndReturn(String input) {
        execute(input);

        String[] operands = maintainer.getAllOperands();

        StringBuffer sb = new StringBuffer();
        for (String op : operands) {
            sb.append(formatOperand(op));
            sb.append(" ");
        }

        return sb.toString().trim();
    }

    private CalculatorCommand constructCommand(OpAndPos opAndPos) {
        CalculatorCommand cmd;
        String opStr = opAndPos.getOpStr();

        if (opAndPos.isOperator()) {
            cmd = buildOperatorCommand(opStr);
            if (cmd == null) {
                System.out.println("operator: " + opStr + " is not supported.");
                return null;
            }

            int num = supportedOperators.get(opStr).getOperandsCount();
            if (num > 0 && maintainer.getOperandsSize() < num) {
                System.out.println("operator " + opStr + " (position:" + opAndPos.getPos() + "): insufficient parameters");
                return null;
            }

            if (num > 0) {
                String[] operands = new String[num];
                while (num-- > 0) {
                    operands[num] = maintainer.pop();
                }
                cmd.setOperands(operands);
            }

        } else {
            cmd = new OperandPushCommand(opStr);
            cmd.setCalculatorStateMaintainer(maintainer);
        }

        return cmd;
    }

    private void displayCurrentState() {
        String[] operands = maintainer.getAllOperands();

        StringBuffer sb = new StringBuffer();
        sb.append("stack:");
        for (String op : operands) {
            sb.append(formatOperand(op));
            sb.append(delimiter);
        }

        System.out.println(sb.toString());
    }

    protected String formatOperand(String operand) {
        BigDecimal operandBd = new BigDecimal(operand);
        if (operandBd.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }

        //remove tailing 0
        int dotPos = operand.indexOf(".");
        if (dotPos != -1 && operand.charAt(operand.length() - 1) == '0') {
            int len = operand.length() - 2;
            while (len > dotPos && operand.charAt(len) == '0') {
                len--;
            }
            if (operand.charAt(len) != '.') {
                len++;
            }
            operand = operand.substring(0, len);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("#.");
        for (int i = 0; i < precisionForDisplay; i++) {
            sb.append("#");
        }
        BigDecimal bd = new BigDecimal(operand);
        DecimalFormat decimalFormat = new DecimalFormat(sb.toString());
        String result = decimalFormat.format(bd);
        if (new BigDecimal(result).compareTo(BigDecimal.ZERO) == 0 && operandBd.compareTo(BigDecimal.ONE) < 0) {
            //don't loss precision
            return operand;
        }
        return result;
    }

    @Override
    public boolean addOperator(String sign, int count, Class<? extends CalculatorCommand> command) {
        if (sign == null || "".equals(sign.trim()) || count < 0 || command == null) {
            return false;
        }

        CalculationOperator calculationOperator = new CalculationOperator(sign, count, command);
        if (supportedOperators.containsKey(calculationOperator.getSign())) {
            return false;
        }

        supportedOperators.put(calculationOperator.getSign(), calculationOperator);
        return true;
    }

    @Data
    @AllArgsConstructor
    private static class OpAndPos {
        int pos;
        String opStr;
        boolean isOperator = false;
    }

    private List<OpAndPos> parseInput(String input) {
        if (input == null || "".equals(input.trim())) {
            return Collections.emptyList();
        }

        int i = 0;
        int len = input.length();
        List<OpAndPos> opAndPosList = new ArrayList<OpAndPos>();

        while (i < len) {
            if (String.valueOf(input.charAt(i)).equals(delimiter)) {
                i++;
                continue;
            } else {
                int startPos = i;
                while (++i < len && !(String.valueOf(input.charAt(i)).equals(delimiter))) {
                }

                String str = input.substring(startPos, i);
                boolean isOperator = supportedOperators.containsKey(str);
                if (isOperator || operandPredicate.test(str)) {
                    OpAndPos opAndPos = new OpAndPos(startPos + 1, str, isOperator);

                    opAndPosList.add(opAndPos);
                } else {
                    //illegal
                    System.out.println("illegal character found:" + str);
                    return Collections.emptyList();
                }

            }

        }
        return opAndPosList;
    }

    private boolean handleCalcResut(CalculatorCommand command, String result) {
        if (result != null) {
            if (result == AbstractCalculatorCommand.NaN_RESULT) {
                handlerNaN();
                System.out.println("Not a Number");
                return false;
            }
            //push calculation result
            maintainer.push(result);
        }

        //undo command can't undo
        if (command instanceof UndoCommand) {
            return true;
        }
        if (command instanceof ClearCommand) {
            //not push clearcommand for undo when nothing cleared
            ClearCommand clearCommand = (ClearCommand) command;
            if (clearCommand.getClearedOperands() != null && clearCommand.getClearedOperands().size() == 0) {
                return true;
            }
        }

        //push command for undo
        maintainer.pushCmd(command);

        return true;
    }

    private void handlerNaN() {
        //pop all previous commands
        maintainer.clearAllCmds();
        //re-push OperandPushCommands of left operands for undo
        String[] operands = maintainer.getAllOperands();
        if (operands.length > 0) {
            for (int i = 0; i < operands.length; i++) {
                CalculatorCommand cmd = new OperandPushCommand(maintainer, operands[i]);
                maintainer.pushCmd(cmd);
            }
        }

    }

    private CalculatorCommand buildOperatorCommand(String oper) {
        CalculationOperator calculationOperator = supportedOperators.get(oper);
        if (calculationOperator == null || calculationOperator.getCmdClass() == null) {
            return null;
        }

        Class<? extends CalculatorCommand> klass = calculationOperator.getCmdClass();
        if (klass != null) {
            try {
                CalculatorCommand newCmd = klass.newInstance();
                newCmd.setCalculatorStateMaintainer(maintainer);
                return newCmd;
            } catch (Exception e) {
            }
        }

        return null;
    }

    public static void main(String[] args) {
        CalculatorDefaultImpl impl = new CalculatorDefaultImpl();
        String op = "-1.0000000000000000022";
        String result = impl.formatOperand(op);
        System.out.println(op);
        System.out.println(result);

    }
}
