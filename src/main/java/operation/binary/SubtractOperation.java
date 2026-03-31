package operation.binary;

public class SubtractOperation extends AbstractBinaryOperation {
    @Override protected int compute(int a, int b, int maxGray) { return a - b; }
    @Override public String getName() { return "Subtração"; }
}