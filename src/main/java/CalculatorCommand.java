public interface CalculatorCommand {
    String execute();

    void undo();
}
