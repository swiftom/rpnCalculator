public interface Calculator{
    void start();

    void end();

    void reset();

    void execute(String input);

    boolean addOperator(CalculationOperator calculationOperator);
}
