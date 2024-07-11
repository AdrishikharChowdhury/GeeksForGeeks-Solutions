//{ Driver Code Starts
import java.io.*;
import java.lang.*;
import java.util.*;

class GFG {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int n = sc.nextInt();
            int[][] grid = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    grid[i][j] = sc.nextInt();
                }
            }

            Solution obj = new Solution();
            int ans = obj.MaxConnection(grid);
            System.out.println(ans);
        }
    }
}

// } Driver Code Ends


class Solution {
    private int n;
    private int[][] grid;
    private int[] dx = {-1, 1, 0, 0};
    private int[] dy = {0, 0, -1, 1};
    private int[] parent;
    private int[] size;

    public int MaxConnection(int[][] grid) {
        this.n = grid.length;
        this.grid = grid;
        this.parent = new int[n * n];
        this.size = new int[n * n];
        
        // Initialize parent and size arrays
        for (int i = 0; i < n * n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
        
        // Union islands
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    for (int k = 0; k < 4; k++) {
                        int ni = i + dx[k];
                        int nj = j + dy[k];
                        if (isValid(ni, nj) && grid[ni][nj] == 1) {
                            union(i * n + j, ni * n + nj);
                        }
                    }
                }
            }
        }
        
        int maxSize = 0;
        boolean hasZero = false;
        
        // Try changing each 0 to 1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    hasZero = true;
                    Set<Integer> neighborIslands = new HashSet<>();
                    for (int k = 0; k < 4; k++) {
                        int ni = i + dx[k];
                        int nj = j + dy[k];
                        if (isValid(ni, nj) && grid[ni][nj] == 1) {
                            neighborIslands.add(find(ni * n + nj));
                        }
                    }
                    int totalSize = 1; // Start with 1 for the changed cell
                    for (int islandRoot : neighborIslands) {
                        totalSize += size[islandRoot];
                    }
                    maxSize = Math.max(maxSize, totalSize);
                }
            }
        }
        
        // If there's no 0 in the grid, return n*n
        return hasZero ? maxSize : n * n;
    }
    
    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    
    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
        }
    }
    
    private boolean isValid(int i, int j) {
        return i >= 0 && i < n && j >= 0 && j < n;
    }
}