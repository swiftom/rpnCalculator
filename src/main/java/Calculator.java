public interface Calculator{
    void start();

    void end();

    void reset();

    void execute(String input);

    boolean addOperator(String sign, int count, Class<? extends CalculatorCommand> command);
}
