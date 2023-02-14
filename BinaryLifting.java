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
                if(dp[node][i-1]!=-1) dp[node][i] = dp[dp[node][i-1]][i-1];
            }
        }
    }
  //Returns -1 if kth ancestor does not exists
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
