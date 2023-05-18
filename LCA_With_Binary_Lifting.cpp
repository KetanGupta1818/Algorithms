class LCA_With_Binary_Lifting{
    public:
    int *parent,*height,n,**dp,max_len;
    LCA_With_Binary_Lifting(vector<vector<int>> &tree, int root){
        n = tree.size();
        parent = (int*) malloc(n*sizeof(int));
        height = (int*) malloc(n*sizeof(int));
        max_len = ceil(log(n)/log(2));
        dp = (int**) malloc(n*sizeof(int*));
        for(int i=0;i<n;i++){
            dp[i] = (int*) malloc((max_len+1)*sizeof(int));
            for(int j=0;j<=max_len;j++) dp[i][j] = -1;
        }
        dfs(tree,root,-1,0);
        for(int i=0;i<n;i++) dp[i][0] = parent[i];
        for(int i=1;i<=max_len;i++){
            for(int node=0;node<n;node++) {
                if( dp[node][i-1]!=-1) dp[node][i] = dp[dp[node][i-1]][i-1];
            }
        }
 
    }
    void dfs(vector<vector<int>> &tree, int node, int p, int h){
        height[node] = h;
        parent[node] = p;
        for(int child: tree[node]){
            if(child!=p) dfs(tree,child,node,h+1);
        }
    }
    int getKthAncestor(int node,int k){
        for(int i=max_len;i>=0;i--){
            int mask = 1<<i;
            if(node == -1) return node;
            if((mask&k) != 0){
                node = dp[node][i];
            }
        }
        return node;
    }
    int LCA(int a, int b){
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
    int getDistance(int u, int v){
        return height[u] + height[v] - 2*height[LCA(u,v)];
    }
};
