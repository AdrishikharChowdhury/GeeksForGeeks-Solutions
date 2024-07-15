//{ Driver Code Starts
import java.util.*;

class GFG {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();

        while (tc-- > 0) {
            int s = sc.nextInt();
            int d = sc.nextInt();

            Solution obj = new Solution();
            String res = obj.smallestNumber(s, d);

            System.out.println(res);
        }
    }
}
// } Driver Code Ends



class Solution {
    static String smallestNumber(int S, int D) {
        // If sum S is 0, only possible if D is 1
        if (S == 0) {
            return (D == 1) ? "0" : "-1";
        }
        
        // If sum S is greater than 9*D, it's not possible
        if (S > 9*D) {
            return "-1";
        }
        
        StringBuilder result = new StringBuilder();
        
        // Subtract 1 from S to ensure we can put 1 at the start
        S -= 1;
        
        // Fill from right to left
        for (int i = D - 1; i > 0; i--) {
            if (S > 9) {
                result.insert(0, '9');
                S -= 9;
            } else {
                result.insert(0, (char)('0' + S));
                S = 0;
            }
        }
        
        // Add the remaining sum (plus the 1 we subtracted earlier) to the leftmost position
        result.insert(0, (char)('0' + S + 1));
        
        return result.toString();
    }
}