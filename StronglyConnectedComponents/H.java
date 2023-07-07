//package kg.my_algorithms.Codeforces;




import java.io.*;
import java.util.*;
//                                    माँ


public class Solution{
    private static final FastReader fr = new FastReader();
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
//        int testCases = fr.nextInt();
//        for(int testCase=1;testCase<=testCases;testCase++) {
//
//        }
        int total_nodes = fr.nextInt();
        int m = fr.nextInt();
        int[][] edges = new int[m][2];
        for(int i=0;i<m;i++){
            int u = fr.nextInt()-1;
            int v = fr.nextInt()-1;
            edges[i][0] = u;
            edges[i][1] = v;
        }
        Kosaraju kosaraju = new Kosaraju(total_nodes,edges);
        if(kosaraju.ssc == 1) out.println("0");
        else{
            int n = kosaraju.ssc;
            int[] inDegree = new int[n];
            int[] outDegree = new int[n];
            int[] map = new int[n];
            for(int[] edge: edges){
                int u = kosaraju.component_number[edge[0]]-1;
                int v = kosaraju.component_number[edge[1]]-1;
                map[v] = edge[1]+1;
                map[u] = edge[0]+1;
                if(u!=v){
                    inDegree[v]++;
                    outDegree[u]++;
                }
            }
            int a=0,b=0;
            for(int i=0;i<n;i++){
                if(inDegree[i] == 0) {
                    a++;
                }
                if(outDegree[i] == 0) {
                    b++;
                }
            }
            out.println(Math.max(a,b));
        }
        out.flush();
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
















class FastReader {
    BufferedReader br;
    StringTokenizer st;

    public FastReader()
    {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() { return Integer.parseInt(next()); }

    long nextLong() { return Long.parseLong(next()); }

    double nextDouble()
    {
        return Double.parseDouble(next());
    }

    String nextLine()
    {
        String str = "";
        try {
            if(st.hasMoreTokens()){
                str = st.nextToken("\n");
            }
            else{
                str = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
