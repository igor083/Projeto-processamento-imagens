package operation.impl.binary;

public class MultiplyOperation extends AbstractBinaryOperation {
    @Override protected int compute(int a, int b, int maxGray) { return (a * b) / Math.max(1, maxGray); }
    @Override public String getName() { return "Multiplicação"; }
}
