package kg.my_algorithms;
import java.math.BigInteger;
import java.util.*;

public class Graph {
    private static int centroidDFS(List<List<Integer>> tree, int node, int parent, int[] dp){
        int sum_of_sizes_of_subtrees=0;
        for(int child: tree.get(node)){
            if(child == parent) continue;
            int subtree_size = centroidDFS(tree,child,node,dp);
            dp[node] = Math.max(subtree_size,dp[node]);
            sum_of_sizes_of_subtrees += subtree_size;
        }
        dp[node] = Math.max(dp[node],tree.size()-sum_of_sizes_of_subtrees-1);
        return sum_of_sizes_of_subtrees+1;
    }
    private static int getCentroid(int[] dp){
        int n = dp.length;
        for(int i=0;i<n;i++) if(dp[i]<=n/2) return i;
        return -1;
    }
}
class Kosaraju{
    List<List<Integer>> graph,reverse_graph;
    int ssc=0,n;
    int[] component_number;
    int[] dfs_order;
    int dfs_index=0;
    boolean[] visited1,visited2;
    //Zero based nodes
    public Kosaraju(int n, int[][] directed_edges){
        this.n = n;
        graph = new ArrayList<>();
        reverse_graph = new ArrayList<>();
        for(int i=0;i<n;i++){
            graph.add(new ArrayList<>());
            reverse_graph.add(new ArrayList<>());
        }
        component_number = new int[n];
        dfs_order = new int[n];
        visited1 = new boolean[n];
        visited2 = new boolean[n];
        for(int[] edge: directed_edges){
            graph.get(edge[0]).add(edge[1]);
            reverse_graph.get(edge[1]).add(edge[0]);
        }
        for(int i=0;i<n;i++){
            if(!visited1[i]) dfs1(i);
        }
        for(int i=n-1;i>=0;i--){
            int node = dfs_order[i];
            if(!visited2[node]){
                ssc++;
                dfs2(node);
            }
        }
    }
    private void dfs1(int node){
        visited1[node] = true;
        for(int child: graph.get(node)){
            if(visited1[child]) continue;
            dfs1(child);
        }
        dfs_order[dfs_index++] = node;
    }
    private void dfs2(int node){
        visited2[node] = true;
        for(int child: reverse_graph.get(node)){
            if(visited2[child]) continue;
            dfs2(child);
        }
        component_number[node] = ssc;
    }
    public int numberOfStronglyConnectedComponents(){
        return ssc;
    }
    public List<List<Integer>> allStronglyConnectedComponents(){
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0;i<ssc;i++) res.add(new ArrayList<>());
        for(int i=0;i<n;i++){
            res.get(component_number[i]-1).add(i);
        }
        return res;
    }
}
class MapUnion{
    private Map<Integer,Integer> map;
    public MapUnion(){
        map = new HashMap<>();
    }
    public int find(int v){
        map.putIfAbsent(v,v);
        if(v != map.get(v)) map.put(v,find(map.get(v)));
        return map.get(v);
    }
    public void union(int x, int y){
        int rootX = find(x);
        int rootY = find(y);
        if(rootX != rootY) map.put(rootX,rootY);
    }
}
class UnionFind{
    int[] rank;
    int[] root;

