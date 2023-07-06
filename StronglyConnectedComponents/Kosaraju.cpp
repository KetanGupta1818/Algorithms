class Kosaraju{
public:
    int ssc=0,n,dfs_index=0;
    int *component_number,*dfs_order;

    void dfs1(int node,vector<bool>& visited1,vector<vector<int>>& graph){
        visited1[node] = true;
        for(int child: graph[node]){
            if(visited1[child]) continue;
            dfs1(child,visited1,graph);
        }
        dfs_order[dfs_index++] = node;
    }
    void dfs2(int node,vector<bool>& visited2,vector<vector<int>>& reverse_graph){
        visited2[node] = true;
        for(int child: reverse_graph[node]){
            if(visited2[child]) continue;
            dfs2(child,visited2,reverse_graph);
        }
        component_number[node] = ssc;
    }

    Kosaraju(int number_of_nodes,int number_of_edges,pair<int,int> directed_edges[]){
        n = number_of_nodes;
        vector<vector<int>> graph(n,vector<int>());
        vector<vector<int>> reverse_graph(n,vector<int>());
        component_number = (int*)malloc(n*sizeof(int));
        dfs_order = (int*)malloc(n*sizeof(int));
        for(int i=0;i<number_of_edges;i++){
            graph[directed_edges[i].first].push_back(directed_edges[i].second);
            reverse_graph[directed_edges[i].second].push_back(directed_edges[i].first);
        }
        vector<bool> visited1(n,false);
        vector<bool> visited2(n,false);
        for(int i=0;i<n;i++){
            if(!visited1[i]) dfs1(i,visited1,graph);
        }
        for(int i=n-1;i>=0;i--){
            int node = dfs_order[i];
            if(!visited2[node]){
                ssc++;
                dfs2(node,visited2,reverse_graph);
            }
        }
    }
};
