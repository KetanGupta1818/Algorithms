/*
    Accepted Solutions
    1) https://www.codechef.com/viewsolution/96491109 
    


*/
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
