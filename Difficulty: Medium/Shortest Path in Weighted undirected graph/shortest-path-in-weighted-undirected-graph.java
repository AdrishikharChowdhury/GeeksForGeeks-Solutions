//{ Driver Code Starts
import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br;
    static PrintWriter ot;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        ot = new PrintWriter(System.out);

        int t = Integer.parseInt(br.readLine().trim());

        while (t-- > 0) {
            String s[] = br.readLine().trim().split(" ");
            int n = Integer.parseInt(s[0]), m = Integer.parseInt(s[1]);
            int edges[][] = new int[m][3];
            for (int i = 0; i < m; i++) {
                s = br.readLine().trim().split(" ");
                edges[i][0] = Integer.parseInt(s[0]);
                edges[i][1] = Integer.parseInt(s[1]);
                edges[i][2] = Integer.parseInt(s[2]);
            }

            List<Integer> list = new Solution().shortestPath(n, m, edges);

            ot.println(list.get(0));
            if (list.get(0) != -1 && !check(list, edges)) ot.println(-1);
        }
        ot.close();
    }

    static boolean check(List<Integer> list, int edges[][]) {
        Set<Integer> hs = new HashSet<>();
        Map<Integer, Map<Integer, Integer>> hm = new HashMap<>();
        for (int i = 1; i < list.size(); i++) hs.add(list.get(i));
        for (int x[] : edges) {
            if (hs.contains(x[0]) || hs.contains(x[1])) {
                if (!hm.containsKey(x[0])) hm.put(x[0], new HashMap<>());
                if (!hm.containsKey(x[1])) hm.put(x[1], new HashMap<>());
                hm.get(x[0]).put(x[1], x[2]);
                hm.get(x[1]).put(x[0], x[2]);
            }
        }
        int sum = 0;
        for (int i = 1; i < list.size() - 1; i++) {
            if (!hm.containsKey(list.get(i)) ||
                !hm.get(list.get(i)).containsKey(list.get(i + 1)))
                return false;
            sum += hm.get(list.get(i)).get(list.get(i + 1));
        }
        return sum == list.get(0);
    }
}

// } Driver Code Ends


class Solution {
    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    public static List<Integer> shortestPath(int n, int m, int[][] edges) {
        if (n == 2 && m == 0) {
            return Collections.singletonList(-1);
        }
        
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], w = edge[2];
            graph.get(a).add(new Edge(b, w));
            graph.get(b).add(new Edge(a, w));
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, 1});
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;
        int[] parent = new int[n + 1];
        Arrays.fill(parent, -1);
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentWeight = current[0], u = current[1];
            
            if (currentWeight > dist[u]) continue;
            
            for (Edge edge : graph.get(u)) {
                int v = edge.to, weight = edge.weight;
                int distance = currentWeight + weight;
                if (distance < dist[v]) {
                    dist[v] = distance;
                    parent[v] = u;
                    pq.offer(new int[]{distance, v});
                }
            }
        }
        
        if (dist[n] == Integer.MAX_VALUE) {
            return Collections.singletonList(-1);
        }
        
        List<Integer> path = new ArrayList<>();
        for (int at = n; at != -1; at = parent[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        path.add(0, dist[n]);
        
        return path;
    }

    public static void main(String[] args) {
        int n = 5, m = 6;
        int[][] edges = {
            {1, 2, 2},
            {2, 5, 5},
            {2, 3, 4},
            {1, 4, 1},
            {4, 3, 3},
            {3, 5, 1}
        };
        System.out.println(shortestPath(n, m, edges));  // Output: [5, 1, 4, 3, 5]

        n = 2;
        m = 1;
        edges = new int[][]{
            {1, 2, 2}
        };
        System.out.println(shortestPath(n, m, edges));  // Output: [2, 1, 2]

        n = 2;
        m = 0;
        edges = new int[0][0];
        System.out.println(shortestPath(n, m, edges));  // Output: [-1]
    }
}