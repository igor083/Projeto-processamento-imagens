package operation.binary;

public class DivideOperation extends AbstractBinaryOperation {
    @Override protected int compute(int a, int b, int maxGray) { return b == 0 ? maxGray : (a * maxGray) / b; }
    @Override public String getName() { return "Divisão"; }
}