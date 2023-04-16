//DFS Tree   source-> https://codeforces.com/blog/entry/68138
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
/*
1. Leetcode 1192. Critical Connections in a Network.   Solution   https://leetcode.com/submissions/detail/934700654/


*/
