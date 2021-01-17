/* *****************************************************************************
 *  Name:    Monica L. Quiros
 *  NetID:   mquiros
 *  Precept: P00
 *
 *  Description:  Algorigthm for percolation modeling.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final boolean BLOCKED_SITE_STATE = false;
    private static final boolean OPEN_SITE_STATE = true;
    private static final int NO_SITE_VALUE = -1;

    private WeightedQuickUnionUF sites; // Tracks the union of the sites
    private boolean[] sitesState; // Tracks if sites are opened or closed
    private int[] topSites; // Tracks the indexes of the top sites
    private int[] bottomSites; // Tracks the indexes of the bottom sites

    private int gridSize; // Number of col/rows of the grid
    private int totalSites; // Total number of sites, including virtual sites.
    private int totalOpenSites; // Total number of opened sites
    private int virtualTopIndex; // Index of the top virtual node
    private int virtualBottomIndex; // Index of the bottom virtual node

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int gridSize) {
        if (gridSize <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }
        this.initializeValues(gridSize);
        this.initializeCollections();
        this.fillCollections();
        this.linkVirtualNodes();
    }

    // creates n-by-n grid, with all sites initially blocked
    public void open(int r, int c) {
        if (!isOpen(r, c)) {
            int[] adjacentSites = adjacentSites(r, c);
            int currentSite = adjacentSites[0];

            for (int i = 1; i < adjacentSites.length; i++) {
                int site = adjacentSites[i];
                if (site != NO_SITE_VALUE && !sites.connected(currentSite, site)) {
                    sites.union(currentSite, site);
                }
            }

            sitesState[currentSite] = OPEN_SITE_STATE;
            totalOpenSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int r, int c) {
        int idx = this.getCoordsPosition(r, c);
        return isOpen(idx);
    }

    private boolean isOpen(int idx) {
        return sitesState[idx];
    }

    // is the site (row, col) full?
    public boolean isFull(int r, int c) {
        int idx = this.getCoordsPosition(r, c);
        return isFull(idx);
    }

    private boolean isFull(int idx) {
        return isOpen(idx) && sites.connected(virtualTopIndex, idx);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return totalOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return sites.connected(virtualTopIndex, virtualBottomIndex);
    }

    // Initializes scalar variables for percolation
    private void initializeValues(int n) {
        gridSize = n;
        totalOpenSites = 0;
        totalSites = (n * n) + 2;
        virtualTopIndex = this.totalSites - 2;
        virtualBottomIndex = this.totalSites - 1;
    }

    // Initializes collections and structures
    private void initializeCollections() {
        sites = new WeightedQuickUnionUF(totalSites);
        sitesState = new boolean[totalSites];
        topSites = new int[gridSize];
        bottomSites = new int[gridSize];
    }

    // Fills initial values in collections
    private void fillCollections() {
        for (int i = 0; i < totalSites; i++) {
            sitesState[i] = BLOCKED_SITE_STATE;

            if (i < gridSize) { this.topSites[i] = i; }

            if ((i > virtualTopIndex - gridSize - 1) && i < virtualTopIndex) {
                this.bottomSites[i % gridSize] = i;
            }
        }
    }

    // Links virtual nodes with top and bottom sites
    private void linkVirtualNodes() {
        for (int i = 0; i < this.topSites.length; i++) {
            sites.union(virtualTopIndex, this.topSites[i]);
            sites.union(virtualBottomIndex, this.bottomSites[i]);
        }
    }

    // Returns the adjacent sites for a site.
    private int[] adjacentSites(int r, int c) {
        int[] adjacentSites = {
                this.getCoordsPosition(r, c),
                topSite(r, c),
                bottomSite(r, c),
                leftSite(r, c),
                rightSite(r, c)
        };
        return adjacentSites;
    }

    // Returns idx of left site if it's opened
    private int leftSite(int r, int c) {
        int prev_c = c - 1;
        if (prev_c > 0 && this.isOpen(r, prev_c)) {
            return getCoordsPosition(r, prev_c);
        } else {
            return NO_SITE_VALUE;
        }
    }

    // Returns idx of right site if it's opened
    private int rightSite(int r, int c) {
        int next_c = c + 1;
        if (next_c <= gridSize && this.isOpen(r, next_c)) {
            return getCoordsPosition(r, next_c);
        } else {
            return NO_SITE_VALUE;
        }
    }

    // Returns idx of top site if it's opened
    private int topSite(int r, int c) {
        int prev_r = r - 1;
        if (prev_r > 0 && this.isOpen(prev_r, c)) {
            return getCoordsPosition(prev_r, c);
        } else {
            return NO_SITE_VALUE;
        }
    }

    // Returns idx of bottom site if it's opened
    private int bottomSite(int r, int c) {
        int next_r = r + 1;
        if (next_r <= gridSize && this.isOpen(next_r, c)) {
            return getCoordsPosition(next_r, c);
        } else {
            return NO_SITE_VALUE;
        }
    }

    // Returns array position for coordenates
    private int getCoordsPosition(int r, int c) {
        if (r < 1 || r > gridSize || c < 1 || c > gridSize) {
            throw new IllegalArgumentException("Invalid range for coord");
        }

        int row = r - 1;
        int col = c - 1;

        return (row * gridSize) + col;
    }
}
