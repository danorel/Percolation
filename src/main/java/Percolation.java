import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.QuickUnionUF;

public class Percolation {
    private final int n;

    private final double[][] grid;
    private final QuickUnionUF uf;

    private enum PercolationStatus {
        CLOSE,
        OPEN
    };

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        uf = new QuickUnionUF(n);

        grid = new double[n][];
        for (int row = 0; row < n; ++row) {
            grid[row] = new double[n];
            for (int col = 0; col < n; ++col) {
                grid[row][col] = PercolationStatus.CLOSE.ordinal();
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateBounds(row, col);

        grid[row][col] = PercolationStatus.OPEN.ordinal();

        int target = coordinates(row, col);
        int neighbour;

        if (row - 1 >= 0 && grid[row - 1][col] == PercolationStatus.OPEN.ordinal()) {
            neighbour = coordinates(row - 1, col);
            uf.union(target, neighbour);
        }

        if (row + 1 < n && grid[row + 1][col] == PercolationStatus.OPEN.ordinal()) {
            neighbour = coordinates(row + 1, col);
            uf.union(target, neighbour);
        }

        if (col - 1 >= 0 && grid[row][col - 1] == PercolationStatus.OPEN.ordinal()) {
            neighbour = coordinates(row, col - 1);
            uf.union(target, neighbour);
        }

        if (col + 1 < n && grid[row][col + 1] == PercolationStatus.OPEN.ordinal()) {
            neighbour = coordinates(row, col + 1);
            uf.union(target, neighbour);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateBounds(row, col);
        return grid[row][col] == PercolationStatus.OPEN.ordinal();
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return -1;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    // get n of percolation system
    public int getN() { return n; }

    // get index for point in array, set by (row, col) structure
    private int coordinates(int row, int col) {
        return n * row + col;
    }

    private void validateBounds(int row, int col) {
        if (
                (row < 0 || row >= n) ||
                (col < 0 || col >= n)
        ) throw new IllegalArgumentException();
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
