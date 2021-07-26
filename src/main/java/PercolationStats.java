import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private final double[] thresholds;
    private final int T;

    private double mean = -1.;
    private double stddev = -1.;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        StdRandom.setSeed(42);

        T = trials;
        thresholds = new double[trials];

        for (int trial = 0; trial < trials; ++trial) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int []point = point(percolation);
                percolation.open(point[0], point[1]);
            }

            thresholds[trial] = percolation.numberOfOpenSites();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (mean == -1) {
            mean = StdStats.mean(thresholds);
        }

        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddev == -1) {
            stddev = StdStats.stddev(thresholds);
        }

        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - ((1.96 * stddev) / Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + ((1.96 * stddev) / Math.sqrt(T));
    }

    // method for generating random row or column value
    public int[] point(Percolation percolation) {
        int row = StdRandom.uniform(0, percolation.getN());
        int col = StdRandom.uniform(0, percolation.getN());

        if (percolation.isOpen(row, col)) {
            return point(percolation);
        }

        int []point = new int[2];
        point[0] = row;
        point[1] = col;

        return point;
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length != 2) throw new IllegalArgumentException();
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats pc = new PercolationStats(n, T);
        System.out.printf("mean = %f\n", pc.mean());
        System.out.printf("stddev = %f\n", pc.stddev());
        System.out.printf("95 percents confidence interval = [%f, %f]\n", pc.confidenceLo(), pc.confidenceHi());
    }
}
