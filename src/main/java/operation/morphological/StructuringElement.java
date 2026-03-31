package operation.morphological;

public class StructuringElement {
    private final int[][] kernel;
    private final int size;

    public StructuringElement() {
        this.size = 3;
        this.kernel = new int[][]{
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
    }

    public StructuringElement(int[][] kernel) {
        this.size = kernel.length;
        this.kernel = kernel;
    }

    public int getSize() {
        return size;
    }

    public int getValue(int row, int col) {
        return kernel[row][col];
    }

    public boolean isActive(int row, int col) {
        return kernel[row][col] == 1;
    }

    public int[][] getKernel() {
        return kernel;
    }
}