    public UnionFind(int n){
        this.root = new int[n];
        this.rank = new int[n];

        for(int i=0;i<n;i++){
            root[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int v){
        if(v == root[v]) return v;
        return root[v] = find(root[v]);
    }

    public boolean isConnected(int x, int y){
        return find(x) == find(y);
    }

    public boolean union(int x, int y){
        int rootX = find(x);
        int rootY = find(y);
        if(rootX == rootY) return false;
        if(rank[rootX] > rank[rootY]) root[rootY] = rootX;
        else if(rank[rootY] > rank[rootX]) root[rootX] = rootY;
        else{
            rank[rootX]++;
            root[rootY] = rootX;
        }
        return true;
    }
    public boolean onlyOneRoot(){
        int root1 = find(0);
        for(int i=1;i<root.length;i++) if(root1!=find(i)) return false;
        return true;
    }
}
class WeightedUnionFind{
    int n;
    int[] root;
    long[] weight;

    public WeightedUnionFind(int n) {
        this.n = n;
        this.root = new int[n];
        this.weight = new long[n];
        for(int i=0;i<n;i++) root[i] = i;
    }
    public int find(int i){
        if(root[i] == i) return i;
        int r = find(root[i]);
        weight[i] = weight[root[i]] + weight[i];
        return root[i] = r;
    }
    public boolean union(int i, int j, long x){
        int root_i = find(i), root_j = find(j);
        if(root_i == root_j) return weight[j]-weight[i] == x;
        root[root_i] = root_j;
        weight[root_i] = weight[j]-weight[i]-x;
        return true;
    }
}

class myPair {
    int minimumElement;
    int index;

    public myPair(int minimumElement, int index) {
        this.minimumElement = minimumElement;
        this.index = index;
    }
    public myPair(myPair p){
        this.minimumElement = p.minimumElement;
        this.index = p.index;
    }
}
class FordFulkerson{
    public long getMaximumFlow(long[][] capacities, int source, int sink){
        long[][] residualCapacities = new long[capacities.length][capacities[0].length];
        for(int i=0;i<capacities.length;i++) for(int j=0;j<capacities[0].length;j++) residualCapacities[i][j] = capacities[i][j];
        long maxFlow = 0L;
        Map<Integer,Integer> parent = new HashMap<>();
        while(BFS(parent,source,sink,residualCapacities)){
            long flow = Integer.MAX_VALUE;
            int v = sink;
            while(v != source){
                int u = parent.get(v);
                if(residualCapacities[u][v]<flow) flow = residualCapacities[u][v];
                v = u;
            }
            maxFlow += flow;
            v = sink;
            while(v!=source){
                int u = parent.get(v);
                residualCapacities[u][v] -= flow;
                residualCapacities[v][u] += flow;
                v = u;
            }
        }
        return maxFlow;
    }
    private boolean BFS(Map<Integer,Integer> parent, int source, int sink,long[][] residualCapacities){
        Set<Integer> set = new HashSet<>();
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(source);
        set.add(source);
        boolean found_path = false;
        while(!queue.isEmpty()){
            int u = queue.remove();
            for(int v=0;v<residualCapacities.length;v++){
                if(!set.contains(v) && residualCapacities[u][v]!=0){
                    parent.put(v,u);
                    set.add(v);
                    queue.add(v);
                    if(v == sink){
                        found_path = true;
                        break;
                    }
                }
            }
        }
        return found_path;
    }
}
class CentroidDecomposition{
    List<List<Integer>> originalTree;
    List<List<Integer>> centroidTree;
    int[] subtree_sizes;
    boolean[] is_centroid;
    int mainCentroid = -1;
    int n;
    public CentroidAndCentroidTree getCentroidAndCentroidTree(List<List<Integer>> originalTree){
        this.originalTree = originalTree;
        this.n = originalTree.size();
        this.is_centroid = new boolean[n];
        this.subtree_sizes = new int[n];
        this.centroidTree = new ArrayList<>();
        for(int i=0;i<n;i++) centroidTree.add(new ArrayList<>());
        formCentroidTree(0,-1);
        //       System.out.println("cnt 2= " + centroidTree);
        return new CentroidAndCentroidTree(mainCentroid,centroidTree);
    }
    private int dfsForSubtreeSizes(int node, int parent){
        int size = 1;
        for(int child: originalTree.get(node)){
            if(child != parent && !is_centroid[child]) size += dfsForSubtreeSizes(child,node);
        }
        return subtree_sizes[node] = size;
    }
    private int getCentroid(int node){
        dfsForSubtreeSizes(node,-1);
        return dfsForFindingCentroid(node,subtree_sizes[node],-1);
    }
    private int dfsForFindingCentroid(int node, int size,int parent){
        for(int child: originalTree.get(node)){
            if(child!= parent && !is_centroid[child] && subtree_sizes[child]>size/2 ) return dfsForFindingCentroid(child,size,node);
        }
        return node;
    }
    private void formCentroidTree(int node, int parent){
        int centroid = getCentroid(node);
        is_centroid[centroid] = true;
        if(parent != -1){
            centroidTree.get(parent).add(centroid);
            centroidTree.get(centroid).add(parent);
        }
        else mainCentroid = centroid;
        for(int child: originalTree.get(centroid)){
            if(!is_centroid[child]) formCentroidTree(child,centroid);
        }
    }
}
class CentroidAndCentroidTree{
    int centroid;
    List<List<Integer>> centroidTree;

    public CentroidAndCentroidTree(int centroid, List<List<Integer>> centroidTree) {
        this.centroid = centroid;
        this.centroidTree = centroidTree;
    }
}
// https://codeforces.com/blog/entry/79880?#comment-658132   and    https://codeforces.com/blog/entry/93801?#comment-1060852
class NumberOfConnectedComponentsInComplementGraph{
    public static int find(List<List<Integer>> graph){
        int n = graph.size();
        int[] degree = new int[n];
        for (List<Integer> list : graph) {
            for(int u : list) degree[u]++;
        }
        int min_degree = n+1,node=-1;
        for(int i=0;i<n;i++) {
            if (degree[i] < min_degree) {
                min_degree = degree[i];
                node = i;
            }
        }
        boolean[] big_node = new boolean[n];
        int bn = n;
        for(int i=0;i<n;i++) big_node[i] = true;
        for(int u: graph.get(node)) {
            big_node[u] = false;
            bn--;
        }
        int nodes_in_complement_graph = degree[node]+1;
        List<List<Integer>> complement_graph = new ArrayList<>();
        for(int i=0;i<nodes_in_complement_graph;i++) complement_graph.add(new ArrayList<>());
        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(node,0);

        for(int u: graph.get(node)) map.put(u,map.size());
        for(int u: graph.get(node)){
            boolean[] adj = new boolean[nodes_in_complement_graph];
            int cbn = 0;
            for(int v: graph.get(u)) {
                if(big_node[v]) cbn++;
                else adj[map.get(v)] = true;
            }
            if (cbn == bn) {
                adj[0] = true;
            }
            for (int v = 0; v < nodes_in_complement_graph; v++) {
                if (!adj[v] && v!=map.get(u)) {
                    if(v==0) complement_graph.get(v).add(map.get(u));
                    complement_graph.get(map.get(u)).add(v);
                }
            }
        }
        int cc=0;
        boolean[] visited = new boolean[nodes_in_complement_graph];
        for(int i=0;i<nodes_in_complement_graph;i++){
            if(!visited[i]){
                cc++;
                bfs(complement_graph,i,visited);
            }
        }
        return cc;
    }
    private static void bfs(List<List<Integer>> graph, int node, boolean[] visited){
        visited[node] = true;
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(node);
        while(!q.isEmpty()) {
            int cur = q.remove();
            for (int c : graph.get(cur)) {
                if (!visited[c]) {
                    q.offer(c);
                    visited[c] = true;
                }
            }
        }
    }
}
class SizesOfConnectedComponentsInComplementGraph {
    private static int bn;
    private static int size;
    public static List<Integer> find(List<List<Integer>> graph) {
        int n = graph.size();
        int[] degree = new int[n];
        for (List<Integer> list : graph) {
            for (int u : list) degree[u]++;
        }
        int min_degree = n + 1, node = -1;
        for (int i = 0; i < n; i++) {
            if (degree[i] < min_degree) {
                min_degree = degree[i];
                node = i;
            }
        }
        boolean[] big_node = new boolean[n];
        bn = n;
        for (int i = 0; i < n; i++) big_node[i] = true;
        for (int u : graph.get(node)) {
            big_node[u] = false;
            bn--;
        }
        int nodes_in_complement_graph = degree[node] + 1;
        List<List<Integer>> complement_graph = new ArrayList<>();
        for (int i = 0; i < nodes_in_complement_graph; i++) complement_graph.add(new ArrayList<>());
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(node, 0);

        for (int u : graph.get(node)) map.put(u, map.size());
        for (int u : graph.get(node)) {
            boolean[] adj = new boolean[nodes_in_complement_graph];
            int cbn = 0;
            for (int v : graph.get(u)) {
                if (big_node[v]) cbn++;
                else adj[map.get(v)] = true;
            }
            if (cbn == bn) {
                adj[0] = true;
            }
            for (int v = 0; v < nodes_in_complement_graph; v++) {
                if (!adj[v] && v!=map.get(u)) {
                    if(v==0) complement_graph.get(v).add(map.get(u));
                    complement_graph.get(map.get(u)).add(v);
                }
            }
        }
        int cc = 0;
        List<Integer> list = new ArrayList<>();
        boolean[] visited = new boolean[nodes_in_complement_graph];
        for (int i = 0; i < nodes_in_complement_graph; i++) {
            if (!visited[i]) {
                size = 0;
                bfs(complement_graph, i, visited);
                list.add(size);
            }
        }
        return list;
    }
    private static void bfs(List<List<Integer>> graph, int node, boolean[] visited){
        visited[node] = true;
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(node);
        while(!q.isEmpty()) {
            int cur = q.remove();
            if (cur == 0) size += bn;
            else size++;
            for (int c : graph.get(cur)) {
                if (!visited[c]) {
                    q.offer(c);
                    visited[c] = true;
                }
            }
        }
    }
}
class BinaryLifting{
    int[] parent;
    int n;
    int[][] dp;
    int max_len;
    public BinaryLifting(int[] parent){
        this.parent = parent;
        this.n = parent.length;
        this.max_len = (int)(Math.ceil(Math.log(n)/Math.log(2)));
        this.dp = new int[n][max_len+1];
        for(int[] d: dp) Arrays.fill(d,-1);
        for(int i=0;i<n;i++){
            dp[i][0] = parent[i];
        }
        for(int i=1;i<=max_len;i++){
            for(int node=0;node<n;node++) {
                if( dp[node][i-1]!=-1) dp[node][i] = dp[dp[node][i-1]][i-1];
            }
        }
    }
    public int getKthAncestor(int node,int k){
        for(int i=max_len;i>=0;i--){
            int mask = 1<<i;
            if(node == -1) return node;
            if((mask&k) != 0){
                node = dp[node][i];
            }
        }
        return node;
    }
}
class DistanceBetweenAllNodesOfAWeightedTree{
    List<List<long[]>> tree;
    int n;
    int[] subtree_size;
    long[] dp1;
    long[] dp2;
    long MOD;
    public DistanceBetweenAllNodesOfAWeightedTree(List<List<long[]>> tree,long MOD) {
        this.tree = tree;
        this.MOD = MOD;
        this.n = tree.size();
        this.subtree_size = new int[n];
        this.dp1 = new long[n];
        this.dp2 = new long[n];
    }
    private int dfs1(int node, int parent){
        int cnt = 1;
        for(long[] edge: tree.get(node)){
            int child = (int)edge[0];
            if(child!=parent){
                cnt += dfs1(child,node);
            }
        }
        return subtree_size[node] = cnt;
    }
    public long get(){
        dfs1(0,-1);
        dp2[0] = dfs2(0,-1);
        dfs3(0,-1,-1);
        long ans = 0L;
        for(long d: dp2) ans = (ans+d)%MOD;
        long inv = Long.parseLong(new BigInteger("2").modInverse(new BigInteger(Long.toString(MOD))).toString());
        return ans*inv%MOD;
    }
    private long dfs2(int node, int parent){
        long res = 0L;
        for(long[] edge: tree.get(node)){
            int child = (int)edge[0];
            if(child != parent){
                res = (res + dfs2(child,node) + subtree_size[child]*edge[1])%MOD;
            }
        }
        return dp1[node] = res;
    }
    private void dfs3(int node, int parent,long w){
        if(parent!=-1){
            dp2[node] = (dp2[parent]+w*(Math.floorMod(n-2L*subtree_size[node],MOD)))%MOD;
        }
        for(long[] edge: tree.get(node)){
            int child = (int)edge[0];
            if(child!=parent) dfs3(child,node,edge[1]);
        }
    }
}
//DFS Tree
class Bridge{
    int n;
    List<List<Integer>> graph;
    List<List<Integer>> bridges;
    int[] dp;
    int[] lvl;
    public Bridge(List<List<Integer>> graph){
        this.graph = graph;
        this.n = graph.size();
        this.bridges = new ArrayList<>();
        this.dp = new int[n];
        this.lvl = new int[n];
    }
    private void bridge_dfs(int node, int parent){
        for(int child: graph.get(node)){
            if(lvl[child] == 0){
                lvl[child] = lvl[node]+1;
                bridge_dfs(child,node);
                dp[node] += dp[child];
            }
            else if(lvl[child] > lvl[node]) dp[node]--;
            else if(lvl[child] < lvl[node]) dp[node]++;
        }
        dp[node]--;
        if(lvl[node]>1 && dp[node] == 0) bridges.add(List.of(parent,node));
    }
    public List<List<Integer>> getAllBridges(){
        lvl[0]++;
        bridge_dfs(0,-1);
        return bridges;
    }
}
class Cycles{
    private static int cycle_start = -1;
    private static int cycle_end = -1;
    public static List<Integer> getAnyCycleInUndirectedGraph(List<List<Integer>> graph){
        int n = graph.size();
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(parent,-1);
        for(int i=0;i<n;i++){
            if(!visited[i] && dfs(i,parent[i],graph,visited,parent)) break;
        }
        List<Integer> cycle = new ArrayList<>();
        if(cycle_start == -1) return cycle;
        cycle.add(cycle_start);
        for(int i=cycle_end;i!=cycle_start && i!=-1;i=parent[i]) cycle.add(i);

        return cycle;
    }
    private static boolean dfs(int node, int p, List<List<Integer>> graph, boolean[] visited, int[] parent){
        visited[node] = true;
        for(int child: graph.get(node)){
            if(child == p) continue;
            if(visited[child]){
                cycle_start = child;
                cycle_end = node;
                return true;
            }
            parent[child] = node;
            if(dfs(child,node,graph,visited,parent)) return true;
        }
        return false;
    }
}
class LCA_With_Binary_Lifting {
    int[] parent;
    int[] height;
    int n;
    int[][] dp;
    int max_len;
    public LCA_With_Binary_Lifting(List<List<Integer>> tree, int root){
        this.n = tree.size();
        this.height = new int[n];
        this.parent = new int[n];
        this.max_len = (int)(Math.ceil(Math.log(n)/Math.log(2)));
        this.dp = new int[n][max_len+1];
        for(int[] d: dp) Arrays.fill(d,-1);
        dfs(root,-1,tree,0);
        for(int i=0;i<n;i++){
            dp[i][0] = parent[i];
        }
        for(int i=1;i<=max_len;i++){
            for(int node=0;node<n;node++) {
                if( dp[node][i-1]!=-1) dp[node][i] = dp[dp[node][i-1]][i-1];
            }
        }
    }
    public int getKthAncestor(int node,int k){
        for(int i=max_len;i>=0;i--){
            int mask = 1<<i;
            if(node == -1) return node;
            if((mask&k) != 0){
                node = dp[node][i];
            }
        }
        return node;
    }
    public int LCA(int a, int b){
        if(a == b) return a;
        if(height[b]>height[a]){
            int temp = a;
            a = b;
            b = temp;
        }
        int c = getKthAncestor(a,height[a]-height[b]);
        if(c == b) return b;
        for(int i=max_len;i>=0;i--){
            if(dp[b][i]!=-1 && dp[b][i]!=dp[c][i]){
                b = dp[b][i];
                c = dp[c][i];
            }
        }
        return dp[b][0];
    }
    private void dfs(int node, int p, List<List<Integer>> tree,int h){
        height[node] = h;
        parent[node] = p;
        for(int child: tree.get(node)){
            if(child == p) continue;
            dfs(child,node,tree,h+1);
        }
    }
    public int getDistance(int u, int v){
        return height[u] + height[v] - 2*height[LCA(u,v)];
    }
}
class LCA_Tree{
    int[] parent;
    int n;
    int[][] dp;
    int[] height;
    int max_len;
    public LCA_Tree(int[] parent,int[] height){
        this.parent = parent;
        this.height = height;
        this.n = parent.length;
        this.max_len = (int)(Math.ceil(Math.log(n)/Math.log(2)));
        this.dp = new int[n][max_len+1];
        for(int[] d: dp) Arrays.fill(d,-1);
        for(int i=0;i<n;i++){
            dp[i][0] = parent[i];
        }
        for(int i=1;i<=max_len;i++){
            for(int node=0;node<n;node++) {
                if( dp[node][i-1]!=-1) dp[node][i] = dp[dp[node][i-1]][i-1];
            }
        }
    }
    public int getKthAncestor(int node,int k){
        for(int i=max_len;i>=0;i--){
            int mask = 1<<i;
            if(node == -1) return node;
            if((mask&k) != 0){
                node = dp[node][i];
            }
        }
        return node;
    }
    public int LCA(int a, int b){
        if(a == b) return a;
        if(height[b]>height[a]){
            int temp = a;
            a = b;
            b = temp;
        }
        int c = getKthAncestor(a,height[a]-height[b]);
        if(c == b) return b;
        for(int i=max_len;i>=0;i--){
            if(dp[b][i]!=-1 && dp[b][i]!=dp[c][i]){
                b = dp[b][i];
                c = dp[c][i];
            }
        }
        return dp[b][0];
    }
}
/*

Dijkstra Vimp
private static int dj(int start,List<List<Edge>> graph){
        int n = graph.size();
        int[] dis = new int[n];
        Arrays.fill(dis,Integer.MAX_VALUE);
        dis[start] = 0;
        Queue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.w));
        queue.offer(new Edge(start,0));
        while(!queue.isEmpty()){
            Edge E = queue.remove();
            int node = E.v;
            if(dis[node] != E.w) continue;   //V imp
            for(Edge edge: graph.get(node)){
                int child = edge.v;
                int w = edge.w;
                if(dis[child] > w + dis[node]){
                    dis[child] = w + dis[node];
                    queue.offer(new Edge(child, dis[child]));
                }
            }
        }
        if(dis[0] == Integer.MAX_VALUE) return -1;
        return dis[0];
    }
 */