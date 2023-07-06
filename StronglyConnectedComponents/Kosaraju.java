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
