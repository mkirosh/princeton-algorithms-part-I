/* *****************************************************************************
 *  Name:    Monica L. Quiros
 *  NetID:   mquiros
 *  Precept: P00
 *
 *  Description:  Algorigthm for montecarlo simulation on percolation modeling.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int t; // Number of trials
    private final int n; // Grid size
    private int[] results; // Keeps track of the number of open sites for each trial

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.t = t;
        this.n = n;
        this.results = new int[this.t];
        simulate();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(this.t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(this.t));
    }

    // Executes all trials
    private void simulate() {
        for (int i = 0; i < t; i++) {
            results[i] = executeTrial();
        }
    }

    // Executes single trial
    private int executeTrial() {
        Percolation model = new Percolation(n);

        while (!model.percolates()) {
            model.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
        }

        return model.numberOfOpenSites();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        System.out.printf("mean\t\t\t\t\t= %f\n", stats.mean());
        System.out.printf("stddev\t\t\t\t\t= %f\n", stats.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]", stats.confidenceLo(), stats.confidenceHi());
    }
}
