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

class LCA{
    List<List<Integer>> tree;
    int n;
    int[] eulerTour;
    int[] firstVisit;
    int index=0;
    int root;
    int[] heights;
    MinSparseTableWithIndex minSparseTableWithIndex;

    public LCA(List<List<Integer>> tree,int root){
        this.tree = tree;
        this.root = root;
        this.n = tree.size();
        this.eulerTour = new int[2*n-1];
        this.heights = new int[2*n-1];
        this.firstVisit = new int[n];
        Arrays.fill(firstVisit,-1);
        dfs(root,-1,0);
        this.minSparseTableWithIndex = new MinSparseTableWithIndex(heights);
    }
    private void dfs(int node,int parent,int height){
        if(firstVisit[node] == -1) firstVisit[node] = index;
        heights[index] = height;
        eulerTour[index++] = node;

        for(int child: tree.get(node)){
            if(child == parent) continue;
            dfs(child,node,height+1);
            heights[index] = height;
            eulerTour[index++] = node;
        }
    }
    public int getLCA(int u, int v){
        return eulerTour[minSparseTableWithIndex.getMinimum(Math.min(firstVisit[u],firstVisit[v]),Math.max(firstVisit[u],firstVisit[v])).index];
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
