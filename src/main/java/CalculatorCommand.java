public interface CalculatorCommand {
    void setCalculatorStateMaintainer(CalculatorStateMaintainer maintainer);

    void setOperands(String... ops);

    String execute();

    void undo();

}
