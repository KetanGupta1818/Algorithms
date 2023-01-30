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
