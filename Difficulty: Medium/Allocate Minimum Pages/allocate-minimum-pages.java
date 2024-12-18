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
            int ans = sln.findPages(a, k);

            System.out.println(ans);
            System.out.println("~");
        }
    }
}
// } Driver Code Ends



//Back-end complete function Template for Java

class Solution {
    // Function to check if current allocation is possible
    public static boolean isPossible(int arr[], int n, int m, int curMin) {
        int studentsRequired = 1;
        int curSum = 0;
        
        for (int i = 0; i < n; i++) {
            // If single book pages are more than current minimum, allocation is impossible
            if (arr[i] > curMin) return false;
            
            // If adding current book exceeds current minimum
            if (curSum + arr[i] > curMin) {
                // Assign to next student
                studentsRequired++;
                curSum = arr[i];
                
                // If students required exceeds available students
                if (studentsRequired > m) return false;
            } else {
                // Add pages to current student
                curSum += arr[i];
            }
        }
        return true;
    }
    
    // Modified method to take just two parameters
    public static int findPages(int[] arr, int k) {
        int n = arr.length;
        // If number of students is more than number of books, allocation impossible
        if (n < k) return -1;
        
        // Calculate total pages
        int sum = 0;
        for (int pages : arr) {
            sum += pages;
        }
        
        // Binary search range
        int start = 0;  // Minimum possible value
        int end = sum;  // Maximum possible value
        int result = -1;
        
        while (start <= end) {
            int mid = start + (end - start) / 2;
            
            // If current distribution is possible
            if (isPossible(arr, n, k, mid)) {
                result = mid;
                // Try to minimize further
                end = mid - 1;
            } else {
                // Need to increase maximum pages per student
                start = mid + 1;
            }
        }
        
        return result;
    }
}