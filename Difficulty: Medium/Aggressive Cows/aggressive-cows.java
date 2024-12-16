//{ Driver Code Starts
// Initial Template for Java

import java.io.*;
import java.util.*;

class GFG {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine().trim());

        while (tc-- > 0) {

            String[] str = br.readLine().trim().split(" ");
            int[] a = new int[str.length];
            for (int i = 0; i < str.length; i++) {
                a[i] = Integer.parseInt(str[i]);
            }
            String[] nk = br.readLine().trim().split(" ");
            int k = Integer.parseInt(nk[0]);
            Solution sln = new Solution();
            int ans = sln.aggressiveCows(a, k);

            System.out.println(ans);
            System.out.println("~");
        }
    }
}
// } Driver Code Ends


// User function Template for Java
class Solution {
    // Method to check if it's possible to place k cows 
    // with a minimum distance of 'distance'
    public static boolean canPlaceCows(int[] stalls, int k, int distance) {
        // Place the first cow at the first stall
        int cowsPlaced = 1;
        int lastPlacedPosition = stalls[0];
        
        // Try to place remaining cows
        for (int i = 1; i < stalls.length; i++) {
            // If the current stall is far enough from the last placed cow
            if (stalls[i] - lastPlacedPosition >= distance) {
                // Place a cow here
                cowsPlaced++;
                lastPlacedPosition = stalls[i];
                
                // If we've placed all k cows, return true
                if (cowsPlaced == k) {
                    return true;
                }
            }
        }
        
        // Couldn't place all k cows
        return false;
    }
    
    // Main method to solve the aggressive cows problem
    public static int aggressiveCows(int[] stalls, int k) {
        // Sort the stalls in ascending order
        Arrays.sort(stalls);
        
        // Binary search for the maximum minimum distance
        int left = 1; // Minimum possible distance
        int right = stalls[stalls.length - 1] - stalls[0]; // Maximum possible distance
        int result = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // If we can place k cows with this minimum distance
            if (canPlaceCows(stalls, k, mid)) {
                // Try to maximize the distance
                result = mid;
                left = mid + 1;
            } else {
                // If we can't place cows, reduce the distance
                right = mid - 1;
            }
        }
        
        return result;
    }
}